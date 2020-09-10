/**
 * 데이터 라이브러리(모델러)
 * 반드시, pp,ppui,ppmap 로드 후 호출되어야 함
 * @author gravity
 * @since 20200907 init
 */
let ModelerObj = function(){
	//데이터 라이브러리 그룹 목록
	this.dataLibraryGroups = [];
	//데이터 라이브러리 목록
	this.dataLibraries = [];
	//
	this.selectedData = null;
	//
	this.tool = ModelerObj.Tool.NONE;
	
	console.log(new Date(), this);
}

/**
 * 도구
 */
ModelerObj.Tool = {
		'NONE':0,
		'SELECT':1,
		'POINT':2,
		'LINE':3,
		'DELETE':4,
		'MOVE':5,
};



/**
 * 초기
 */
ModelerObj.prototype.init = function(){
	let _this = this;
	
	//
	_this.setEventHandler();
	
	//그룹 목록 조회
	_this.getGroups(function(){
		_this.renderGroups();
		
		//데이터 목록 조회
		_this.getDataLibraries(function(){
			//
			_this.renderDatas(1);
		});		
	});
	
	//
	console.log('ModelerObj', '<<.init');
	
};



/**
 * 이벤트 핸들러
 */
ModelerObj.prototype.setEventHandler = function(){
	let _this = this;
	
	//그룹 선택박스 change
	Ppui.change('#data-library-group', function(){
		_this.renderDatas();
	});
		
	// 도구 - 선택 클릭
	Ppui.click('.ds-tool-select', function(){
		//
		_this.setTool(Ppui.hasClass(this, 'active') ? ModelerObj.Tool.NONE : ModelerObj.Tool.SELECT); 		
		
		
		//
		_this.processToolSelect();
	});
	
	
	// 도구 - 점 클릭
	Ppui.click('.ds-tool-point', function(){
		//
		if(Pp.isEmpty(_this.selectedData)){
			toastr.warning('데이터 라이브러리를 선택하시기 바랍니다.');
			return;
		}

		//
		_this.setTool(Ppui.hasClass(this, 'active') ? ModelerObj.Tool.NONE : ModelerObj.Tool.POINT); 		
		
		//
		_this.processToolPoint();		
	});
	
	
	// 도구 - 선 클릭
	Ppui.click('.ds-tool-line', function(){
		//
		if(Pp.isEmpty(_this.selectedData)){
			toastr.warning('데이터 라이브러리를 선택하시기 바랍니다.');
			return;
		}

		//
		_this.setTool(Ppui.hasClass(this, 'active') ? ModelerObj.Tool.NONE : ModelerObj.Tool.LINE); 		
		
		//
		_this.processToolLine();
			
	});
	
	// 도구 - 지우기 클릭
	Ppui.click('.ds-tool-delete', function(){
		//
		_this.setTool(Ppui.hasClass(this, 'active') ? ModelerObj.Tool.NONE : ModelerObj.Tool.DELETE); 		
		
		//
		_this.processToolDelete();
	});
	
	//도구 - 이동 클릭
	Ppui.click('.ds-tool-move', function(){
		//
		_this.setTool(Ppui.hasClass(this, 'active') ? ModelerObj.Tool.NONE : ModelerObj.Tool.MOVE); 		
		
		//
		_this.processToolMove();
		
	});

	//TODO 카테고리 > 변경버튼 클릭
	Ppui.on(document.querySelector('.ds-change-category'), 'click', function(){
		
	});
	
	
	//TODO my모델러 > 검색 버튼 클릭
	Ppui.on(document.querySelector('.ds-modeler-search'), 'click', function(){
		
	});
	


	//TODO my모델러 > 신규 버튼 클릭
	Ppui.on(document.querySelector('.ds-modeler-new'), 'click', function(){
		
	});
	
	
	//TODO my모델러 > 읽어오기 버튼 클릭
	Ppui.on(document.querySelector('.ds-modeler-load'), 'click', function(){
		
	});
	
	
	//TODO my모델러 > 삭제 버튼 클릭
	Ppui.on(document.querySelector('.ds-modeler-delete'), 'click', function(){
		
	});
	
	
	//TODO my모델러 > 저장 버튼 클릭
	Ppui.on(document.querySelector('.ds-modeler-save'), 'click', function(){
		
	});


};



/**
 * dataLibraryId로 데이터 조회
 * @returns {object}
 *
 */
ModelerObj.prototype.getDataById =function(dataLibraryId){
	
	//
	for(let i=0; i<this.dataLibraries.length; i++){
		let d = this.dataLibraries[i];
		
		//
		if(dataLibraryId == d.dataLibraryId){
			return d;
		}
	}
	
	//
	return null;
};



/**
 * lonLat위치에 데이터 라이브러리 추가(표시)하기
 * @param {object} data 데이터 라이브러리
 * @param {LonLat} lonLat
 */
ModelerObj.prototype.showDataLibraryAtMap = function(lonLat){
	 if(Pp.isNull(this.selectedData)){
		 console.log('<<.showDataLibraryAtMap - emtpy data', data);
		 return;
	 }
	 //console.log(lonLat);
	 
	 
	 //
	 if(!Ppmap.getManager().isExistStaticModel(this.selectedData.dataLibraryId)) {
		let model = {};
		model.projectId = this.selectedData.dataLibraryId;
		model.projectFolderName = this.selectedData.dataLibraryPath;
		
		
		//to fix
		model.projectFolderName = model.projectFolderName.split(this.selectedData.dataLibraryKey)[0];
		model.projectFolderName = model.projectFolderName.replace(/\/+$/, '');
		model.buildingFolderName = 'F4D_'+this.selectedData.dataLibraryKey;

		//
		Ppmap.getManager().addStaticModel(model);
	}
	
	//uid는 pk값은 역할을 함. 이 값이 중복되면 해당 데이터는 추가되지 않음
	let uid = Pp.createUid('' + parseInt(Math.random() * 1000));
	//
	Ppmap.getManager().instantiateStaticModel({
		projectId : this.selectedData.dataLibraryId,
		instanceId : uid,
		longitude : lonLat.lon,
		latitude : lonLat.lat,
		height : 0
	});
};

/**
 * setter tool
 * @param {ModelerObj.Tool}
 */
ModelerObj.prototype.setTool = function(tool){
	let beforeTool = this.tool;
	this.tool = tool;

	//
	if('function' === typeof(this.toolChanged)){
		this.toolChanged(beforeTool, this.tool);
	}
};

/**
 * getter tool
 * @returns {ModelerObj.Tool}
 */
 ModelerObj.prototype.getTool = function(){
	return this.tool;
};


/**
 * tool이 변경되면 호출됨
 * @param {ModelerObj.Tool}
 * @returns {void}
 */
ModelerObj.prototype.toolChanged = function(beforeTool, afterTool){
	Ppui.removeClass('[class*=ds-tool]', 'active');

	//
	let selector = '.ds-tool-' + ModelerObj.getToolName(afterTool).toLowerCase();
	
	//
    Ppui.addClass(selector, 'active');
    
    //
    if(ModelerObj.Tool.NONE === afterTool){
        //
	    Ppmap.getManager().defaultSelectInteraction.setActive(false);
    }
	
};


/**
 * 맵에서 노드 선택이 변경되면 호출됨
 * @param {Node} nodes 선택된 노드들
 */
ModelerObj.prototype.nodeSelected = function(nodes){
    if(Pp.isEmpty(nodes)){
        //
        Ppui.find('.ds-selected-data-library').value = '';
        //
        return;
    }

    //
    for(let i=0; i<nodes.length; i++){
        let d = nodes[i];

        //
        let dataLibraryId = d.data.projectId;
        let dataLibraryKey = d.data.buildingSeed.buildingId.replace(/F4D_/gi, '');

        //
        let data = this.getDataById(dataLibraryId);
        //console.log(data);

        //
        //Ppui.find('.ds-selected-data-library').value = data.dataLibraryName;
    }
};

ModelerObj.prototype.dataChanged = function(beforeData, afterData){

}


/**
 * tool 이름 리턴
 * @param {ModelerObj.Tool}
 * @returns {string}
 */
ModelerObj.getToolName = function(tool){
	let keys = Object.keys(ModelerObj.Tool);

	//
	for(let i=0; i<keys.length; i++){
		let k = keys[i];
		//
		if(tool === ModelerObj.Tool[k]){
			return k;
		}
	}

	//
	return '';
};




/**
 * 도구 - 이동
 */
ModelerObj.prototype.processToolMove = function(){
	if(ModelerObj.Tool.MOVE !== this.getTool()){
		return;
	}

	//
	let _this = this;
	//이벤트 제거
	Ppmap.removeInputAction('all');

	//
	let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);	
	
	//
	Ppmap.getManager().defaultSelectInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;

	//
	Ppmap.getManager().defaultTranslateInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultTranslateInteraction.setActive(true);
	
	//클릭 이벤트 등록
	handler.setInputAction(function(event){
		//이벤트 제거
		//
		if(ModelerObj.Tool.MOVE !== _this.getTool()){
			//
			handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
			//
			Ppmap.getManager().defaultSelectInteraction.setActive(false);
			return;	
		}
		
		//TODO 선택된 데이터 라이브러리 정보 추출

	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);

	//오른쪽 클릭
	handler.setInputAction(function(){
		//
		_this.setTool(ModelerObj.Tool.NONE);
		
		//
		handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
		handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);

		//
		Ppmap.getManager().defaultSelectInteraction.setActive(false);
		Ppmap.getManager().defaultTranslateInteraction.setActive(false);

	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};

/**
 * 도구 - 선택
 */ 
ModelerObj.prototype.processToolSelect = function(){
	if(ModelerObj.Tool.SELECT !== this.getTool()){
		return;
	}

	//
	let _this = this;
	//이벤트 제거
	Ppmap.removeInputAction('all');

	//
	let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);	
	
	//
	Ppmap.getManager().defaultSelectInteraction.setTargetType('f4d');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;
	
	//클릭 이벤트 등록
	handler.setInputAction(function(event){
		//이벤트 제거
		//
		if(ModelerObj.Tool.SELECT !== _this.getTool()){
			//
			handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
			//
			Ppmap.getManager().defaultSelectInteraction.setActive(false);
		}
		
        // 선택된 데이터 라이브러리 정보 추출
        let nodes = Ppmap.getManager().selectionManager.getSelectedF4dNodeArray();
        _this.nodeSelected(nodes);

	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);
};


/**
 * 도구 - 점
 */
ModelerObj.prototype.processToolPoint = function(){
	if(ModelerObj.Tool.POINT !== this.getTool()){
		return;
	}

	//
	let _this = this;
	Ppmap.removeInputAction('all');
	
	//
	let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);
	//클릭
	handler.setInputAction(function(event){
		if(ModelerObj.Tool.POINT !== _this.getTool()){
			handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
			handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
			return;
		}
		//
		let lonLat = Ppmap.Convert.ctsn2ToLonLat(event.position);
		
		//데이터 라이브러리 표시
		_this.showDataLibraryAtMap(lonLat);
	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);
	
	//오른쪽 클릭
	handler.setInputAction(function(event){
		_this.setTool(ModelerObj.Tool.NONE);
		
		//
		handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
		handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
		
		
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
		
};


/**
 * 도구 - 선
 */ 
ModelerObj.prototype.processToolLine = function(){
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

			let dataPositions = Mago3D.GeographicCoordSegment.getArcInterpolatedGeoCoords(p1, p2, 10);
			//
			arr = arr.concat(dataPositions);			
		}

		//
		return arr;
	};


	//
	if(ModelerObj.Tool.LINE !== this.getTool()){
		return;
	}

	//
	let _this = this;
	//
	Ppmap.removeInputAction('all');

	//
	let points = [];

	//
	let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

	//클릭
	handler.setInputAction(function(event){
		if(ModelerObj.Tool.LINE !== _this.getTool()){
			handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
			handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
			handler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
			return;
		}

		//
		let lonLat = Ppmap.Convert.ctsn2ToLonLat(event.position);
		points.push(lonLat);

		//
		Ppmap.createPoint('data-library-tool-line-point', lonLat.lon, lonLat.lat);

	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);

	//오른쪽 클릭
	handler.setInputAction(function(event){
		//
		_this.setTool(ModelerObj.Tool.NONE);
		
		// 점, 선 삭제
		Ppmap.removeAll();		

		//
		handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
		handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
		handler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
		
		//
		let dataPositions = _toDataPositions(points);
		//
		for(let i=0; i<dataPositions.length; i++){
			let d = dataPositions[i];
			// 데이터 라이브러리 표시
			_this.showDataLibraryAtMap(Ppmap.Convert.toLonLat(d.longitude, d.latitude));
		}
	}, Cesium.ScreenSpaceEventType.RIGHT_CLICK);

	//이동
	handler.setInputAction(function(event){
		// 선 그리기
		if(0 == points.length){
			return;
		}

		//
		if(ModelerObj.Tool.LINE !== _this.getTool()){
			handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
			handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
			handler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
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
 * 도구 - 삭제
 */
ModelerObj.prototype.processToolDelete = function(){
	//
	if(ModelerObj.Tool.DELETE !== this.getTool()){
		return;
	}
	
	//
	let _this = this;

	//이벤트 제거
	Ppmap.removeInputAction('all');

	//
	let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);
	
	//
	handler.setInputAction(function(event){
		if(ModelerObj.Tool.DELETE !== _this.getTool()){
			handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
			//
			Ppmap.getManager().defaultSelectInteraction.setActive(false);
			return;
		}
		
		//
		Ppmap.getManager().defaultSelectInteraction.setTargetType('f4d');
		Ppmap.getManager().defaultSelectInteraction.setActive(true);
		Ppmap.getManager().isCameraMoved = true;
		
		
		var selectionManager = Ppmap.getManager().selectionManager;
		var selectedData = Ppmap.getManager().selectionManager.getSelectedF4dNode();
		if(!selectedData) {
			console.log('선택된 데이터가 없습니다');
			return;
		}
		MAGO3D_INSTANCE.getF4dController().deleteF4dMember(selectedData.data.projectId, selectedData.data.nodeId);
		Ppmap.getManager().defaultSelectInteraction.clear();
		selectionManager.clearCurrents();
		
	}, Cesium.ScreenSpaceEventType.LEFT_CLICK);
};



/**
 * 데이터 라이브러리 > 그룹 목록 로드
 */
ModelerObj.prototype.getGroups = function(callbackFn){
	let _this = this;
	
	//	
	Pp.get('../api/data-library-groups', [], function(res){
		_this.dataLibraryGroups = res._embedded.dataLibraryGroups;
		
		//
		if(Pp.isNotNull(callbackFn)){
			//
			callbackFn(_this.dataLibraryGroups);			
		}
	});
};



/**
 * 그룹 선택박스 데이터 바인드
 */
ModelerObj.prototype.renderGroups = function(){
	let option = {
		'tkey': 'dataLibraryGroupName',	
		'vkey': 'dataLibraryGroupId',	
		'headerText': '전체',	
		'headerValue': '',	
		'append': false,	
	};
	
	//
	let datas = [];
	for(let i=0; i<this.dataLibraryGroups.length; i++){
		let d = this.dataLibraryGroups[i];
		
		//depth1만 처리
		if(1 === d.depth){
			datas.push(d);
		}
	}
	
	//
	Ppui.bindDatas('#data-library-group', datas, option);
};


/**
 * 데이터 라이브러리 > 목록 검색
 * @param {number} pageNo
 */
ModelerObj.prototype.renderDatas = function(pageNo){
	let _this = this;
	
	//하위 데이터 그룹 목록 조회
	let _children = function(dataLibraryGroupId, depth){
		//
		if(Pp.isEmpty(dataLibraryGroupId)){
			return _this.dataLibraryGroups;
		}
		
		//
		let arr=[];
		
		//
		for(let i=0; i<_this.dataLibraryGroups.length; i++){
			let d = _this.dataLibraryGroups[i];
			
			if(d.depth <= depth){
				continue;
			}
			
			//
			if(dataLibraryGroupId == d.parent){
				arr.push(d);
				arr = arr.concat(_children(d.dataLibraryGroupId, depth+1));
			}
		}
		
		
		//
		return arr;
	};
	
	//데이터 그룹으로 필터링된 데이터 목록 조회
	let _getDatasByGroup = function(dataLibraryGroupId){
		
		//
		let arr=[];
		
		//
		for(let i=0; i<_this.dataLibraries.length; i++){
			let d = _this.dataLibraries[i];
			
			//
			if(dataLibraryGroupId === d.dataLibraryGroupId){
				arr.push(d);
			}
		}
		
		//
		return arr;
	};
	
	//데이터 그룹 목록으로 필터링된 데이터 목록 조회
	let _getDatasByGroups = function(groups){
		let arr = [];
		
		//
		for(let i=0; i<groups.length; i++){
			let d = groups[i];
			if(Pp.isNull(d)){
				continue;
			}
			
			//
			arr = arr.concat(_getDatasByGroup(d.dataLibraryGroupId));
		}
		
		//
		return arr;
		
	};
	
	//데이터 페이징
	let _paging = function(datas, pageJson){
		let arr=[];
		
		//
		for(let i=pageJson.startIndex; i<=pageJson.endIndex; i++){
			arr.push(datas[i]);
		}
		
		//
		return arr;
	};
	
	//데이터 표시
	let _renderDatas = function(datas){
		//데이터 라이브러리 목록 화면에 표시
		let source = $('#data-library-template').html();
		let template = Handlebars.compile(source);
		
		//
		Handlebars.registerHelper('showGroupName', function(dataLibraryGroupId){
			for(let i=0; i<_this.dataLibraryGroups.length; i++){
				let d = _this.dataLibraryGroups[i];
				//
				if(dataLibraryGroupId == d.dataLibraryGroupId){
					return d.dataLibraryGroupName;
				}
			}
		});
		
		let html = template({'datas': pagedDatas});
		Ppui.find('.ds-modeler-list').innerHTML = html;

		//
		_this.selectedData = null;
	};
	
	//선택된 그룹 하위 모든 그룹 목록 조회
	let dataLibraryGroupId = Ppui.find('#data-library-group').value; 
	let groups = [this.getGroup(dataLibraryGroupId)]; 
	groups = groups.concat(_children(dataLibraryGroupId , 1));
	//그룹에 속하는 모든 데이터 조회
	let datas = _getDatasByGroups(groups);
	//페이징
	let pageJson = Pp.paginate(datas.length, (pageNo?pageNo:1), 10, 5);
	//데이터 페이징
	let pagedDatas = _paging(datas, pageJson);
	//데이터 표시
	_renderDatas(pagedDatas);
	//tr 클릭 이벤트 등록
	Ppui.click('table.ds-data-library-list > tbody > tr', function(){
		let el = Ppui.child(this, '[name=dataLibraryId]');
		el.checked = true;
		//
		_this.selectedData = _this.getDataById(el.value);

		//
		Ppui.removeClass('table.ds-data-library-list > tbody > tr', 'on');
		Ppui.addClass(this, 'on');
	});
	
	
	//페이징 html 표시
	DS.pagination(datas.length, (pageNo?pageNo:1), $('.pagination'), function(_pageNo){
		//console.log(_pageNo);
		_this.renderDatas(_pageNo);
	});
		
};


/**
 * 데이터 라이브러리 그룹 조회
 * @param {string|number} dataLibraryGroupId
 * @returns {object}
 */
ModelerObj.prototype.getGroup = function(dataLibraryGroupId){
	for(let i=0; i<this.dataLibraryGroups.length; i++){
		let d = this.dataLibraryGroups[i];
		
		//
		if(dataLibraryGroupId == d.dataLibraryGroupId){
			return d;
		}
	} 
	
	//
	return null;
};


/**
 * 데이터 라이브러리 목록
 */
ModelerObj.prototype.getDataLibraries = function(callbackFn){
	let _this = this;
	Pp.get('../api/data-libraries', [], function(res){
		_this.dataLibraries = res._embedded.dataLibraries;
		
		//
		if(Pp.isNotNull(callbackFn)){
			callbackFn(_this.dataLibraries);
		}
	});
};

//TODO 데이터 라이브러리 > 목록 > 라디오버튼 클릭



//TODO 카테고리 > 변경전 선택박스 로드
//TODO 카테고리 > 변경후 선택박스 로드


//TODO 속성 > 속정정보 표시



//TODO my모델러 > 검색어 입력
//TODO my모델러 > 목록 표시
//TODO my모델러 > 목록 > 라디오 버튼 클릭






//
let mobj = new ModelerObj();

//TODO 모델러(데이터 라이브러리) 메뉴 선택시에 호출되도록 수정해야 함
window.addEventListener('load', function(){
    let intvl = setInterval(function(){
        if(Pp.isNotNull(MAGO3D_INSTANCE)){
            clearInterval(intvl);
            //
            mobj.init();	    
        }
    }, 500);
});
