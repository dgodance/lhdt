
//원/중/근경
const ViewPoint = {'A':0, 'B':1, 'C':2};

//테스트용
const xyz1 = {'lon':127.2827, 'lat':36.4780};	//시작점
const xyz2 = {'lon':127.2925, 'lat':36.4824};	//종료점

const PREDICT_SERVER_URL = 'http://localhost:8090/svc/landscape_anals/uploadFileAndGetSkylineImage';


/**
 * 스카이라인
 * @dependency pp, ppmap
 * @author gravity
 * @since	20200812	init
 */
const SkylineObj = function(){
	
	//원/중/근경 point xyz
	this._viewPoint = {}	
	
	//캡처한 이미지(blob)
	this._captureBlob = {};
	
	//스카이라인 이미지(blob)
	this._skylineBlob = {};
};



/**
 * 
 */
SkylineObj.prototype.init = function(viewer){
	//
	ppmap.viewer = viewer;

	//
	this.setEventHandler();
	
};



/**
 * 이벤트 등록
 */
SkylineObj.prototype.setEventHandler = function(){
	//
	let the = this;
	
	//모달 표시
	ppui.on(document.querySelector('.ds-show-modal'), 'click', function(){
		the.showModal();
	});
	
	
	
	//캡처된 원/중/근경 이미지 표시 클릭
	let showImageColl = document.getElementsByClassName('ds-image-thumb');
	for(let i=0; i<showImageColl.length; i++){
		let node = showImageColl[i];
		node.addEventListener('click' ,function(){
			the.showCaptureImage(parseInt(node.dataset.index));
		});		
	}
	
	//분석된 원/중/근경 이미지 표시 클릭
	let showImageSkylineColl = document.querySelectorAll('.ds-image-skyline-thumb');
	for(let i=0; i<showImageSkylineColl.length; i++){
		let node = showImageSkylineColl[i];
		node.addEventListener('click', function(){
			the.showSkylineImage(parseInt(node.dataset.index));
		});
	}
	
	
	//자동으로 3점 지도 캡처 & get 스카이라인 base64 & show modal
	ppui.on(document.querySelector('.ds-autoall'), 'click' ,function(){
		document.querySelector('body').style.cursor = 'wait';
		
		//모든 점 자동 캡처
		the.autoCaptureAll(function(){
			console.log('<<.autoCaptureAll');
			
			//show modal
			the.showModal();
			
			
			//캡처 blob <img>에 할당
			for(let i=0; i<the.getViewPointCo(); i++){
				the.setCaptureImgSrc(i, the.getCaptureBlob(i));
				the.setCaptureThumbImgSrc(i, the.getCaptureBlob(i));
			}
			
			//모든 blob 업로드 & get 스카이라인 base64
			the.uploadBlobAndGetSkylineImageAll(function(){
				console.log('<<.uploadBlobAndGetSkylineImageAll');
								
				//
				alert('스카이라인 분석이 완료되었습니다.');
				document.querySelector('body').style.cursor = 'default';
			});
		});
		
		
		//
		the.showCaptureImage(ViewPoint.A);
	});
	
	
	
	//테스트 위치로 이동
	ppui.on(document.querySelector('.ds-flyto'), 'click', function(){
		//세종시청
		gotoFly(127.2891, 36.4800, 10);
		
		//TODO 지도위에 HTML 표시하기. 성공
		
		
	});
	
	
	//2점 선택했다고 치고...draw 2 points
	ppui.on(document.querySelector('.ds-points2'), 'click', function(){
		//시작점
		the.drawPoint(xyz1.lon, xyz1.lat);
		//끝점
		the.drawPoint(xyz2.lon, xyz2.lat);
		//선
		the.drawLine(xyz1, xyz2);		
		
	});
	
	
	
	//원/중/근경 위치 계산 & 점표시
	ppui.on(document.querySelector('.ds-points3'), 'click', function(){
		let viewPoint = the.calcViewPoint(xyz1, xyz2);
		
		//점 표시
		for(let i=0; i<3; i++){
			the.drawPoint(viewPoint[i].lon, viewPoint[i].lat);
		}
		
	});



	
	//원경 클릭 이벤트
	ppui.on(document.querySelector('.ds-movetop1'), 'click', function(){
		//
		let heading = ppmap.getHeading(xyz1, xyz2);
		
		//
		const xyz = pp.extend(sobj.getViewPoint(ViewPoint.A), {'alt':30.0});
		//
		const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
		//
		ppmap.flyTo(xyz, hpr, {'duration':0.5});
		
	});
		
		
	

	//중경 클릭 이벤트
	ppui.on(document.querySelector('.ds-movetop2'), 'click', function(){
		let heading = ppmap.getHeading(xyz1, xyz2);
		
		//
		const xyz = pp.extend(sobj.getViewPoint(ViewPoint.B), {'alt':30.0});
		//
		const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
		//
		ppmap.flyTo(xyz, hpr, {'duration':0.5});		
	});
		
		
	//근경 클릭 이벤트
	ppui.on(document.querySelector('.ds-movetop3'), 'click', function(){
		let heading = ppmap.getHeading(xyz1, xyz2);
		
		//
		const xyz = pp.extend(sobj.getViewPoint(ViewPoint.C), {'alt':30.0});
		const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
		//
		ppmap.flyTo(xyz, hpr, {'duration':0.5});
		
	});
	
	
	//저장 클릭
	ppui.on(document.querySelector('.ds-save'), 'click', function(){
		if(!confirm('저장하시겠습니까?')){
			return;
		}
		
		//
		let url = 'http://localhost:8090/svc/landscape_anals/saveCaptureAndSkylineImages';
		let fd = new FormData();
		
		for(let i=0; i<the.getViewPointCo(); i++){
			fd.append('capture['+i+']', the.getCaptureBlob(i));
			fd.append('skyline['+i+']', the.getSkylineBlob(i));
		}
		
		//
		pp.submitFormData(url, fd, function(){
			alert('저장되었습니다.');
		});
	});
		
};


/**
 * base64를 blob으로 변환
 * @param {string} base64
 * @param {string} contentType
 * @returns {Blob}
 */
function base64ToBlob(base64, contentType){
	let byteCharacters = window.atob(base64);
	let byteArrays=[];
	let sliceSize=512;
	
	for(let offset=0; offset<byteCharacters.length; offset+=sliceSize){
		let slice = byteCharacters.slice(offset, offset + sliceSize);
		
		//
		let byteNumbers = new Array(slice.length);
		for(let i=0; i<slice.length; i++){
			byteNumbers[i] = slice.charCodeAt(i);
		}
		
		//
		let byteArray = new Uint8Array(byteNumbers);
		byteArrays.push(byteArray);
	}
	
	//
	let blob = new Blob(byteArrays, {type:contentType});
	return blob;
}


/**
 * set 캡처 이미지(blob) 
 * @param {number} gbn
 * @param {Blob} blob
 */ 
SkylineObj.prototype.setCaptureBlob = function(gbn, blob){
	this._captureBlob[gbn] = blob;
};

/**
 * get 캡처 이미지(blob) 
 * @param {number} gbn
 * @returns {Blob} blob
 */ 
SkylineObj.prototype.getCaptureBlob = function(gbn){
	return this._captureBlob[gbn];
};

/**
 * set 스카이라인 이미지(blob) 
 * @param {number} gbn
 * @param {Blob} blob
 */ 
SkylineObj.prototype.setSkylineBlob = function(gbn, blob){
	this._skylineBlob[gbn] = blob;
};


/**
 * get 스카이라인 이미지(blob) 
 * @param {number} gbn
 * @returns {Blob}
 */ 
SkylineObj.prototype.getSkylineBlob = function(gbn){
	return this._skylineBlob[gbn];
};



/**
 * viewer정보 설정
 * @param {Viewer} viewer
 */
SkylineObj.prototype.setViewer = function(viewer){
	ppmap.viewer = viewer;	
};



/**
 * 원/중/근경 xyz 구하기
 * @param {ViewPoint} gbn 옵션. 존재시 해당 xyz만 리턴, 미존재시 원/중/근경 xyz 모두 리턴
 * @returns {object} gbn 설명 참조
 */
SkylineObj.prototype.getViewPoint = function(gbn){
	if(pp.isNotNull(gbn)){
		return this._viewPoint[gbn];
	}
	
	//
	return this._viewPoint;
}


/**
 * viewpoint 갯수
 */
SkylineObj.prototype.getViewPointCo = function(){
	return Object.keys(this._viewPoint).length;
};


/**
 * 3점 자동 캡처 
 * @param {function} callbackFn 자동 캡처 완료 후 호출할 콜백함수
 */
SkylineObj.prototype.autoCaptureAll = function(callbackFn){
	//
	let the = this;
	
	//
	let _flyToAndCapture = function(gbn){
		
		if(gbn >= the.getViewPointCo()){
			callbackFn();
			return;
		}
		
		//
		the.flyToAndCapture(gbn, function(blob){
			//
			the.setCaptureBlob(gbn, blob);
			//
			_flyToAndCapture(++gbn);
		});
	}
	
	//
	_flyToAndCapture(ViewPoint.A);	
};



/**
 * 지도 화면 캡처
 * @param {function} callbackFn 캡처 완료 후 호출할 콜백함수. 옵션
 */
SkylineObj.prototype.captureMap = function(callbackFn){
	
	//
	ppmap.captureMap(function(blob){
		if(pp.isNotNull(callbackFn)){
			callbackFn(blob);
		}
	});	
};



/**
 * 원/중/근경 xyz 계산
 * @param {LonLatAlt} beginXyz
 * @param {LonLatAlt} endXyz
 * @returns {object} {'aView':{LonLatAlt}, 'bView':{LonLatAlt}, 'cView':{LonLatAlt}}
 */
SkylineObj.prototype.calcViewPoint = function(beginXyz, endXyz){
	//
	let diffx = (endXyz.lon - beginXyz.lon) / 4;
	let diffy = (endXyz.lat - beginXyz.lat) / 4;
		
	//
	for(let i=0; i<3; i++){
		let times = (i+1);
		//
		this._viewPoint[i] = {'lon' : (beginXyz.lon + (diffx*times)), 'lat': (beginXyz.lat + (diffy*times))};		
	}
	
	//
	return this._viewPoint;
};



/**
 * 지도에 점 표시
 * @param {number} lon 경도
 * @param {number} lat 위도
 */
SkylineObj.prototype.drawPoint = function(lon, lat){
	let entity = ppmap.viewer.entities.add({
		position : Cesium.Cartesian3.fromDegrees(lon, lat, 10),
		point : {
				color : Cesium.Color.RED,
				pixelSize: 10,
				disableDepthTestDistance: Number.POSITIVE_INFINITY,
				heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
		}
	});
};



/**
 * 지도에 선 그리기
 * @param {LonLatAlt} xyz1
 * @param {LonLatAlt} xyz2
 */
SkylineObj.prototype.drawLine = function(xyz1, xyz2){
	let entity = ppmap.viewer.entities.add({
		name:'',
		polyline:{
			positions: Cesium.Cartesian3.fromDegreesArray([xyz1.lon, xyz1.lat, xyz2.lon, xyz2.lat]),
			width: 5,
			material: Cesium.Color.YELLOW,
			clampToGround: true				
		}
	})
};


/**
 * flyTo & 캡처후 콜백함수 호출
 * @param {LonLatAlt} xyz
 * @param {ViewPoint} gbn
 * @param {function} callbackFn flyTo&캡처 완료 후 호출할 콜백함수
 */
SkylineObj.prototype.flyToAndCapture = function(gbn, callbackFn){
	let the = this;
	
	//
	let heading = ppmap.getHeading(xyz1, xyz2);
	//
	const xyz = pp.extend(sobj.getViewPoint(gbn), {'alt':30.0});		
	//
	const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
	
	//
	ppmap.flyTo(xyz, hpr, {'duration':0.5}, function(){
		//
		the.captureMap(function(blob){
			//
			callbackFn(blob);
		});
	});
};



/**
 * 1개 blob 업로드 & 계산된(처리된) 스카인라인 이미지를 base64 문자열로 리턴받기
 * @param {ViewPoint} gbn
 * @param {function} callbackFn 처리된 이미지 받은 후 호출한 콜백함수
 */
SkylineObj.prototype.uploadBlobAndGetSkylineImage = function(gbn, callbackFn){
	//
	if(gbn >= sobj.getViewPointCo()){
		console.log(i, 'break');
		return;
	}
	
	//
	let fd = new FormData();
	fd.append('blob', this.getCaptureBlob(gbn));
	
	//
	pp.submitFormData(PREDICT_SERVER_URL, fd, function(json){
		callbackFn(json.base64)
	}, {});
	
};


/**
 * 모든  blob 업로드 & get 스카인라인 base64후 콜백함수 호출
 * @param {function} callbackFn 모든 작업 완료 후 호출할 콜백함수
 */
SkylineObj.prototype.uploadBlobAndGetSkylineImageAll = function(callbackFn){
	//
	let the = this;
	
	//
	let _upload = function(gbn){
		if(gbn >= the.getViewPointCo()){
			callbackFn();
			//
			return;
		}
		
		//
		the.uploadBlobAndGetSkylineImage(gbn, function(base64){
			//
			the.setSkylineBlob(gbn, base64ToBlob(base64));
			//화면에 표시
			the.setSkylineImgSrc(gbn, the.getSkylineBlob(gbn));
			the.setSkylineThumbImgSrc(gbn, the.getSkylineBlob(gbn));
			//
			_upload(++gbn);
		});
	};
	
	//
	_upload(ViewPoint.A);	
};





/**
 * 결과창 표시
 */
SkylineObj.prototype.showModal = function(){
	
	//모달창 실행
	let $skylineModal =  $('#skylineModal').dialog({
		autoOpen: false,
		width: 1024,
		height: 768,
		modal: true,
		resizable: false,
		title : '스카이라인 분석'
	});
	
	//
	$skylineModal.dialog('open');
};



/**
 * 캡처한 이미지(blob)를 <img>에 설정
 * @param {ViewPoint} gbn
 * @param {Blob} blob
 */
SkylineObj.prototype.setCaptureImgSrc = function(gbn, blob){
	let img = document.querySelector('.ds-image[data-index="'+gbn+'"]');
	if(pp.isNull(img)){
		console.log('null img');
		return;
	}

	//
	img.src = window.URL.createObjectURL(blob);
};


/**
 * 캡처한 이미지(blob)를 thunbnail <img>에 설정
 * @param {ViewPoint} gbn
 * @param {Blob} blob
 */
SkylineObj.prototype.setCaptureThumbImgSrc = function(gbn, blob){
	let img = document.querySelector('.ds-image-thumb[data-index="'+gbn+'"]');
	if(pp.isNull(img)){
		console.log('null img');
		return;
	}

	//
	img.src = window.URL.createObjectURL(blob);
};


/**
 * 스카이라인 이미지(blob)를 <img>에 설정
 * @param {number} gbn
 * @param {Blob} blob
 */
SkylineObj.prototype.setSkylineImgSrc = function(gbn, blob){
	let img = document.querySelector('.ds-image-skyline[data-index="'+gbn+'"]');
	if(pp.isNull(img)){
		console.log('null img');
		return;
	}

	//
	//img.src = 'data:image/png;base64,' + base64;
	img.src = window.URL.createObjectURL(blob);
};

/**
 * 스카이라인 이미지(blob)를 thumbnail <img>에 설정
 * @param {number} gbn
 * @param {Blob} blob
 */
SkylineObj.prototype.setSkylineThumbImgSrc = function(gbn, blob){
	let img = document.querySelector('.ds-image-skyline-thumb[data-index="'+gbn+'"]');
	if(pp.isNull(img)){
		console.log('null img');
		return;
	}

	//
	//img.src = 'data:image/png;base64,' + base64;
	img.src = window.URL.createObjectURL(blob);
};


/**
 * 캡처된 이미지 <img> show
 * @param {ViewPoint|number} gbn 원/중/근경 구분
 */
SkylineObj.prototype.showCaptureImage = function(gbn){
	//
	ppui.removeClass(document.querySelectorAll('.ds-image'), 'on');
	ppui.removeClass(document.querySelectorAll('.ds-image-skyline'), 'on');
	
	//
	ppui.addClass(document.querySelector('.ds-image[data-index="'+gbn+'"]'), 'on');
};


/**
 * 스카이라인 이미지 <img> show
 * @param {ViewPoint|number} gbn
 */
SkylineObj.prototype.showSkylineImage = function(gbn){
	//
	ppui.removeClass(document.querySelectorAll('.ds-image'), 'on');
	ppui.removeClass(document.querySelectorAll('.ds-image-skyline'), 'on');
	
	//
	ppui.addClass(document.querySelector('.ds-image-skyline[data-index="'+gbn+'"]'), 'on');

};




//
let sobj = new SkylineObj();
