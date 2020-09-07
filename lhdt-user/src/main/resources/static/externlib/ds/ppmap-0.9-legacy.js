/**
 * @since
 *	20200902 remove viewer. add MAGO3D_INSTANCE
 */
const Ppmap = function () {
};

/**
 */
Ppmap.init = function () {
	//
	console.log(new Date(), '<<Ppmap.init()');
};

/**
 * @deprecated 20200902
 */
Ppmap.getViewer = function () {
    return this._viewer;
}

/**
 * @deprecated 20200902
 */
Ppmap.setViewer = function (viewer) {
    this._viewer = viewer;
}


/**
 * @param {Cartesian3|LonLatAlt} ctsnOrXyz
 * @param {Cartesian3} direction
 */
Ppmap.addDirectionRay = function (ctsnOrXyz, direction) {
    const origin = Ppmap.toCartesian3(ctsnOrXyz);

    const directionRay = Cesium.Cartesian3.multiplyByScalar(direction, 100000, new Cesium.Cartesian3());
    Cesium.Cartesian3.add(origin, directionRay, directionRay);

    MAGO3D_INSTANCE.getViewer().entities.add({
        polyline: {
            positions: [origin, directionRay],
            width: 5,
            material: Cesium.Color.WHITE
        }
    });
}


/**
 * 모든 entity 삭제
 */
Ppmap.removeAll = function(){
	//
	MAGO3D_INSTANCE.getViewer().entities.removeAll();
}


/**
 * entity 삭제
 * @param {Entity} entity 엔티티 인스턴스
 */
Ppmap.removeEntity = function(entity){
	if(Pp.isNull(entity)){
		return;
	}
	
	//
	MAGO3D_INSTANCE.getViewer().entities.remove(entity);
}


/**
 * point entity 생성
 * @param {string} entityName
 * @param {number} lon
 * @param {number} lat
 * @param {object} option TODO
 * @returns {Entity}
 */
Ppmap.createPoint = function(entityName, lon, lat, option) {
    let worldPosition = Cesium.Cartesian3.fromDegrees(lon, lat);
    
    var entity = MAGO3D_INSTANCE.getViewer().entities.add({
        name: entityName,
        position: worldPosition,
        point: {
            color: Cesium.Color.RED,
            pixelSize: 10,
            outlineColor: Cesium.Color.RED,
            outlineWidth: 2,
            disableDepthTestDistance: Number.POSITIVE_INFINITY,
            heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
        }
    });
    return entity;
}


/**
 * polyline entity 생성
 * @param {string} entityName
 * @param {array} arr [lon1,lat1,lon2,lat2,...]
 * @param {object} option TODO
 * @returns {Entity}
 */
Ppmap.createPolyline = function(entityName, arr, option) {
	//	
    var entity = MAGO3D_INSTANCE.getViewer().entities.add({
		name: entityName,
        polyline: {
	        positions: Cesium.Cartesian3.fromDegreesArray(arr),
            width : 5,
			material : Cesium.Color.RED
        }
    });
    return entity;
}


/**
 * 현재 카메라 상태 추출
 * @returns {object} {'position', 'direction', 'up', 'right', 'transform', 'frustum'}
 * @since 20200904 init
 */
Ppmap.getCameraStatus = function() {
    const camera = MAGO3D_INSTANCE.getViewer().camera;
    const json = {
        position: camera.position.clone(),
        direction: camera.direction.clone(),
        up: camera.up.clone(),
        right: camera.right.clone(),
        transform: camera.transform.clone(),
        frustum: camera.frustum.clone()
    }
    return json;
}


/**
 * 카메라 상태값에 의한 flyTo
 * @param {object} status 카메라 상태. Ppmap.getCameraStatus()의 리턴값 참조
 * @since 20200904 init
 */
Ppmap.flyToByCameraStatus = function(status) {
    MAGO3D_INSTANCE.getViewer().camera.flyTo({
        destination : status.position,
        orientation : {
            direction : status.direction,
            up : status.up,
            right : status.right,
        }
    });
}



/**
 * @param {Cartesian3|LonLatAlt} ctsn1
 * @param {Cartesian3|LonLatAlt} ctsn2
 * @returns {Cartesian3} 
 */
Ppmap.getDirection = function (ctsn1, ctsn2) {
    //
    const origin = Ppmap.toCartesian3(ctsn1);
    const target = Ppmap.toCartesian3(ctsn2);

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
Ppmap.getHeading = function (pointA, pointB) {
    //
    const ctsnA = Ppmap.toCartesian3(pointA);
    const ctsnB = Ppmap.toCartesian3(pointB);

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
Ppmap.flyTo = function (ctsnOrXyz, hpr, option, callbackFn) {
    const ctsn = Ppmap.toCartesian3(ctsnOrXyz);

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
    if (null != option && undefined != option && Pp.isNotEmpty(option.duration)) {
        opt.duration = option.duration;
    }
    //
    if (null != callbackFn && undefined != callbackFn) {
        opt.complete = callbackFn;
    }

    //
    MAGO3D_INSTANCE.getViewer().scene.camera.flyTo(opt);
}




/**
 * degree object인지 여부
 * obj는 json형식 && lon라는 키 존재하면 degree라고 판단
 * @param {any} obj
 * @returns {boolean}
 */
Ppmap.isDegree = function (obj) {
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
Ppmap.captureMap = function (callbackFn) {
    
    var targetResolutionScale = 1.0;
    var timeout = 500; // in ms

    // define callback functions
    var prepareScreenshot = function () {
        MAGO3D_INSTANCE.getViewer().resolutionScale = targetResolutionScale;
        MAGO3D_INSTANCE.getViewer().scene.preRender.removeEventListener(prepareScreenshot);
        // take snapshot after defined timeout to allow scene update (ie. loading data)
        //startLoading();
        setTimeout(function () {
            MAGO3D_INSTANCE.getViewer().scene.postRender.addEventListener(takeScreenshot);
        }, timeout);
    }

    //
    var takeScreenshot = function () {
        MAGO3D_INSTANCE.getViewer().scene.postRender.removeEventListener(takeScreenshot);
        var canvas = MAGO3D_INSTANCE.getViewer().scene.canvas;
        canvas.toBlob(function (blob) {
            MAGO3D_INSTANCE.getViewer().resolutionScale = 1.0;

            console.log(blob);
            callbackFn(blob);
        });
    }

    //
    MAGO3D_INSTANCE.getViewer().scene.preRender.addEventListener(prepareScreenshot);
}


/**
 * 파라미터를 Cartesian3로 변환
 * @param {any} 가변적 Cartesian3 or LonLatAlt or lon,lat,alt
 * @returns {Cartesian3}
 */
Ppmap.toCartesian3 = function () {
    const args = arguments;


    if (0 === args.length) {
        throw Error('');
    }

    //
    if (1 === args.length && args[0] instanceof (Cesium.Cartesian3)) {
        return args[0];
    }

    //
    if (1 === args.length && Ppmap.isDegree(args[0])) {
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
Ppmap.resetRotate = function () {
    MAGO3D_INSTANCE.getViewer().scene.camera.flyTo({
        destination: MAGO3D_INSTANCE.getViewer().scene.camera.positionWC,
        duration: 1
    });
    /*
    this._viewer.scene.camera.flyTo({
        destination: camera.positionWC,
        duration : 0
    });
    */
}
