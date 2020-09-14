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
 * 
 */
Ppmap.getViewer = function () {
    return MAGO3D_INSTANCE.getViewer();
}

/**
 * @deprecated 20200902
 */
Ppmap.setViewer = function (viewer) {
    this._viewer = viewer;
}

/**
 * 커서 백업
 */
Ppmap.backupCursor = function(){
	window['cursor'] = MAGO3D_INSTANCE.getViewer()._container.style.cursor;
};

/**
 * 커서 복원
 */
Ppmap.restoreCursor = function(){
	MAGO3D_INSTANCE.getViewer()._container.style.cursor = window['cursor'];
};

/**
 * 커서 복원
 */
Ppmap.setCursor = function(cursor){
    //
    Ppmap.backupCursor();
    //
	MAGO3D_INSTANCE.getViewer()._container.style.cursor = cursor;
};



/**
 */
Ppmap.getManager = function(){
    return MAGO3D_INSTANCE.getMagoManager();
};


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
 * PointAndLabel 생성
 * @param entityName
 * @param lon
 * @param lat
 * @param option
 * @returns {*}
 */
Ppmap.createPointAndLabel = function(entityName, text, lon, lat, option) {
    let worldPosition = Cesium.Cartesian3.fromDegrees(lon, lat);

    //
    let opt = Pp.extend({}, option);

    var entity = MAGO3D_INSTANCE.getViewer().entities.add({
        name: entityName,
        position: worldPosition,
        label: {
            text: text,
            font: "24px Helvetica",
            fillColor: Cesium.Color.SKYBLUE,
            outlineColor: Cesium.Color.BLACK,
            outlineWidth: 2,
            style: Cesium.LabelStyle.FILL_AND_OUTLINE,
            pixelOffset:  new Cesium.Cartesian2(0, -30),
            scale: 0.6,
            showBackground: true,
            horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
            translucencyByDistance: new Cesium.NearFarScalar(
                1.5e1,
                1.0,
                1.5e4,
                0
            ),
            heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
        },
        point: {
            color: (opt.color ? opt.color : Cesium.Color.RED),
            pixelSize: 10,
            outlineColor: Cesium.Color.YELLOW,
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
 * @param {array} arr [LonLat,LonLat,...]
 * @param {object} option TODO
 * @returns {Entity}
 */
Ppmap.createPolylineAndLabel = function(entityName,text, lonLats, option) {
    //
    let arr=[];
    //
    for(let i=0; i<lonLats.length; i++){
        let d = lonLats[i];
        //
        arr.push(d.lon);
        arr.push(d.lat);
    }
    var pp = Cesium.Cartesian3.fromDegreesArray(arr);
    var result = new Cesium.Cartesian3();
    Cesium.Cartesian3.midpoint(pp[0], pp[1], result);
    var entity = MAGO3D_INSTANCE.getViewer().entities.add({
        name: entityName,
        position: result,
        label: {
            text: text,
            font: "24px Helvetica",
            fillColor: Cesium.Color.SKYBLUE,
            outlineColor: Cesium.Color.BLACK,
            outlineWidth: 2,
            style: Cesium.LabelStyle.FILL_AND_OUTLINE,
            pixelOffset:  new Cesium.Cartesian2(0, -30),
            scale: 0.6,
            showBackground: true,
            horizontalOrigin: Cesium.HorizontalOrigin.CENTER,
            translucencyByDistance: new Cesium.NearFarScalar(
                1.5e1,
                1.0,
                1.5e4,
                0
            ),
            heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
        },
        polyline: {
            // This callback updates positions each frame.
            positions: new Cesium.CallbackProperty(function() {
                return Cesium.Cartesian3.fromDegreesArray(arr);
            }, false),
            width: 10,
            clampToGround: true,
            material: new Cesium.PolylineOutlineMaterialProperty({
                color: Cesium.Color.YELLOW,
            })
        },
    });

    //
    return entity;
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

	//
	let opt = Pp.extend({}, option);
    
    var entity = MAGO3D_INSTANCE.getViewer().entities.add({
        name: entityName,
        position: worldPosition,
        point: {
            color: (opt.color ? opt.color : Cesium.Color.RED),
            pixelSize: 10,
            outlineColor: Cesium.Color.YELLOW,
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
 * @param {array} arr [LonLat,LonLat,...]
 * @param {object} option TODO
 * @returns {Entity}
 */
Ppmap.createPolyline = function(entityName, lonLats, option) {
	//
	let arr=[];
	//
	for(let i=0; i<lonLats.length; i++){
		let d = lonLats[i];
		//
		arr.push(d.lon);
		arr.push(d.lat);
	}
	
	//	
    var entity = MAGO3D_INSTANCE.getViewer().entities.add({
		name: entityName,
		 polyline: {
            // This callback updates positions each frame.
            positions: new Cesium.CallbackProperty(function() {
				return Cesium.Cartesian3.fromDegreesArray(arr);                    
            }, false),
            width: 10,
            clampToGround: true,
            material: new Cesium.PolylineOutlineMaterialProperty({
                color: Cesium.Color.YELLOW,
            })
        },
	});

	//
    return entity;
}


/**
 * cartesian2를 LonLat을 변환
 */
Ppmap.cartesian2ToLonLat = function(ctsn2){
	const ctsn3 = MAGO3D_INSTANCE.getViewer().scene.pickPosition(ctsn2);
	const cartographic = Cesium.Cartographic.fromCartesian(ctsn3);
	
	//
	return Ppmap.cartoToLonLat(cartographic);
};


/**
cartographic을 {'lon', 'lat'}으로 변환
 * @param {Cartographic} cartographic
 * @returns {LonLat}
 */
Ppmap.cartoToLonLat = function(cartographic){
	if(Pp.isEmpty(cartographic)){
		return {
			'lon': NaN,	
			'lat': NaN,	
		}
	}
	
	//
	return {
		'lon':Cesium.Math.toDegrees(cartographic.longitude),
		'lat': Cesium.Math.toDegrees(cartographic.latitude)
	};
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
Ppmap.resetRotate = function (callbackFn) {
    debugger;
	let json={};
	json.destination = MAGO3D_INSTANCE.getViewer().scene.camera.positionWC;
	json.duration = 1;
	if(Pp.isNotEmpty(callbackFn)){
		json.complete = callbackFn;
	}
	
    MAGO3D_INSTANCE.getViewer().scene.camera.flyTo(json);
}


/**
 * 이벤트 삭제
 * @param {array|string} arr 이벤트타입의 배열 또는 'all' 문자열
 * @since 20200909 init
 */
Ppmap.removeInputAction = function(arrOrString){
    if(Pp.isEmpty(arrOrString)){
        return;
    }

    //
    let handler = new Cesium.ScreenSpaceEventHandler(MAGO3D_INSTANCE.getViewer().scene.canvas);

    //
    if('string' === arrOrString && 'ALL' === arrOrString.toUpperCase()){
        let arr = [];
        arr.push(Cesium.ScreenSpaceEventType.LEFT_DOWN);
        arr.push(Cesium.ScreenSpaceEventType.LEFT_UP);
        arr.push(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        arr.push(Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);
        arr.push(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
        arr.push(Cesium.ScreenSpaceEventType.RIGHT_CDOWN);
        arr.push(Cesium.ScreenSpaceEventType.RIGHT_UP);
        arr.push(Cesium.ScreenSpaceEventType.RIGHT_CLICK);

        //
        Ppmap.removeInputAction(arr);
        return;
    }

    //
    for(let i=0; i<arrOrString.length; i++){
        let eventType = arrOrString[i];

        //
        if('number' === eventType){
            handler.removeInputAction(eventType);
            return;
        }        
    }
};


/**
 * zoomTo with headingPitchRoll
 */
Ppmap.zoomTo = function(entity){
	//
	let heading = 0.0;
	let pitch = Cesium.Math.toRadians(-90);
	let roll = 0.0;
	
	//	
	MAGO3D_INSTANCE.getViewer().zoomTo(entity, new Cesium.HeadingPitchRoll(heading, pitch, roll) );
}


/**
 * 변환 전문
 */
Ppmap.Convert = {
    /**
     * cartesian2 => cartesian3
     * @param {Cartesian2} ctsn2 카티시안2
     */
    ctsn2ToCtsn3:function(ctsn2){
        return MAGO3D_INSTANCE.getViewer().scene.pickPosition(ctsn2);
    },

    
    /**
     * cartesian2 => cartographic
     * @param {Cartesian2} ctsn2 
     */
    ctsn2ToCartographic: function(ctsn2){
        return Cesium.Cartographic.fromCartesian(this.ctsn2ToCtsn3(ctsn2));
    },


    /**
     * cartesian2 => LonLat
     * @param {Cartesian2} ctsn2 
     */
    ctsn2ToLonLat: function(ctsn2){
        //
        let ctsn3 = this.ctsn2ToCtsn3(ctsn2);
        //
        let cartographic = this.ctsn3ToCartographic(ctsn3);
        //
        return this.cartographicToLonLat(cartographic);
    },


    /**
     * cartesian3 => cartographic
     * @param {Cartesian3} ctsn3 
     */
    ctsn3ToCartographic: function(ctsn3){
        return Cesium.Cartographic.fromCartesian(ctsn3);
    },

    /**
     * cartesian3 => LonLat
     * @param {Cartesian3} ctsn3 
     */
    ctsn3ToLonLat: function(ctsn3){
        //
        let cartographic = this.ctsn3ToCartographic(ctsn3);
        //
        return this.cartographicToLonLat(cartographic);
    },
    
    /**
     * cartographic => LonLat
     * @param {Cartographic} cartographic 
     */
    cartographicToLonLat: function(cartographic){
        //
        return {
            'lon':Cesium.Math.toDegrees(cartographic.longitude),
            'lat': Cesium.Math.toDegrees(cartographic.latitude),
            'longitude':Cesium.Math.toDegrees(cartographic.longitude),
            'latitude': Cesium.Math.toDegrees(cartographic.latitude),
        };
    },

	toLonLat: function(lon, lat, alt)   {
		return {
			'lon': lon,
			'lat': lat,
			'alt': (alt?alt:0),
		}
	}

};