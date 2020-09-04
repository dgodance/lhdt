


const lsDropDownList = function() {
	this._ele = '#lsAnalsActionGroup';
	this._val = {};
	this._lsFreeAnalsWidgetSource = {};
	this._lsSavedAnalsWidgetSource = {};
	this._lsFreeAnalsWidgetSourceTlt = {};
	this._lsSavedAnalsWidgetSourceTlt = {};
}

lsDropDownList.prototype.init = function() {
	this.initProperty();
	this.change();
}
lsDropDownList.prototype.initProperty = function() {
	const p = new lsFreeAnalsWidget();
	p.defaultRender();
}

lsDropDownList.prototype.change = function () {
	let that = this;
	$(that._ele).change(function() {
		const val = that._val = $(this).val();
		if(val === "0") {
			const p = new lsFreeAnalsWidget();
			p.defaultRender()
		} else {
			const p = new lsSavedAnalsWidget();
			p.reqeustDataBylsAnalsPg();
		}
	});
}


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

	//시작 위치
	this._xyz1 = null;
	//종료 위치
	this._xyz2 = null;
};



/**
 * 
 */
SkylineObj.prototype.init = function(){
	//
	this.setEventHandler();
	
	//
	console.log(new Date(), 'SkylineObj', '<<.init');
	
};

SkylineObj.prototype.calcTwoPointSimulation = function() {

}



/**
 * 이벤트 등록
 */
SkylineObj.prototype.setEventHandler = function(){
	//
	let the = this;
	
	//모달 표시
	Ppui.on('.ds-show-modal', 'click', function(){
		the.showModal();
	});
	
	
	
	//캡처된 원/중/근경 이미지 표시 클릭
	let showImageColl = Ppui.find('.ds-image-thumb');
	for(let i=0; i<showImageColl.length; i++){
		let node = showImageColl[i];
		Ppui.click(node, function(){
			Ppui.removeClass(['.ds-image-thumb', '.ds-image-skyline-thumb'], 'on');
			Ppui.addClass(node, 'on');
			the.showCaptureImage(parseInt(node.dataset.index));
		});		
	}
	
	//분석된 원/중/근경 이미지 표시 클릭
	let showImageSkylineColl = Ppui.find('.ds-image-skyline-thumb');
	for(let i=0; i<showImageSkylineColl.length; i++){
		let node = showImageSkylineColl[i];
		Ppui.click(node, function(){
			Ppui.removeClass(['.ds-image-thumb', '.ds-image-skyline-thumb'], 'on');
			Ppui.addClass(node, 'on');
			the.showSkylineImage(parseInt(node.dataset.index));
		});
	}
	
	
	//자동으로 3점 지도 캡처 & get 스카이라인 base64 & show modal
	Ppui.on('.ds-autoall', 'click' ,function(){
		the.autoCaptureAllMenual();
	});
	
	
	
	//테스트 위치로 이동
	Ppui.on('.ds-flyto', 'click', function(){
		//세종시청
		gotoFly(127.2891, 36.4800, 10);
		
		//TODO 지도위에 HTML 표시하기. 성공
		
		
	});
	
	
	//2점 선택했다고 치고...draw 2 points
	Ppui.on('.ds-points2', 'click', function(){
		//시작점
		the.drawPoint(xyz1.lon, xyz1.lat);
		//끝점
		the.drawPoint(xyz2.lon, xyz2.lat);
		//선
		the.drawLine(xyz1, xyz2);
		
	});
	
	
	
	//원/중/근경 위치 계산 & 점표시
	Ppui.on('.ds-points3', 'click', function(){
		let viewPoint = the.calcViewPoint(xyz1, xyz2);
		
		//점 표시
		for(let i=0; i<3; i++){
			the.drawPoint(viewPoint[i].lon, viewPoint[i].lat);
		}
		
	});

	//원경 클릭 이벤트
	Ppui.on('.ds-movetop1', 'click', function(){
		//
		let heading = Ppmap.getHeading(xyz1, xyz2);
		
		//
		const xyz = Pp.extend(the.getViewPoint(ViewPoint.A), {'alt':30.0});
		//
		const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
		//
		Ppmap.flyTo(xyz, hpr, {'duration':0.5});
		
	});

	//중경 클릭 이벤트
	Ppui.on('.ds-movetop2', 'click', function(){
		let heading = Ppmap.getHeading(xyz1, xyz2);
		
		//
		const xyz = Pp.extend(the.getViewPoint(ViewPoint.B), {'alt':30.0});
		//
		const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
		//
		Ppmap.flyTo(xyz, hpr, {'duration':0.5});		
	});
		
		
	//근경 클릭 이벤트
	Ppui.on('.ds-movetop3', 'click', function(){
		let heading = Ppmap.getHeading(xyz1, xyz2);
		
		//
		const xyz = Pp.extend(the.getViewPoint(ViewPoint.C), {'alt':30.0});
		const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
		//
		Ppmap.flyTo(xyz, hpr, {'duration':0.5});
		
	});
	
	
	//
	console.log('SkylineObj', '<<.setEventHandler');
		
};


/**
 * base64를 blob으로 변환
 * @param {string} base64
 * @param {string} contentType
 * @returns {Blob}
 */
function base64ToBlob(base64, contentType){
	if(Pp.isEmpty(base64)){
		console.log('<<.base64ToBlob - empty base64');
		return null;
	}

	//
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

SkylineObj.prototype.drawTwoPointLine = function(xyz1, xyz2) {
	//시작점
	const point1 = this.drawPoint(xyz1.lon, xyz1.lat);
	//끝점
	const point2 = this.drawPoint(xyz2.lon, xyz2.lat);
	//선
	const line = this.drawLine(xyz1, xyz2);

	return {
		point1: point1,
		point2: point2,
		line: line
	}
}


/**
 * 두 위치로부터 원/중/근경 위치 계산 & 지도위에 점으로 표시
 * @param {object} xyz1 위치1
 * @param {object} xyz2 위치2
 * @returns {void}
 */
SkylineObj.prototype.execCalcViewPoint = function(xyz1, xyz2) {
	debugger;

	//
	if(Pp.isEmpty(xyz1) || Pp.isEmpty(xyz2)){
		console.log('<<.execCalcViewPoint - empty xyz1 or xyz2');
		return;
	}

	const that = this;
	let viewPoint = that.calcViewPoint(xyz1, xyz2);

	//점 표시
	for(let i=0; i<3; i++){
		that.drawPoint(viewPoint[i].lon, viewPoint[i].lat);
	}

	//
	this._xyz1 = xyz1;
	this._xyz2 = xyz2;
}


/**
 * 1. 3점 위치 지도 캡처
 * 2. 스카이라인 분석
 * 3. 모달창에 캡처 이미지 & 스카이라인 이미지 표시
 * @param {function} callbackFn 콜백함수
 * @returns {void}
 */
SkylineObj.prototype.autoCaptureAllMenual = function(callbackFn) {
	const that = this;


	//clear 스카이라인 이미지
	that.clearAllSkylineImg();

	try{
		//모든 점 자동 캡처
		that.autoCaptureAll(function(){
			console.log('<<.autoCaptureAll');
	
			//show modal
			that.showModal();
	
			//캡처 blob <img>에 할당
			for(let i=0; i<that.getViewPointCo(); i++){
				that.setCaptureImgSrc(i, that.getCaptureBlob(i));
				that.setCaptureThumbImgSrc(i, that.getCaptureBlob(i));
			}
	
			//모든 blob 업로드 & get 스카이라인 base64
			that.uploadBlobAndGetSkylineImageAll(function(){
				console.log('<<.uploadBlobAndGetSkylineImageAll');
	
				//
				if(Pp.isNotNull(callbackFn)){
					callbackFn();
				}
			});
		});
	}catch(e){
		console.log(new Date(), e);
		document.querySelector('body').style.cursor = 'default';
		alert('분석중 오류가 발생했습니다. 관리자에게 문의하시기 바랍니다.');
	}

	//
	that.showCaptureImage(ViewPoint.A);
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
 * @deprecated 20200902
 */
SkylineObj.prototype.setViewer = function(viewer){
	Ppmap.viewer = viewer;	
};



/**
 * 원/중/근경 xyz 구하기
 * @param {ViewPoint} gbn 옵션. 존재시 해당 xyz만 리턴, 미존재시 원/중/근경 xyz 모두 리턴
 * @returns {object} gbn 설명 참조
 */
SkylineObj.prototype.getViewPoint = function(gbn){
	if(Pp.isNotNull(gbn)){
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
	Ppmap.captureMap(function(blob){
		if(Pp.isNotNull(callbackFn)){
			callbackFn(blob);
		}
	});	
};


/**
 * 스카이라인 썸네일 이미지를 로딩 gif로 변경하기
 */
SkylineObj.prototype.clearAllSkylineImg = function(){
	let coll = Ppui.find('.ds-image-skyline-thumb');
	for(let i=0; i<coll.length; i++){
		let el = coll.item(i);

		//
		el.src = '../images/ko/Bubble-Loader-Icon-1.gif';
	}
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
	return MAGO3D_INSTANCE.getViewer().entities.add({
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
	return MAGO3D_INSTANCE.getViewer().entities.add({
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
	let heading = Ppmap.getHeading(this._xyz1, this._xyz2);
	//
	const xyz = Pp.extend(the.getViewPoint(gbn), {'alt':30.0});
	//
	const hpr = new Cesium.HeadingPitchRoll(Cesium.Math.toRadians(heading), Cesium.Math.toRadians(0), Cesium.Math.toRadians(0));
	
	//
	Ppmap.flyTo(xyz, hpr, {'duration':0.5}, function(){
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
	if(gbn >= this.getViewPointCo()){
		console.log(i, 'break');
		return;
	}
	
	//
	let fd = new FormData();
	fd.append('blob', this.getCaptureBlob(gbn));
	
	//
	Pp.submitFormData(PREDICT_SERVER_URL, fd, function(json){
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
		height: 680,
		modal: true,
		resizable: false,
		title : '경관 분석',
		show:{
			'effect':'fade',
			'duration':500
		},
		buttons:{
			'저장':function(){
				skylineObj.saveAllImages();
			},
			'닫기':function(){
				$(this).dialog('close')
			}
		}
	}).dialog('open');
};



/**
 * 캡처한 이미지(blob)를 <img>에 설정
 * @param {ViewPoint} gbn
 * @param {Blob} blob
 */
SkylineObj.prototype.setCaptureImgSrc = function(gbn, blob){
	let img = Ppui.find('.ds-image[data-index="'+gbn+'"]');
	if(Pp.isNull(img)){
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
	let img = Ppui.find('.ds-image-thumb[data-index="'+gbn+'"]');
	if(Pp.isNull(img)){
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
	let img = Ppui.find('.ds-image-skyline[data-index="'+gbn+'"]');
	if(Pp.isNull(img)){
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
	let img = Ppui.find('.ds-image-skyline-thumb[data-index="'+gbn+'"]');
	if(Pp.isNull(img)){
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
	Ppui.removeClass(['.ds-image', '.ds-image-skyline'], 'on');
	Ppui.removeClass(['.ds-image-thumb', '.ds-image-skyline-thumb'], 'on');
	
	//
	Ppui.addClass('.ds-image[data-index="'+gbn+'"]', 'on');
	Ppui.addClass('.ds-image-thumb[data-index="'+gbn+'"]', 'on');
};


/**
 * 스카이라인 이미지 <img> show
 * @param {ViewPoint|number} gbn
 */
SkylineObj.prototype.showSkylineImage = function(gbn){
	//
	Ppui.removeClass(['.ds-image', '.ds-image-skyline'], 'on');
	Ppui.removeClass(['.ds-image-thumb', '.ds-image-skyline-thumb'], 'on');
	
	//
	Ppui.addClass('.ds-image-skyline[data-index="'+gbn+'"]', 'on');
	Ppui.addClass('.ds-image-skyline-thumb[data-index="'+gbn+'"]', 'on');

};


/**
 * 캡처 이미지, 스카이라인 이미지 모두를 서버에 저장
 */
SkylineObj.prototype.saveAllImages = function(){
	if(!confirm('저장하시겠습니까?')){
		return;
	}
	
	//
	let url = 'http://localhost:8090/svc/landscape_anals/saveCaptureAndSkylineImages';
	let fd = new FormData();
	
	for(let i=0; i<this.getViewPointCo(); i++){
		fd.append('capture['+i+']', this.getCaptureBlob(i));
		fd.append('skyline['+i+']', this.getSkylineBlob(i));
	}
	
	//
	Pp.submitFormData(url, fd, function(){
		toastr.info('저장되었습니다.');
	});
};

$(document).ready(function() {
	new lsDropDownList().init();
});