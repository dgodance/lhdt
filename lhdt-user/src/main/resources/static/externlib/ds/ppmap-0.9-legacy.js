const PpMap = function () {

};

PpMap.prototype.init = function (viewer) {
    this._viewer = viewer
};

PpMap.prototype.getViewer = function () {
    return this._viewer;
}

PpMap.prototype.setViewer = function (viewer) {
    this._viewer = viewer;
}


/**
 * @param {Cartesian3|LonLatAlt} ctsnOrXyz
 * @param {Cartesian3} direction
 */
PpMap.prototype.addDirectionRay = function (ctsnOrXyz, direction) {
    const origin = this.toCartesian3(ctsnOrXyz);

    const directionRay = Cesium.Cartesian3.multiplyByScalar(direction, 100000, new Cesium.Cartesian3());
    Cesium.Cartesian3.add(origin, directionRay, directionRay);

    this._viewer.entities.add({
        polyline: {
            positions: [origin, directionRay],
            width: 5,
            material: Cesium.Color.WHITE
        }
    });
}



/**
 * @param {Cartesian3|LonLatAlt} ctsn1
 * @param {Cartesian3|LonLatAlt} ctsn2
 * @returns {Cartesian3} 
 */
PpMap.prototype.getDirection = function (ctsn1, ctsn2) {
    //
    const origin = this.toCartesian3(ctsn1);
    const target = this.toCartesian3(ctsn2);

    //
    const direction = Cesium.Cartesian3.subtract(target, origin, new Cesium.Cartesian3());
    //
    Cesium.Cartesian3.normalize(direction, direction);

    //
    return direction;
}




/**
* header값 계산 & 리턴
* @see https://stackoverflow.com/questions/58323971/cesium-calculate-heading-and-pitch-from-2-cartesian3-points
* @param {Cartesian3|LonLatAlt} pointA  
* @param {Cartesian3|LonLatAlt} pointB  
* @return {Number}
*/
PpMap.prototype.getHeading = function (pointA, pointB) {
    //
    const ctsnA = this.toCartesian3(pointA);
    const ctsnB = this.toCartesian3(pointB);

    //		
    const transform = Cesium.Transforms.eastNorthUpToFixedFrame(ctsnA);
    const positionvector = Cesium.Cartesian3.subtract(ctsnB, ctsnA, new Cesium.Cartesian3());
    const vector = Cesium.Matrix4.multiplyByPointAsVector(Cesium.Matrix4.inverse(transform, new Cesium.Matrix4()), positionvector, new Cesium.Cartesian3());
    const direction = Cesium.Cartesian3.normalize(vector, new Cesium.Cartesian3());
    //heading
    const heading = Math.atan2(direction.y, direction.x) - Cesium.Math.PI_OVER_TWO;
    //pitch
    const pitch = Cesium.Math.PI_OVER_TWO - Cesium.Math.acosClamped(direction.z);

    //
    return Cesium.Math.toDegrees(Cesium.Math.TWO_PI - Cesium.Math.zeroToTwoPi(heading));
}



/**
 * 해당 위치로 이동
 * @param {Cartesian3|lonLatAlt} ctsnOrXyz
 * @param {HeadingPitchRoll} hpr
 * @param {object} option {'duration':number}
 * @param {Function} callbackFn flyTo완료 후 호출할 콜백함수. 옵션
 */
PpMap.prototype.flyTo = function (ctsnOrXyz, hpr, option, callbackFn) {
    const ctsn = this.toCartesian3(ctsnOrXyz);

    //
    let opt = {
        destination: ctsn,
        orientation: {
            heading: hpr.heading,
            pitch: hpr.pitch,
            roll: hpr.roll
        }
    };
    //
    if (null != option && undefined != option && pp.isNotEmpty(option.duration)) {
        opt.duration = option.duration;
    }
    //
    if (null != callbackFn && undefined != callbackFn) {
        opt.complete = callbackFn;
    }

    //
    this._viewer.scene.camera.flyTo(opt);
}




/**
 * degree object인지 여부
 * obj는 json형식 && lon라는 키 존재하면 degree라고 판단
 * @param {any} obj
 * @returns {boolean}
 */
PpMap.prototype.isDegree = function (obj) {
    if ('object' !== typeof (obj)) {
        return false;
    }

    //
    return (undefined !== obj['lon'] ? true : false);
}


/**
 * 지도 화면 캡처
 * @param callbackFn 스크린샷 후 호출할 콜백함수
 */
PpMap.prototype.captureMap = function (callbackFn) {
    let viewer = this._viewer;
    var scene = this._viewer.scene;
    if (!scene) {
        console.error("No scene");
    }

    var targetResolutionScale = 1.0;
    var timeout = 500; // in ms

    // define callback functions
    var prepareScreenshot = function () {
        viewer.resolutionScale = targetResolutionScale;
        scene.preRender.removeEventListener(prepareScreenshot);
        // take snapshot after defined timeout to allow scene update (ie. loading data)
        //startLoading();
        setTimeout(function () {
            scene.postRender.addEventListener(takeScreenshot);
        }, timeout);
    }

    //
    var takeScreenshot = function () {
        scene.postRender.removeEventListener(takeScreenshot);
        var canvas = scene.canvas;
        canvas.toBlob(function (blob) {
            viewer.resolutionScale = 1.0;

            console.log(blob);
            callbackFn(blob);
        });
    }

    //
    scene.preRender.addEventListener(prepareScreenshot);
}


/**
 * 파라미터를 Cartesian3로 변환
 * @param {any} 가변적 Cartesian3 or LonLatAlt or lon,lat,alt
 * @returns {Cartesian3}
 */
PpMap.prototype.toCartesian3 = function () {
    const args = arguments;


    if (0 === args.length) {
        throw Error('');
    }

    //
    if (1 === args.length && args[0] instanceof (Cesium.Cartesian3)) {
        return args[0];
    }

    //
    if (1 === args.length && this.isDegree(args[0])) {
        return Cesium.Cartesian3.fromDegrees(args[0].lon, args[0].lat, args[0].alt || 0.0);
    }

    //
    const lon = args[0];
    const lat = args[1];
    const alt = args[2] || 0.0;

    //
    return Cesium.Cartesian3.fromDegrees(lon, lat, alt);
}


/**
* 지도 방향? 초기화
*/
PpMap.prototype.resetRotate = function () {
    ppmap.viewer.scene.camera.flyTo({
        destination: ppmap.viewer.scene.camera.positionWC,
        duration: 1
    });
    /*
    this._viewer.scene.camera.flyTo({
        destination: camera.positionWC,
        duration : 0
    });
    */
}



//
let ppmap = new PpMap();