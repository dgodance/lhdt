
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
    if(pp.isNull(image)) {
        console.log('null img');
        return;
    }
    image.src = window.URL.createObjectURL(blob);
}

AnalsLandScapeDiff.prototype.captureMap = function(callbackFn) {
    ppmap.captureMap(function (blob){
        if(pp.isNotNull(callbackFn)) {
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
AnalsLandScapeDiff.prototype.renderDiffDropdown = function() {
    debugger;
    const html = $('#landscapeDiffSource').html();
    const template_html = Handlebars.compile(html);
    let the = this;
    $.get('http://localhost:9091/adminsvc/ls-diff/group').done(function(data) {
        const lsGroupData = {
            landscapeGroupList: data
        }
        new AnalsLandScapeDiff().renderDiffContent();

        $('#landscapeDiffDataDiv').append(template_html(lsGroupData));
        $('#landscapeDiffDataDiv').find('#landscapeGroup').change(function() {
            const groupId = $('#landscapeGroup').val();
            new AnalsLandScapeDiff().renderDiffContent(groupId);
        })

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
                    url: 'http://localhost:9091/adminsvc/ls-diff',
                    data: formData,
                    processData: false,
                    contentType: false
                }).done(function(data) {
                    console.log(data);
                    new AnalsLandScapeDiff().renderDiffContent($('#landscapeGroup').val());
                });

                // the.captureScreenProc(blob); //drawing
            })
        })
    })

}

AnalsLandScapeDiff.prototype.renderDiffContent = function(groupId) {
    if(groupId === undefined)
        groupId = 1;
    $.get('http://localhost:9091/adminsvc/ls-diff/'+groupId).done(function(diffObj) {
        const data = {
            landscapeDiffList: diffObj
        }
        $('#landscapeDiffDetDataDiv').empty();
        $('#landscapeDiffDetDataDiv').append(template(data));
        $('#landscapeName').val("");
    });
}

$(document).ready(function() {
    const analsLandScapeDiff = new AnalsLandScapeDiff();
    analsLandScapeDiff.renderDiffDropdown();
    analsLandScapeDiff.init();
})