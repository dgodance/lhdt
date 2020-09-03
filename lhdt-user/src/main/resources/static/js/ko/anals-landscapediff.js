
const LandScapeCameraStatus = function() {
    this._cameraInfo = {
        position : undefined,
        direction : undefined,
        up : undefined,
        right : undefined,
        transform : undefined,
        frustum : undefined
    };
    this._captureIdx = 0;
    this._blob = undefined;
}

const source = $('#landscapeDiffContentSource').html();
const template = Handlebars.compile(source);

let analsLandScapeDiff = undefined;

//
const	LS_DIFF_REST_URL = 'http://localhost:9091/adminsvc/ls-diff-rest';

/**
 * 경관 비교
 * @dependency pp, ppmap
 * @constructor
 * @since 20200824 init
 */
const AnalsLandScapeDiff = function() {
    this._captureList = {};
    this._landScapDiffStatList = [];
//
};

AnalsLandScapeDiff.prototype.init = function() {
}

AnalsLandScapeDiff.prototype.captureScreenProc = function(blob) {
    let the = this;
    Ppmap.captureMap(function(blob) {
        const landscapeCameraStatus = new LandScapeCameraStatus()
        landscapeCameraStatus._blob = blob;
        landscapeCameraStatus._captureIdx += 1;
        landscapeCameraStatus._cameraInfo = the.captureCameraState();
        the._landScapDiffStatList.push(landscapeCameraStatus);
        the.setCaptureImgSrc(blob);
    });
};

AnalsLandScapeDiff.prototype.setCaptureImgSrc = function(blob) {
    const image = new Image();
    if(Pp.isNull(image)) {
        console.log('null img');
        return;
    }
    image.src = window.URL.createObjectURL(blob);
}

AnalsLandScapeDiff.prototype.captureMap = function(callbackFn) {
    Ppmap.captureMap(function (blob){
        if(Pp.isNotNull(callbackFn)) {
            callbackFn(blob);
        }
    });
};

AnalsLandScapeDiff.prototype.captureCameraState = function() {
    const camera = MAGO3D_INSTANCE.getViewer().camera;
    const store = {
        position: camera.position.clone(),
        direction: camera.direction.clone(),
        up: camera.up.clone(),
        right: camera.right.clone(),
        transform: camera.transform.clone(),
        frustum: camera.frustum.clone()
    }
    return store;
}

AnalsLandScapeDiff.prototype.moveCameraByStatus = function(status) {
    MAGO3D_INSTANCE.getViewer().camera.flyTo({
        destination : status.position,
        orientation : {
            direction : status.direction,
            up : status.up,
            right : status.right,
        }
    });
}

AnalsLandScapeDiff.prototype.renderDiffDropdown = function() {
	let the = this;
	
    debugger;
    const html = $('#landscapeDiffSource').html();
    const template_html = Handlebars.compile(html);
    
    $.get(LS_DIFF_REST_URL + '/group').done(function(data) {
        debugger;
        const lsGroupData = {
            landscapeGroupList: data
        }

        $('#landscapeDiffDataDiv').append(template_html(lsGroupData));

		//경관그룹 선택박스 change 이벤트 등록
        $('#landscapeDiffDataDiv').find('#landscapeGroup').change(function() {
            renderDiffContentDefault();
        })

        renderDiffContentDefault();

		//저장 버튼 클릭 이벤트 등록
        $('#landscapeDiffDataDiv').find('#landscapeSaveBtn').click(function() {
			if(!confirm('저장하시겠습니까?')){
				return;
			}
			
			//
            Ppmap.captureMap(function(blob) {
                debugger;
                const formData = new FormData();


                formData.append("captureCameraState", JSON.stringify(the.captureCameraState()));
                formData.append('landScapeDiffGroupId', parseInt($('#landscapeGroup').val()));
                formData.append('landscapeName', $('#landscapeName').val());
                formData.append('image', blob);
                $.ajax({
                    type: 'POST',
                    url: LS_DIFF_REST_URL,
                    data: formData,
                    processData: false,
                    contentType: false
                }).done(function(data) {
					alert('저장되었습니다.');
					//
                    renderDiffContentDefault();
                });

                // the.captureScreenProc(blob); //drawing
            })
        })
    })
}


/**
 * (저장된)경관명 목록 표시
 * @param {string} groupId 그룹 아이디
 * @param {number|undefined} pageNum 페이지번호
 */
AnalsLandScapeDiff.prototype.renderDiffContent = function(groupId, pageNum) {
	if(Pp.isEmpty(groupId)){
		return;
	}
	//
    if(pageNum === undefined){
		pageNum = 1;	
	}
	
	//
    $.get(LS_DIFF_REST_URL + '/'+groupId+'?lsDiffPage='+pageNum).done(function(diffObj) {
        debugger;
        $('#landscapeDiffDetDataDiv').empty();
        $('#landscapeDiffDetDataDiv').append(template(diffObj));
        $('#landscapeName').val("");
    });
}

function renderDiffContentDefault() {
    new AnalsLandScapeDiff().renderDiffContent($('#landscapeGroup').val());
}
function renderDiffContentDefaultWithPage(pageNum) {
    new AnalsLandScapeDiff().renderDiffContent($('#landscapeGroup').val(), pageNum);
}

function gotoScene(id) {
    $.get(LS_DIFF_REST_URL + '/scene/'+id).done(function(diffObj) {
        const analsLandScapeDiff = new AnalsLandScapeDiff();
        analsLandScapeDiff.moveCameraByStatus(JSON.parse(diffObj.captureCameraState));
    });
}


/**
 * (저장된 이미지)확인
 * @param {string|number} id 경관 아이디
 */
function showLsDiffImage(id){
    $.get(LS_DIFF_REST_URL + '/images/'+id).done(function(res) {
		//이미지 정보는 base64 문자열로 리턴됨
        console.log(res);
	
		//
		let el = document.createElement('img');
		el.src = 'data:image/png;base64,' + res.base64;
		document.querySelectorAll('body')[0].appendChild(el);
    });
}


/**
 * 경관명 삭제
 * @param {string} id 경관명 아이디
 */
function deleteData(id) {
	if(!confirm('삭제하시겠습니까?')){
		return;
	}
	
	//
    $.ajax({
        url : LS_DIFF_REST_URL + '/scene/'+id,
        type : "DELETE",
        success: function() {
			alert('삭제되었습니다.');
			//
            renderDiffContentDefault();
        }
    });
}

/**
 * 값이 들어왔을시 해당 페이지로 이당하는 query를 보낸다.
 * @param paginNum
 */
function paginLandScapeList(paginNum) {
    renderDiffContentDefaultWithPage(paginNum);
}

$(document).ready(function() {
    const analsLandScapeDiff = new AnalsLandScapeDiff();
    analsLandScapeDiff.renderDiffDropdown();
    analsLandScapeDiff.init();
})