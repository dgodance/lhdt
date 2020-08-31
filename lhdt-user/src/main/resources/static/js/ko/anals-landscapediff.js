
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


/**
 * 경관 비교
 * @dependency pp, ppmap
 * @constructor
 * @since 20200824 init
 */
const AnalsLandScapeDiff = function() {
    this._captureList = {};
    this._landScapDiffStatList = [];
};

AnalsLandScapeDiff.prototype.init = function() {
}

AnalsLandScapeDiff.prototype.captureScreenProc = function(blob) {
    let the = this;
    the.captureMap(function(blob) {
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
    ppmap.captureMap(function (blob){
        if(Pp.isNotNull(callbackFn)) {
            callbackFn(blob);
        }
    });
};

AnalsLandScapeDiff.prototype.captureCameraState = function() {
    const camera = ppmap.viewer.camera;
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
    ppmap.viewer.camera.flyTo({
        destination : status.position,
        orientation : {
            direction : status.direction,
            up : status.up,
            right : status.right,
        }
    });
}

AnalsLandScapeDiff.prototype.renderDiffDropdown = function() {
    debugger;
    const html = $('#landscapeDiffSource').html();
    const template_html = Handlebars.compile(html);
    let the = this;
    $.get('http://localhost:9091/adminsvc/ls-diff-rest/group').done(function(data) {
        debugger;
        const lsGroupData = {
            landscapeGroupList: data
        }

        $('#landscapeDiffDataDiv').append(template_html(lsGroupData));
        $('#landscapeDiffDataDiv').find('#landscapeGroup').change(function() {
            renderDiffContentDefault();
        })

        renderDiffContentDefault();

        $('#landscapeDiffDataDiv').find('#landscapeSaveBtn').click(function() {
            the.captureMap(function(blob) {
                debugger;
                const formData = new FormData();


                formData.append("captureCameraState", JSON.stringify(the.captureCameraState()));
                formData.append('landScapeDiffGroupId', parseInt($('#landscapeGroup').val()));
                formData.append('landscapeName', $('#landscapeName').val());
                formData.append('image', blob);
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:9091/adminsvc/ls-diff-rest',
                    data: formData,
                    processData: false,
                    contentType: false
                }).done(function(data) {
                    renderDiffContentDefault();
                });

                // the.captureScreenProc(blob); //drawing
            })
        })
    })
}

AnalsLandScapeDiff.prototype.renderDiffContent = function(groupId, pageNum) {
    if(pageNum === undefined)
        pageNum = 1;
    $.get('http://localhost:9091/adminsvc/ls-diff-rest/'+groupId+'?lsDiffPage='+pageNum).done(function(diffObj) {
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
    $.get('http://localhost:9091/adminsvc/ls-diff-rest/scene/'+id).done(function(diffObj) {
        const analsLandScapeDiff = new AnalsLandScapeDiff();
        analsLandScapeDiff.moveCameraByStatus(JSON.parse(diffObj.captureCameraState));
    });
}

function showData(id) {
    $.get('http://localhost:9091/adminsvc/ls-diff-rest/info/'+id).done(function(diffObj) {

    });
}

function deleteData(id) {
    $.ajax({
        url : 'http://localhost:9091/adminsvc/ls-diff-rest/scene/'+id,
        type : "DELETE",
        success: function() {
            renderDiffContent();
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