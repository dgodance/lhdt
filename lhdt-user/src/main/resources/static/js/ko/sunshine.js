$(document).ready(function() {
    function onTimelineScrubfunction(e) {
        var clock = e.clock;
        clock.currentTime = e.timeJulian;
        clock.shouldAnimate = false;
    }

    var timeControlsContainer = document.getElementById('timeControlsContainer');
    var clock = Cesium.Clock();
    var clockViewModel = Cesium.ClockViewModel(clock);
    var animationContainer = document.createElement('div');
    animationContainer.className = 'cesium-viewer-animationContainer';
    timeControlsContainer.appendChild(animationContainer);
    var animation = Cesium.Animation(animationContainer, Cesium.AnimationViewModel(clockViewModel));
    var timelineContainer = document.createElement('div');
    timelineContainer.className = 'cesium-viewer-timelineContainer';
    timeControlsContainer.appendChild(timelineContainer);
    var timeline = Cesium.Timeline(timelineContainer, clock);
    timeline.addEventListener('settime', onTimelineScrubfunction, false);
    timeline.zoomTo(clock.startTime, clock.stopTime);

    window.setInterval(function() {
        clock.tick();
    }, 32);
})