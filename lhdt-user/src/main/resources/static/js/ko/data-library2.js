

/**
 * 데이터 라이브러리
 */
let DataLibrary2Obj = function(){
	//jquery instance
	this.$container = null;
	
	//선택된 데이터 라이브러리
	this.selectedDataLibrary = null;
	
	//변경할 선택된 데이터 라이브러리
	this.selectedTobeDataLibrary = null;
	
	//선택된 툴
	this.selectedTool = DataLibrary2Obj.Tool.NONE;
	
	this.handler = null;
	
	this.rotate = null;
	
	this.selectByPolygon = null;
	
	//caching 데이터 라이브러리 정보
	this.cacheDataLibraries = [];
	
	//나의 데이터 모달 인스턴스
	this.dataLibrary2MyObjRef = null;
};

/**
 * static
 */
DataLibrary2Obj.Tool = {
	NONE : {text: '없음', id: 'none'},
	SELECT : {text: '선택', id: 'select'},
	POINT : {text: '점', id: 'point'},
	LINE : {text: '선', id: 'line'},
	DELETE : {text: '삭제', id: 'delete'},
	MOVE : {text: '이동', id: 'move'},
	ROTATE : {text: '회전', id: 'rotate'},
	SELECTBYPOLYGON : {text: '영역선택', id: 'selectbypolygon'},
};



/**
 * static
 */
DataLibrary2Obj.prototype.getToolByText = function(toolText){
	let keys = Object.keys(DataLibrary2Obj.Tool);
	for(let i=0; i<keys.length; i++){
		let k = keys[i];
		
		if(toolText == DataLibrary2Obj.Tool[k].text){
			return DataLibrary2Obj.Tool[k];
		}
	}
	
	return null;
};


/**
 * static
 */
DataLibrary2Obj.prototype.getToolById = function(toolId){
	let keys = Object.keys(DataLibrary2Obj.Tool);
	for(let i=0; i<keys.length; i++){
		let k = keys[i];
		
		if(toolId == DataLibrary2Obj.Tool[k].id){
			return DataLibrary2Obj.Tool[k];
		}
	}
	
	return null;
};


/**
 * 초기화
 */
DataLibrary2Obj.prototype.init = function(dataLibrary2MyObjRef){
	this.$container = $('.modelerContent');
	
	this.dataLibrary2MyObjRef = dataLibrary2MyObjRef;
	
	
	//회전 instance
	this.rotate = new Mago3D.RotateInteraction();
	Ppmap.getManager().interactionCollection.add(this.rotate);
	this.rotate.setTargetType('f4d');
	
	
	//영역으로 선택 instance
	this.selectByPolygon = new PolygonDrawer(Ppmap.getViewer(),  this.selectByPolygonCallback);
	
	
	this.setTool(DataLibrary2Obj.Tool.NONE);
	
	
	this.setEventHandler();
	
	
	console.log(this, '<<.init');
};


/**
 * 이벤트 등록
 */
DataLibrary2Obj.prototype.setEventHandler = function(){
	let self = this;
	
	
	//데이터 라이브러리 선택 팝업 호출	
	this.$container.find('.open-select-data-library-modal').click(function(){
		//모달창 표시
		dataLibrary2SelectDataObj.show('asis');		
	});
	
	
	//선택된 데이터 라이브러리 삭제
	this.$container.find('.delete-selected-data-library').click(function(){
		if(!confirm('삭제하시겠습니까?')	){
			return;
		}
		
		self.selectedDataLibrary = null;
		
		self.renderSelectedDataLibrary();
		
	});
	
	
	//도구 클릭
	this.$container.find('[class*=ds-tool]').click(function(){
		//
		let beforeTool = self.selectedTool;
		
		//
		self.$container.find('[class*=ds-tool]').removeClass('active');
		$(this).addClass('active');
		
		
		//
		let afterTool = self.getToolById($(this).data('id'));		

		
		self.setTool(DataLibrary2Obj.Tool.NONE);
	
		
		if(beforeTool == afterTool){
			self.$container.find('[class*=ds-tool]').removeClass('active');
			return;			
		}
		
		self.setTool(afterTool);
		
	});
	
	
	//변경 데이터 라이브러리 선택 모달 실행
	this.$container.find('.open-select-tobe-data-library').click(function(){
		dataLibrary2SelectDataObj.show('tobe');
	});
	
	
	//변경 데이터 라이브러리 삭제
	this.$container.find('.delete-tobe-data-library').click(function(){
		if(!confirm('삭제하시겠습니까?')){
			return;
		}
		
		
		self.selectedTobeDataLibrary = null;
		self.renderSelectedTobeDataLibrary();
	});
	
	
	//변경
	this.$container.find('.change-data-library').click(function(){
		
		if(Pp.isEmpty(self.selectedDataLibrary)){
			toastr.warning('데이터 라이브러리를 선택하시기 바랍니다. 변경을 취소합니다.');
			return;
		}
		
		
		let totcnt = self.$container.find('.selected-data-library-list .totcnt').html();
		
		if('0' == totcnt){
			toastr.warning('선택된 데이터 라이브러리가 없습니다. 변경을 취소합니다.');
			return;
		}
		
		
		if(Pp.isEmpty(self.selectedTobeDataLibrary)){
			toastr.warning('변경할 데이터 라이브러리를 선택하시기 바랍니다. 변경을 취소합니다.');
			return;
		}
		
		
		let msg = '[' + self.selectedDataLibrary.dataLibraryName + ']';
		msg += '을(를) ';
		msg += '[' + self.selectedTobeDataLibrary.dataLibraryName + ']';
		msg += '(으)로 변경하시겠습니까?';
		if(!confirm(msg)){
			reutrn;
		}
		
		//TODO 변경
	});
	
	
	//나의 데이터 관리 > 신규
	this.$container.find('.new-my-data-library').click(function(){
		if(!confirm('지도상의 데이터 라이브러리를 삭제한 후 신규로 작업하시겠습니까?')){
			return;	
		}
		
		
		//지도상의 모든 데이터 라이브러리 정보 삭제
		
		
		//컨텐트 영역의 정보 초기화
	});	
	
	
	
	
	//나의 데이터 관리 > 불러오기
	this.$container.find('.get-my-data-library').click(function(){
		self.dataLibrary2MyObjRef.open();
	});
	
	
	
	//나의 데이터 관리 > 저장하기
	this.$container.find('.save-my-data-library').click(function(){
		if(!confirm('저장하시겠습니까?')){
			return;
		}
		
		//지도위의 데이터 라이브러리 정보 추출
		
		//db에 저장하기 위해 api 호출
	});
	
	
	
};



/**
 *
 */
DataLibrary2Obj.prototype.setTool = function(tool){
	this.selectedTool = tool;
	console.log('setTool', this.selectedTool);
	
	
	this.$container.find('[class*=ds-tool]').removeClass('active');
	this.$container.find('.ds-tool-' + this.selectedTool.id).addClass('active');
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.NONE){
		this.processNone();
	}
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.POINT){
		this.processPoint();
		toastr.info('점을 이용하여 지도상에 데이터 라이브러리를 생성합니다.');
	}
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.LINE){
		this.processLine();
		toastr.info('선을 이용하여 지도상에 데이터 라이브러리를 생성합니다.');
	}
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.SELECT){
		this.processSelect();
		toastr.info('지도상에서 데이터 라이브러리를 선택(클릭)하시기 바랍니다.');
	}
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.SELECTBYPOLYGON){
		this.processSelectbypolygon();
		toastr.info('지도상에서 영역을 선택하시기 바랍니다.');
	}
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.MOVE){
		this.processMove();
		toastr.info('지도상의 데이터 라이브러리를 선택(클릭)한 후 이동하시기 바랍니다.');
	}
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.ROTATE){
		this.processRotate();
		toastr.info('지도상의 데이터 라이브러리를 선택(클릭)한 후 회전하시기 바랍니다.');
	}
	
	
	if(this.selectedTool == DataLibrary2Obj.Tool.DELETE){
		this.processDelete();
		toastr.info('지도상의 데이터 라이브러리를 선택(클릭)하면 삭제됩니다.');
	}
	
};



/**
 * 도구 - NONE 처리
 */
DataLibrary2Obj.prototype.processNone = function(){
	if(DataLibrary2Obj.Tool.NONE != this.selectedTool){
		return;
	}
	
	Ppmap.getManager().defaultSelectInteraction.setActive(false);
	Ppmap.getManager().defaultTranslateInteraction.setActive(false);
	Ppmap.getManager().off(Mago3D.MagoManager.EVENT_TYPE.SELECTEDF4D, this.selectedf4dCallback);
	Ppmap.getManager().off(Mago3D.MagoManager.EVENT_TYPE.DESELECTEDF4D, this.deselectedf4dCallback);
	
	this.rotate.setActive(false);
	this.selectByPolygon.setActive(false);
	
	if(Pp.isNotEmpty(Ppmap.getManager().selectionManager)){
		Ppmap.getManager().selectionManager.clearCurrents();
	}
	
	//
	if (Pp.isNotNull(this.handler) && !this.handler.isDestroyed()) {
		this.handler.destroy();
	}
	
	
	
	this.renderSelectedDataLibraries([]);

};


/**
 * 도구 - 점으로생성 처리
 */
DataLibrary2Obj.prototype.processPoint = function(){
	//
	let self = this;
	
	if(DataLibrary2Obj.Tool.POINT != this.selectedTool){
		return;
	}
	
	if(Pp.isEmpty(this.selectedDataLibrary)){
		this.setTool(DataLibrary2Obj.Tool.NONE);	
		toastr.warning('데이터 라이브러리를 선택하시기 바랍니다.');
		return;
	}
	
	
	//
	self.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);
	//클릭
	self.handler.setInputAction(function(event){
		if(DataLibrary2Obj.Tool.POINT == self.selectedTool){
			//
			let lonLat = Ppmap.Convert.ctsn2ToLonLat(event.position);
			
			//데이터 라이브러리 표시
			self.showDataLibraryAtMap(lonLat);
		}
	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);
	
	//오른쪽 클릭
	self.handler.setInputAction(function(){
		if(DataLibrary2Obj.Tool.POINT != self.selectedTool){
			return;
		}
			
		//
		self.$container.find('.ds-tool-none').trigger('click');
		//console.log('point right clicked');
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};

/**
 * 도구 - 선으로생성 처리
 */
DataLibrary2Obj.prototype.processLine = function(){
	    //LonLat을 지도에 표시하기 위한 좌표형태로 변환
	let _toDataPositions = function(lonLats){
		let arr=[];

		//
		for(let i=0; i<lonLats.length-1; i++){
			//
			let p1 = {
				'longitude': lonLats[i].lon,
				'latitude': lonLats[i].lat,
				'altitude': 0,
			}
			//
			let p2 = {
				'longitude': lonLats[i+1].lon,
				'latitude': lonLats[i+1].lat,
				'altitude': 0,
			}

            //10m 간격
			let dataPositions = Mago3D.GeographicCoordSegment.getArcInterpolatedGeoCoords(p1, p2, 10);
			//
			arr = arr.concat(dataPositions);			
		}

		//
		return arr;
	};
	
	
	
	if(DataLibrary2Obj.Tool.LINE != this.selectedTool){
		return;
	}
	
	if(Pp.isEmpty(this.selectedDataLibrary)){
		this.setTool(DataLibrary2Obj.Tool.NONE);	
		toastr.warning('데이터 라이브러리를 선택하시기 바랍니다.');
		return;
	}
	
	
	//
	let self = this;
	
	//
	let points = [];

	//
	self.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

	//클릭
	self.handler.setInputAction(function(event){
		if(DataLibrary2Obj.Tool.LINE != self.selectedTool){
			return;
		}
		
		//
		let lonLat = Ppmap.Convert.ctsn2ToLonLat(event.position);
		points.push(lonLat);
		
		//
		Ppmap.createPoint('data-library-tool-line-point', lonLat.lon, lonLat.lat);
		
		
	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);
	
	
		//오른쪽 클릭
	self.handler.setInputAction(function(event){
			if(DataLibrary2Obj.Tool.LINE != self.selectedTool){
				return;
			}
			
			// 점, 선 삭제
			Ppmap.removeAll();		
			
			//
			let dataPositions = _toDataPositions(points);
			//
			for(let i=0; i<dataPositions.length; i++){
				let d = dataPositions[i];
				// 데이터 라이브러리 표시
				self.showDataLibraryAtMap(Ppmap.Convert.toLonLat(d.longitude, d.latitude));
			}
	
			//
			self.setTool(DataLibrary2Obj.Tool.NONE);
		//console.log('line right clicked');
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
	
	
	//이동
	self.handler.setInputAction(function(event){
		// 선 그리기
		if(0 == points.length){
			return;
		}


		//
		Ppmap.removeEntity(window['line'+points.length]);

		//
		let lonLat = Ppmap.Convert.ctsn2ToLonLat(event.endPosition);
		//
		if(Pp.isEmpty(lonLat.lon) || Pp.isEmpty(lonLat.lat)){
			return;
		}
		
		let arr=[];
		arr.push(points[points.length-1].lon);
		arr.push(points[points.length-1].lat);
		arr.push(lonLat.lon);
		arr.push(lonLat.lat);
		//console.log(arr);
		
		//
		let entity = MAGO3D_INSTANCE.getViewer().entities.add({
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
		Ppmap.removeEntity(window['line'+points.length]);
		window['line'+points.length] = entity;
		
	}, Cesium.ScreenSpaceEventType.MOUSE_MOVE);
};


/**
 * 도구 - 선택 처리
 */
DataLibrary2Obj.prototype.processSelect = function(){
	if(DataLibrary2Obj.Tool.SELECT != this.selectedTool){
		return;
	}
	
	let self = this;
	
	
	
	Ppmap.getManager().defaultSelectInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;
	Ppmap.getManager().on(Mago3D.MagoManager.EVENT_TYPE.SELECTEDF4D, self.selectedf4dCallback);
	Ppmap.getManager().on(Mago3D.MagoManager.EVENT_TYPE.DESELECTEDF4D, self.deselectedf4dCallback);
	

	//
	self.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);	
	
	
	////오른쪽 클릭
	self.handler.setInputAction(function(){
		//
		if (DataLibrary2Obj.Tool.SELECT != self.selectedTool) {
			return;
		}
		
		//
		self.setTool(DataLibrary2Obj.Tool.NONE);
		
		//console.log('select right clicked');
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};


/**
 * 도구 - 영역선택 처리
 */
DataLibrary2Obj.prototype.processSelectbypolygon = function(){

	let self = this;
	
	
	if(DataLibrary2Obj.Tool.SELECTBYPOLYGON != this.selectedTool){
		return;
	}
	
	
	this.selectByPolygon.setActive(true);
	
	//
	self.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);
	
	
	//오른쪽 클릭
	self.handler.setInputAction(function(){
		if (DataLibrary2Obj.Tool.SELECTBYPOLYGON != self.selectedTool) {
			return;
		}
		
		//
		self.setTool(DataLibrary2Obj.Tool.NONE);
		//console.log('delete right clicked');
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};


/**
 * 도구 - 이동 처리
 */
DataLibrary2Obj.prototype.processMove = function(){
	if(DataLibrary2Obj.Tool.MOVE != this.selectedTool){
		return;
	}
	
	
	//
	let self = this;

	//
	Ppmap.getManager().defaultTranslateInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultTranslateInteraction.setActive(true);
	
	//
	Ppmap.getManager().defaultSelectInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;
	

	//
	self.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);	

	//오른쪽 클릭
	self.handler.setInputAction(function(){
		if(DataLibrary2Obj.Tool.MOVE != self.selectedTool){
			return;
		}
		
		//
		self.setTool(DataLibrary2Obj.Tool.NONE);
		//console.log('move right clicked');
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};


/**
 * 도구 - 회전 처리
 */
DataLibrary2Obj.prototype.processRotate = function(){
	let self = this;
	
	
	if(DataLibrary2Obj.Tool.ROTATE != this.selectedTool){
		return;
	}
	
	
	/**
	 * 선택된 객체를 마우스로 회전시키는 기능
	 */
	this.rotate.setActive(true);
	

	Ppmap.getManager().defaultSelectInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;
	Ppmap.getManager().on(Mago3D.MagoManager.EVENT_TYPE.SELECTEDF4D, self.selectedf4dCallback);
	


	//
	self.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);
	//오른쪽 클릭
	self.handler.setInputAction(function(){
		if (DataLibrary2Obj.Tool.ROTATE != self.selectedTool) {
			return;
		}
		
		//
		self.setTool(DataLibrary2Obj.Tool.NONE);
		//console.log('delete right clicked');
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};


/**
 * 도구 - 삭제 처리
 */
DataLibrary2Obj.prototype.processDelete = function(){
	//
	let self = this;
		
	if(DataLibrary2Obj.Tool.DELETE != this.selectedTool){
		return;
	}
	
	//
	Ppmap.getManager().defaultSelectInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;
	Ppmap.getManager().on(Mago3D.MagoManager.EVENT_TYPE.SELECTEDF4D, self.selectedf4dCallback);

	//
	self.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);
		
	
	//오른쪽 클릭
	self.handler.setInputAction(function(){
		if (DataLibrary2Obj.Tool.DELETE != self.selectedTool) {
			return;
		}
		
		//
		self.setTool(DataLibrary2Obj.Tool.NONE);
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};


DataLibrary2Obj.prototype.showDataLibraryAtMap = function(lonLat){
	if(Pp.isNull(this.selectedDataLibrary)){
		 return;
	 }
	 //console.log(lonLat);
	 
	 
	 //
	 if(!Ppmap.getManager().isExistStaticModel(this.selectedDataLibrary.dataLibraryId)) {
		let model = {};
		model.projectId = this.selectedDataLibrary.dataLibraryId;
		model.projectFolderName = this.selectedDataLibrary.dataLibraryPath;
		
		
		//to fix
		model.projectFolderName = model.projectFolderName.split(this.selectedDataLibrary.dataLibraryKey)[0];
		model.projectFolderName = model.projectFolderName.replace(/\/+$/, '');
		model.buildingFolderName = 'F4D_'+this.selectedDataLibrary.dataLibraryKey;

		//
		Ppmap.getManager().addStaticModel(model);
	}
	
	//uid는 pk값은 역할을 함. 이 값이 중복되면 해당 데이터는 추가되지 않음
	let uid = Pp.createUid('' + parseInt(Math.random() * 1000));
	//
	Ppmap.getManager().instantiateStaticModel({
		projectId : this.selectedDataLibrary.dataLibraryId,
		instanceId : uid,
		longitude : lonLat.lon,
		latitude : lonLat.lat,
		height : 0
	});
}


/**
 * f4d 객체가 선택해제되면 호출됨
 */
DataLibrary2Obj.prototype.deselectedf4dCallback = function(){
	var selectionManager = Ppmap.getManager().selectionManager;
	var selected = Ppmap.getManager().selectionManager.getSelectedF4dNode();
	
	//console.log(selectionManager, selected);
	
	if ($('button.ds-tool-select').hasClass('active')) {
		if(!Pp.isEmpty(selected)){
			return;
		}
		
		dataLibrary2Obj.renderSelectedDataLibraries([]);
	}
}



/**
 * f4d 객체가 선택되면 호출됨
 */
DataLibrary2Obj.prototype.selectedf4dCallback = function(){
	var selectionManager = Ppmap.getManager().selectionManager;
	var selected = Ppmap.getManager().selectionManager.getSelectedF4dNode();
	
	
	//삭제이면
	if ($('button.ds-tool-delete').hasClass('active')) {
		if (Pp.isEmpty(selected)) {
			return;
		}
		
		if(!confirm('삭제하시겠습니까?')){
			return;
		}

		//
		MAGO3D_INSTANCE.getF4dController().deleteF4dMember(selected.data.projectId, selected.data.nodeId);
		Ppmap.getManager().defaultSelectInteraction.clear();
		selectionManager.clearCurrents();
		return;
	}
	
	//선택이면
	if ($('button.ds-tool-select').hasClass('active')) {
		let datas=[];
		
		if (!Pp.isEmpty(selected)) {
			datas.push({projectId: selected.data.projectId,
						nodeId: selected.data.nodeId,
						dataLibraryName: dataLibrary2Obj.getDataLibrary(selected.data.projectId).dataLibraryName});
		}
		
					
					
		//렌더링 선택된 데이터 라이브러리 목록
		dataLibrary2Obj.renderSelectedDataLibraries(datas);
	}
};


/**
 * 지도위에 영역이 선택되면 호출됨
 */
DataLibrary2Obj.prototype.selectByPolygonCallback = function(cartesians){
	/**
	 * Cesium Cartesian3의 array를 이욯하여 Mago3D.Polygon2D 객체 생성
	 */
	var polygon2D = Mago3D.Polygon2D.makePolygonByCartesian3Array(cartesians);
	
	
	/**
	 * selectionManager는 mago3d에서 선택된 데이터들을 관리하는 객체.
	 * selectionByPolygon2D 메소드를 이용하여 영역에 포함된 데이터를 찾을 수 있음.
	 */
	var selectionManager = Ppmap.getManager().selectionManager;
	var selected = selectionManager.selectionByPolygon2D(polygon2D, 'f4d');
	//console.log(selected);
	
	let datas=[];
	for(let i=0; i<selected.length; i++){
		let d = selected[i];
		
		datas.push({projectId: d.data.projectId, 
					nodeId: d.data.nodeId, 
					dataLibraryName: dataLibrary2Obj.getDataLibrary(d.data.projectId).dataLibraryName});
	}
	
	
	dataLibrary2Obj.renderSelectedDataLibraries(datas);
	
};



/**
 * 지도상에서 선택된 데이터 라이브러리 목록을 화면에 목록으로 표시
 */
DataLibrary2Obj.prototype.renderSelectedDataLibraries = function(datas){
	let _totcnt = function(){
		let totcnt = dataLibrary2Obj.$container.find('.selected-data-library-list table tbody tr').not('tr.no-data-found').length
		dataLibrary2Obj.$container.find('.selected-data-library-list .totcnt').html(totcnt);
		
		if(0 == totcnt){
			let s = '<tr class="no-data-found"><td colspan="2" class="text-center">데이터가 없습니다.</td></tr>';
			dataLibrary2Obj.$container.find('.selected-data-library-list table tbody').html(s);
		}
	};
	
	let source = $('#selected-data-library-list-template').html();
	let template = Handlebars.compile(source);
	let html = template({datas: datas});
	dataLibrary2Obj.$container.find('.selected-data-library-list').html(html);
	
	//선택해제 클릭 이벤트
	dataLibrary2Obj.$container.find('.selected-data-library-list .deselect').click(function(){
		let projectId = $(this).data('project-id');
		let nodeId = $(this).data('node-id');
		
		//TODO
	});
	
	_totcnt();
	
	
	
	//삭제 클릭 이벤트
	dataLibrary2Obj.$container.find('.selected-data-library-list .del').click(function(){
		if(!confirm('삭제하시겠습니까?')){
			return;
		}
		
		
		let projectId = $(this).data('project-id');
		let nodeId = $(this).data('node-id');
		
		
		MAGO3D_INSTANCE.getF4dController().deleteF4dMember(projectId, nodeId);
		//TODO 선택 표시 잔상은 아직 남아 있음. 이거 제거해야 함
		
		
		$(this).closest('tr').remove();
		
		_totcnt();
	});
};



/**
 * 데이터 라이브러리 선택 모달에서 데이터 선택하면 호출됨
 * @param {object} data 선택된 데이터 라이브러리
 */
DataLibrary2Obj.prototype.dataLibrarySelected = function(data, callFromGbn){
	if('asis' === callFromGbn){
		this.selectedDataLibrary = data;		
		this.renderSelectedDataLibrary();
	}
	
	if('tobe' === callFromGbn){
		this.selectedTobeDataLibrary = data;
		this.renderSelectedTobeDataLibrary();
	}
	
};


/**
 * 선택된 데이터 라이브러리 정보 화면에 표시
 */
DataLibrary2Obj.prototype.renderSelectedDataLibrary = function(){
	let data = this.selectedDataLibrary;
	
	if(null == this.selectedDataLibrary){
		data = {
			dataLibraryGroupName: '',
			dataLibraryName: '',
			dataLibraryThumbnail: '',
		};
	}
	
	this.$container.find('.selected-data-library-group-name').html(data.dataLibraryGroupName);
	this.$container.find('.selected-data-library-name').html(data.dataLibraryName);
	this.$container.find('.selected-data-library-thumbnail').attr('src', '../' + data.dataLibraryThumbnail);
};


/**
 * 선택된 변경할 데이터 라이브러리 정보 화면에 표시
 */
DataLibrary2Obj.prototype.renderSelectedTobeDataLibrary = function(){
	let data = this.selectedTobeDataLibrary;
	
	if(null == this.selectedTobeDataLibrary){
		data = {
			dataLibraryGroupName: '',
			dataLibraryName: '',
			dataLibraryThumbnail: '',
		};
	}
	
	this.$container.find('.selected-tobe-data-library-group-name').html(data.dataLibraryGroupName);
	this.$container.find('.selected-tobe-data-library-name').html(data.dataLibraryName);
	this.$container.find('.selected-tobe-data-library-thumbnail').attr('src', '../' + data.dataLibraryThumbnail);
};


/**
 * 데이터 라이브러리 정보 조회
 * cache에 존재하면 cache에서 리턴
 * cache에 없으면 동기 방식으로 api요청 (응답된 데이터는 cache에 저장함)
 * @param {string|number} dataLibraryId
 * @returns {object}
 */
DataLibrary2Obj.prototype.getDataLibrary = function(dataLibraryId){
	let self = this;
	
	
	for(let i=0; i<this.cacheDataLibraries.length; i++){
		let d = this.cacheDataLibraries[i];
		
		if(dataLibraryId == d.dataLibraryId){
			return d;
		}	
	}
	
	
	$.ajax({
		url: '../api/data-libraries/' + dataLibraryId,
		async: false,
		success: function(res){
			self.cacheDataLibraries.push(res);
		}
	});
	
	return this.getDataLibrary(dataLibraryId);
}



/**
 * DataLibrary2MyObj에서 호출함
 */
DataLibrary2Obj.prototype.myDataLibrarySelected = function(data){
	
};




////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////


/**
 * 데이터 라이브러리 선택 팝업
 */
let DataLibrary2SelectDataObj = function(){
	//jquery modal element
	this.$container = null;
	
	//jquery jstree instance
	this.$jstree = null;
	
	//jquery dialog instance
	this.$dialog = null;
	
	//데이터 라이브러리 목록
	this.datas = [];	
	
	this.callFromGbn = '';
	
};


DataLibrary2SelectDataObj.prototype.init = function(){
	this.$container = $('.data-library2-select-data-modal');
	
	this.setEventHandler();
	
	console.log(this, '<<.init');
};



/**
 * 이벤트 등록
 */
DataLibrary2SelectDataObj.prototype.setEventHandler = function(){
	let self = this;
	
	
	//데이터 라이브러리 명 엔터
	this.$container.find('[name=searchDataLibraryName]').keyup(function(event){
		if(13 === event.keyCode){
			self.search();
		}
	});
	
	
	//검색 버튼 클릭
	this.$container.find('button.search').click(function(){
		
		self.search();
	});
};



/**
 * 검색
 */
DataLibrary2SelectDataObj.prototype.search = function(){
	let self = this;
	
	
	if(null != self.$jstree){
		self.$jstree.jstree('deselect_all');
	}
	
	
	let p = {searchDataLibraryName: this.$container.find('[name=searchDataLibraryName]').val()};
	
	if(Pp.isEmpty(p.searchDataLibraryName)){
		return;
	}
	
	
	
	$.get('todo url', p, function(res){
		self.renderDataLibraries(res);
	});
};


/**
 * 팝업창 표시
 * @param {string} callFromGbn asis|tobe
 */
DataLibrary2SelectDataObj.prototype.show = function(callFromGbn){
	let self = this;
	
	this.callFromGbn = callFromGbn;
	
	
	
	//모달창 표시
	this.$dialog = this.$container.dialog({
		width: 800,
		height: 600,
		resizable: false,
		autoOpen: false,
		modal: true,
		title: '데이터 라이브러리 선택',
		position: {my:'left top', at:'left top', of:'.cesium-viewer'},
		buttons:[
			{
				text: '닫기',
				click: function(){
					$(this).dialog('close');
				}
			}							
		]
	}).dialog('option', 'show', {
		effect: 'fade',
		duration: 300,
	}).dialog('open');
	
	
	//splitter 초기화
	this.$container.find('#mainSplitter').height(440).split({
		orientation: 'vertical',
		limit: 100,
		position: '30%',
		onDrag: function(event){
			console.log(event);
		}
	});
	
	
	//
	this.loadDataLibraryGroups(function(res){
		self.renderTree(res);
	});
	
	
};


/**
 * 데이터 라이브러리 그룹 목록 조회
 */
DataLibrary2SelectDataObj.prototype.loadDataLibraryGroups = function(callbackFn){
	$.get('../api/data-library-groups', callbackFn);
	
};



/**
 * jstree 생성
 */
DataLibrary2SelectDataObj.prototype.renderTree = function(res){
	let self = this;
	
	//
	if(Pp.isEmpty(res._embedded) || Pp.isEmpty(res._embedded.dataLibraryGroups)){
		//todo clear tree
		return;
	}
	
	let datas = [];
	
	for(let i=0; i<res._embedded.dataLibraryGroups.length; i++){
		let d = res._embedded.dataLibraryGroups[i];
		
		datas.push({id: ''+d.dataLibraryGroupId, parent: (0===d.parent?'#':'' + d.parent), text: d.dataLibraryGroupName});	
	}
	
	
	
	//
	self.$jstree = $('.jstree-container').jstree({
		core: {
			data: datas
		}
	});
	
	$('.jstree-container').on('loaded.jstree', function(event, data){
		//모든 트리 펼치기
		$(this).jstree('open_all');
	});
	
	$('.jstree-container').on('select_node.jstree', function(event, data){
		//노드 클릭 이벤트
		let dataLibraryGroupId = data.selected[0];
		
		// 
		self.loadDataLibraries(dataLibraryGroupId, function(res){
			self.renderDataLibraries(res);
			
		});
	});
	
};





/**
 * 데이터 라이브러리 목록 조회
 * @param {string} dataLibraryGroupId
 */
DataLibrary2SelectDataObj.prototype.loadDataLibraries = function(dataLibraryGroupId, callbackFn){
	$.get('../api/data-libraries', 
		{dataLibraryGroupId: dataLibraryGroupId}, 
		callbackFn);
};


/**
 * 렌더링 데이터 라이브러리 목록 
 */
DataLibrary2SelectDataObj.prototype.renderDataLibraries = function(res){
	let self = this;
	
	
	let source = $('#data-library-list-template').html();
	let template = Handlebars.compile(source);
	this.datas=[];
	
	if(Pp.isEmpty(res._embedded) || Pp.isEmpty(res._embedded.dataLibraries)){
		this.datas = [];	
	}else{
		this.datas = res._embedded.dataLibraries;
	}
	
	let html = template({datas: this.datas});
	this.$container.find('.list').html(html);
	
	//데이터 클릭 이벤트 등록
	this.$container.find('a.link').click(function(){
		let dataLibraryId = $(this).data('data-library-id');
		
		self.dataLibrarySelected(self.getDataLibraryByDataLibraryId(dataLibraryId));
		
		
		self.$dialog.dialog('option', 'hide', {
			effect: 'fade',
			duration: 300,
		}).dialog('close');
	});
};



/**
 * 데이터 라이브러리 선택(클릭)되면 호출됨
 */
DataLibrary2SelectDataObj.prototype.dataLibrarySelected = function(data){
	dataLibrary2Obj.dataLibrarySelected(data, this.callFromGbn);
	
	this.callFromGbn = '';
};





/**
 * dataLibraryId로 dataLibrary data 조회
 * @param {string|number} dataLibraryId
 * @returns {object|null}
 */
DataLibrary2SelectDataObj.prototype.getDataLibraryByDataLibraryId = function(dataLibraryId){
	for(let i=0; i<this.datas.length; i++){
		let d = this.datas[i];
		
		if(dataLibraryId == d.dataLibraryId){
			return d;
		}
	}
	
	return null;
};


/////////////////////////////////
/////////////////////////////////
/////////////////////////////////
/////////////////////////////////
/////////////////////////////////



/**
 * 나의 데이터 모달
 * @since 20201019 init
 */
DataLibrary2MyObj = function(){
	this.$container = null;
	
	//데이터 라이브러리2 인스턴스
	this.dataLibrary2ObjRef = null;
};


/**
 * 초기화
 */
DataLibrary2MyObj.prototype.init = function(dataLibrary2ObjRef){
	this.$container = $('.my-data-library-modal');
	
	this.dataLibrary2ObjRef = dataLibrary2ObjRef;
	
	
	this.setEventHandler();
};


/**
 * 이벤트 등록
 */
DataLibrary2MyObj.prototype.setEventHandler = function(){
	let self = this;
	
	
	
	//나의 데이터 라이브러리 선택 클릭
	self.$container.find('.select-my-data-library').click(function(){
		//모달창 닫기
		self.$container.dialog('close');
		
		//데이터 조회 api
		let data = 'todo';
		
		
		dataLibrary2ObjRef.myDataLibrarySelected(data);
			
	});
	
	
	
	//나의 데이터 라이브러리 삭제 클릭
	self.$container.find('.delete-my-data-library').click(function(){
		if(!confirm('삭제하시겠습니까?')){
			return;
		}
		
		//삭제 by api
		
		
		//성공이면 
		$(this).closest('tr').remove();
	});
	
};


/**
 * 모달창 표시
 */
DataLibrary2MyObj.prototype.open = function(){
	let self = this;
	
	
	//모달창 실행
	self.$container.dialog({
		title: '나의 데이터 불러오기',
		width: 800,
		height: 600,
		position: {my:'left top', at:'left top', of:'.cesium-viewer'},
		buttons:[
		{
			text: '닫기',
			click: function(){
				$(this).dialog('close');
			}
		}]
	}).dialog('open');
	
	//데이터 목록 조회
	self.getMyDataLibrariesAsync(function(res){
		//화면에 표시
		self.renderMyDataLibraries(res);			
	});
	
};



/**
 * 나의 데이터 라이브러리 목록 조회 by api
 */
DataLibrary2MyObj.prototype.getMyDataLibrariesAsync = function(callbackFn){
	//$.get('todo url', callbackFn);
	
	callbackFn({});
};


/**
 * 데이터를 화면에 표시
 */
DataLibrary2MyObj.prototype.renderMyDataLibraries = function(res){	
	
	let source = $('#my-data-library-list-template').html();
	let template = Handlebars.compile(source);
	let datas = [];
	
	if(!Pp.isEmpty(res) && !Pp.isEmpty(res._embedded)){
		datas = res._embedded.todo;		
	}
	
		
	let html = template({datas: datas});
	this.$container.find('.list-container').html(html);
	
	
	
	//
	this.setEventHandler();
	
};




let dataLibrary2Obj = new DataLibrary2Obj();
let dataLibrary2SelectDataObj = new DataLibrary2SelectDataObj();
let dataLibrary2MyObj = new DataLibrary2MyObj();

$(document).ready(function(){
	dataLibrary2Obj.init(dataLibrary2MyObj);
	dataLibrary2SelectDataObj.init();	
	dataLibrary2MyObj.init(dataLibrary2Obj);	
});