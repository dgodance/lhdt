$(document).ready(function() {
    function onTimelineScrubfunction(e) {
        var clock = e.clock;
        clock.currentTime = e.timeJulian;
        clock.shouldAnimate = false;
    }
    var datepicker = new tui.DatePicker('#sunshineDatePicker', {
        date: new Date(),
        input: {
            element: '#sunshine-datepicker-input',
            format: 'yyyy-MM-dd'
        }
    });

})


var clearMap = function () {
    lengthInMeters = 0;
    areaInMeters = 0
    if (Cesium.defined(handler)) {
        handler.destroy();
        handler = null;
    }
    for (var i = 0, len = this._polylines.length; i < len; i++) {
        _viewer.entities.remove(this._polylines[i]);
    }
    for (var i = 0, len = this._labels.length; i < len; i++) {
        _viewer.entities.remove(this._labels[i]);
    }

    _viewer.entities.remove(activeShape);
    _viewer.entities.remove(activeLabel);

    activeShape = undefined;
    activeLabel = undefined;
    activeShapePoints = [];

    this._polylines = [];
    this._labels = [];
}

var timeSlider;
var solarMode = false;

//일조분석 조회
$('#solarAnalysis .execute').click(function() {
    debugger;
    if(!timeSlider) {
        timeSlider = new KotSlider('timeInput');
        timeSlider.setMin(1);
        timeSlider.setMax(24);
        timeSlider.setDuration(200);
        var html = '';
        _viewer.shadows = true;
        _viewer.softShadows = true;
        for(var i=1;i<25;i++) {
            if(i === 1 || 1 === 10) {
                html += '<span style="margin-left:22px;">' + i + '</span>';
            } else if(i < 10) {
                html += '<span style="margin-left:27px;">' + i + '</span>';
            } else {
                html += '<span style="margin-left:19px;">' + i + '</span>';
            }
        }

        $('#saRange .rangeWrapChild.legend').html(html);
        $('#saRange .rangeWrapChild.legend').on('click','span',function(){
            timeSlider.setValue(parseInt($(this).index())+1);
        });
    }

    var currentHour = new Date().getHours();
    currentHour  = currentHour === 0 ? 24 : currentHour;
    timeSlider.setValue(currentHour);

    //레인지 보이기
    $('#saRange').show();
    $('#csRange').hide();
    magoInstance.getViewer().scene.globe.enableLighting = true;
    magoManager.sceneState.setApplySunShadows(true);
    solarMode = true;

    changeDateTime();
});
var changeDateTime = function() {
    var date = datepicker.getDate();
    var hours = $('#timeInput').val();
    date.setHours(hours);
    setDate(date);
};