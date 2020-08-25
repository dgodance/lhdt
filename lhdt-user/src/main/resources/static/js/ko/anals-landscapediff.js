
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

AnalsLandScapeDiff.prototype.setEventHandler = function() {
    //
    let the = this;
    $('#landscapeSaveBtn').click(function() {
        $('')
        the.captureMap(function(blob) {
            the.captureScreenProc(blob);
        })
    })

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
    $.get('http://localhost:9091/adminsvc/ls-diff/group').done(function(data) {
        const lsGroupData = {
            landscapeGroupList: data
        }
        debugger;

        $('#landscapeDiffDataDiv').append(template_html(lsGroupData));
        $('#landscapeDiffDataDiv').find('#landscapeGroup').change(function() {
            console.log(this.value);
        })
    })

}

AnalsLandScapeDiff.prototype.renderDiffContent = function() {
    var source = $('#landscapeDiffContentSource').html();
    var template = Handlebars.compile(source);
    var data = {
        landscapeDiffList: [
            {landScapeDiffNum: 1, landscapeDiffName: '결과1'},
            {landScapeDiffNum: 2, landscapeDiffName: '결과2'},
            {landScapeDiffNum: 3, landscapeDiffName: '결과3'},
        ]
    }
    $('#landscapeDiffDetDataDiv').append(template(data));
}

$(document).ready(function() {
    const analsLandScapeDiff = new AnalsLandScapeDiff();
    analsLandScapeDiff.renderDiffDropdown();
    analsLandScapeDiff.init();
    analsLandScapeDiff.setEventHandler();
})