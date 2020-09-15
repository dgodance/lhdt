/**
 * 디자인 레이어
 * @author gravity
 * @since 20200910 init
 */
const DesignLayerObj = function(){
    //도시 그룹 목록
    this.urbanGroups = [];
    //디자인 레이어 그룹 목록
    this.groups = [];
    //디자인 레이어 목록
    this.datas = [];
    //선택된 도구
    this.tool = DesignLayerObj.Tool.NONE;
    //intersection한 필지 정보 목록
    this.lands=[];
    //체크된 필지 레이어 정보
    this.landLayer = {};

    //
    this.rotate = null;
    //
    this.upanddown = null;
    //
    this.handler = null;
};

/**
 * 도구 종류
 */
DesignLayerObj.Tool = {
    NONE: 0,
    SELECT: 1,
    POINT: 2,
    LINE: 3,
    DELETE: 4,
    MOVE: 5,
    ROTATE: 6,
    UPDOWN: 7,
    INTERSECTION : 8,
    LANDUPDOWN : 9,
};

/**
 * 초기
 */
DesignLayerObj.prototype.init = function(){
    this.setEventHandler();

    //
    this.getUrbanGroups();
    this.getDesignLayerGroups();
    this.getDesignLayers();

    //
    let datas = this.getUrbanGroupsByParent(0);
    this.renderUrbanGroup('#urban-group1', datas);

    //
    console.log('DesignLayerObj', '<<.init');
};


/**
 * 이벤트 등록
 */
DesignLayerObj.prototype.setEventHandler = function(){
    let _this = this;


    /**
     * 도시 그룹1 change
     */
    Ppui.change('#urban-group1', function(){
        //
        let datas = _this.getUrbanGroupsByParent(this.value);
        //도시 그룹2 렌더링
        _this.renderUrbanGroup('#urban-group2', datas);
    });

    /**
     * 도시 그룹2 change
     */
    Ppui.change('#urban-group2', function(){
        let urbanGroupId = this.value;
        //디자인 레이어 목록 렌더링
        _this.renderDesignLayersByUrbanGroupId(urbanGroupId);
    });


    /**
     * 도구 - 선택
     */
    Ppui.click('[class*=design-layer-tool]', function () {
        //
        let afterTool = Ppui.hasClass(this, 'active') ? ModelerObj.Tool.NONE : _this.getToolByEl(this);
        //
        _this.setTool(DesignLayerObj.Tool.NONE);
        _this.setTool(afterTool);
    });


    /**
     * 전체 필지 높이 up/down
     */
    Ppui.click('.ds-toggle-land-updown', function(){

        /**
         * on된 디자인 레이어와 관련된 정보(디자인 레이어 목록, imageryLayer 목록)
         */
        let _getDatas = function(){
            let coll = Ppui.find('[name=design-layer-id]:checked');
            if(Pp.isEmpty(coll)){
                return [];
            }

            //
            if(coll instanceof Element){
                coll = [coll];
            }

            let json = {};
            for(let i=0; i<coll.length; i++){
                let el = coll[i];
                let designLayerId = el.value;
                let data = _this.getDataById(designLayerId)

                //
                if(null == data){
                    continue;
                }

                //
                if('land' !== data.designLayerGroupType){
                    //continue;
                }

                if(Pp.isNull(_this.landLayer[designLayerId])){
                    continue;
                }

                //
                json[designLayerId] = {
                    'data': data,
                    'layer': _this.landLayer[designLayerId],
                }
            }

            //
            return json;
        };


        /**
         * 필지 높이 up/down
         * @param {Object} d {'data':Object, 'layer':ImageryLayer}
         * @param {boolean} isShow
         */
        let _updownFeatures = function (d, isShow) {

            //
            if(!isShow){
                _this.offExtrusionModel(d.data.designLayerId);
               
                //
                return;
            }

            //
            let imageryProvider = d.layer.imageryProvider;
            let layerName = imageryProvider.layers;
            let currentCqlFilter = imageryProvider._resource.queryParameters.cql_filter;

            //feature 정보 요청
            _this.getFeatures({ 'typeNames': layerName, 'cql_filter': currentCqlFilter }, function (e) {
                let entities = e.entities.values;

                //
                for (let i = 0; i < entities.length; i++) {
                    let entity = entities[i];
                    var polygonHierarchy = entity.polygon.hierarchy.getValue().positions;

                    //
                    let h = Pp.nvl(entity.properties._maximum_building_floors._value, null);
                    if (Pp.isEmpty(h)) {
                        //높이값 없음
                        //console.log('empty height', entity);
                        continue;
                    }

                    //필지 height 변경
                    var building = Mago3D.ExtrusionBuilding.makeExtrusionBuildingByCartesian3Array(polygonHierarchy.reverse(), h * 3.3, {
                        color: new Mago3D.Color(0.2, 0.7, 0.8, 0.4)
                    });

                    building.type = 'land';
                    building.layerId = entity.id;
                    building.designLayerId = d.data.designLayerId;
                    /**
                     * magoManager에 속한 modeler 인스턴스의 addObject 메소드를 통해 모델 등록, 뒤의 숫자는 데이터가 표출되는 최소 레벨을 의미. 숫자가 낮을수록 멀리서 보임
                     */
                    Ppmap.getManager().modeler.addObject(building, 12);
                }
            });
        }


        //on/off 판단
        let b = this.checked;

        //get on된 디자인 레이어(필지) 목록
        let json = _getDatas();

        //get 각 디자인 레이별 height
        let designLayerIds = Object.keys(json);
		for(let i=0; i<designLayerIds.length; i++){
            let designLayerId = designLayerIds[i];
			let d = json[designLayerId];
             
            //
            _updownFeatures(d, b);
		}


    });
};


/**
 * 도구 설정
 * @param {number} tool 도구
 */
DesignLayerObj.prototype.setTool = function(tool){
    let beforeTool = this.tool;
    //
    this.tool = tool;

    //
    this.toolChanged(beforeTool, this.tool);

};


/**
 * 설정된 도구 조회
 * @returns {DesignLayerObj.Tool}
 */
DesignLayerObj.prototype.getTool = function(){
    return this.tool;
};


DesignLayerObj.prototype.toolIs = function(tool){
    return this.tool === tool;
}


/**
 * tool로 해당 button's 엘리먼트 구하기
 * @param {DesignLayerObj.Tool} tool
 */
DesignLayerObj.prototype.getElByTool = function (tool) {
    let toolName = '';
    let keys = Object.keys(DesignLayerObj.Tool);

    for (let i = 0; i < keys.length; i++) {
        let k = keys[i];

        //
        if (tool == DesignLayerObj.Tool[k]) {
            toolName = k.toLowerCase();
        }
    }


    let coll = Ppui.find('button[class*=design-layer-tool]');
    for (let i = 0; i < coll.length; i++) {
        let el = coll.item(i);

        for (let j = 0; j < el.classList.length; j++) {
            let className = el.classList[j].replace(/design-layer-tool-/gi, '');

            //
            if (toolName === className) {
                return el;
            }
        }
    }

    //
    return;
};


/**
 * el로 tool 구하기
 * @param {Element} el
 * @returns {DesignLayerObj.Tool}
 */
DesignLayerObj.prototype.getToolByEl = function (el) {
    let keys = Object.keys(DesignLayerObj.Tool);

    //
    for (let i = 0; i < keys.length; i++) {
        let k = keys[i];

        for (let j = 0; j < el.classList.length; j++) {
            let className = el.classList[j].replace(/design-layer-tool-/gi, '');

            //
            //console.log(k, className);
            if (className === k.toLowerCase()) {
                return DesignLayerObj.Tool[k];
            }
        }
    }

    //
    return DesignLayerObj.Tool.NONE;
};



/**
 * 
 * @param {any} e
 */
DesignLayerObj.prototype.selectedf4dCallback = function (e) {
    console.log(e);

    //
    if (Ppui.hasClass('button.design-layer-tool-delete', 'active')) {
        //TODO 삭제
        return;
    }
};


/**
 * 도구 변경시 호출됨   
 * @param {number} beforeTool 변경 전 도구
 * @param {number} afterTool 변경 후 도구
 */
DesignLayerObj.prototype.toolChanged = function (beforeTool, afterTool) {
    console.log(this.getToolName(beforeTool), '=>', this.getToolName(afterTool));


    //
    Ppui.hide('div.design-layer-land-wrapper');

    //
    Ppui.removeClass(this.getElByTool(beforeTool), 'active');
    Ppui.addClass(this.getElByTool(afterTool), 'active');


    //건물 높이 extrusion 삭제
    let _clearExtrusionLands = function(){
        let selectionManager = Ppmap.getManager().selectionManager;
        let modeler = Ppmap.getManager().modeler;
        let lands = modeler.getObjectByKV('type', 'land');

        if(Pp.isEmpty(lands)){
            return;
        }

        for(let i=0; i<lands.length; i++){
            modeler.removeObject(lands[i]);
        }

        //
        selectionManager.clearCurrents();
        Ppmap.getManager().defaultSelectInteraction.clear();
    };

    //모든 이벤트/정보 클리어
    if(this.toolIs(DesignLayerObj.Tool.NONE)){
        //
        this.setSelectionInteraction(false);
        //
        Ppmap.getManager().defaultTranslateInteraction.setActive(false);
        //
        if(Pp.isNotNull(this.upanddown)){
            this.upanddown.setActive(false);
        }
        //
        if(Pp.isNotNull(this.rotate)){
            this.rotate.setActive(false);
        }

        //
        if (Pp.isNotNull(this.handler) && !this.handler.isDestroyed()) {
            this.handler.destroy();
        }


        //
        //_clearExtrusionLands();
    }

    //선택
    if(this.toolIs(DesignLayerObj.Tool.SELECT)){
        this.processToolSelect();
    }

    //삭제
    if(this.toolIs(DesignLayerObj.Tool.DELETE)){
        this.processToolDelete();
    }
    
    //이동
    if(this.toolIs(DesignLayerObj.Tool.MOVE)){
        this.processToolMove();
    }
    
    //회전
    if(this.toolIs(DesignLayerObj.Tool.ROTATE)){
        this.processToolRotate();
    }
    
    //높이조절
    if(this.toolIs(DesignLayerObj.Tool.UPDOWN)){
        this.processToolUpdown();
    }
    
    //필지정보조회
    if(this.toolIs(DesignLayerObj.Tool.INTERSECTION)){
        this.processToolIntersection();
    }
    
    //필지높이조절
    if(this.toolIs(DesignLayerObj.Tool.LANDUPDOWN)){
        this.processToolLandUpdown();
    }

};


/**
 * 지도에서 객체 선택 상태로 할지 말지 
 * @param {boolean} onOff 
 */
DesignLayerObj.prototype.setSelectionInteraction = function(onOff){
    if(onOff){
        //
        Ppmap.getManager().defaultSelectInteraction.setTargetType('native');
        Ppmap.getManager().defaultSelectInteraction.setActive(onOff);
        Ppmap.getManager().isCameraMoved = true;
    }else{
        Ppmap.getManager().defaultSelectInteraction.setActive(onOff);
        Ppmap.getManager().off(Mago3D.MagoManager.EVENT_TYPE.SELECTEDF4D, this.selectedf4dCallback);
    }
};


/**
 * selecteImageryLayer로 필지 높이조절하기
 * @param {ImageryLayer} selectedImageryLayer 
 * @param {LonLat} geoCoord 
 */
DesignLayerObj.prototype.extrudeLandByImageryLayer = function(selectedImageryLayer, geoCoord){
    //
    var imagerProvider = selectedImageryLayer.imageryProvider;
    var layerName = imagerProvider.layers;
    var currentCqlFilter = imagerProvider._resource.queryParameters.cql_filter;    

    //
    let opt = {
        'typeNames' : layerName,
        'cql_filter': currentCqlFilter + ' AND ' + 'CONTAINS(the_geom, POINT(' + geoCoord.longitude + ' ' + geoCoord.latitude + '))',
    };

    //
    this.getFeatures(opt, function(e){
        var entities = e.entities.values;
        //
        if(Pp.isEmpty(entities)){
            console.log('empty entities');
            return;
        }

        //
        for(let i=0; i<entities.length; i++) {
            var entity = entities[i];

            //
            let h = Pp.nvl(entity.properties._maximum_building_floors._value, null);
            if(Pp.isEmpty(h)){
                //높이값 없음
                //console.log('empty height', entity);
                continue;
            }

            var polygonHierarchy  = entity.polygon.hierarchy.getValue().positions;
            
            /**
             * @class Mago3D.ExtrusionBuilding
             * Polygon geometry과 높이를 이용하여 건물을 생성
             * 
             * Mago3D.ExtrusionBuilding의 static method인 makeExtrusionBuildingByCartesian3Array 함수를 통해 빌딩을 생성,
             * Cesium의 Cartesian3 배열과 높이, 스타일관련 옵션으로 건물 객체 반환
             */
            var building = Mago3D.ExtrusionBuilding.makeExtrusionBuildingByCartesian3Array(polygonHierarchy.reverse(), h*3.3, {
                color : new Mago3D.Color(0.2, 0.7, 0.8, 0.4)
            });
            building.type = 'land';
            building.designLayerId = selectedImageryLayer.layerId;
            /**
             * magoManager에 속한 modeler 인스턴스의 addObject 메소드를 통해 모델 등록, 뒤의 숫자는 데이터가 표출되는 최소 레벨을 의미. 숫자가 낮을수록 멀리서 보임
             */
            Ppmap.getManager().modeler.addObject(building, 12);            
        }
    });

};

/**
 * 특정 위치(지도에서 마우스 클릭)의 필지 높이조절
 * @param {Cartesian2} ctsn2 
 */
DesignLayerObj.prototype.extrudeLandByCtsn2 = function(ctsn2){
    let viewer = MAGO3D_INSTANCE.getViewer();
    let scene = viewer.scene;
    let pickRay = viewer.camera.getPickRay(ctsn2.position);

    //
    let selectedImageryLayers = viewer.imageryLayers.pickImageryLayerInRay(pickRay, scene, function (layer) {
        return !layer.isBaseLayer();
    });
    

    //
    if(Pp.isEmpty(selectedImageryLayers)){
        toastr.warning('선택된 필지정보가 없습니다.');
        return;
    }

    //
    var geoCoord = Ppmap.Convert.ctsn2ToLonLat(ctsn2.position);

    //
    for(let i=0; i<selectedImageryLayers.length; i++){
        let d = selectedImageryLayers[i];

        //
        this.extrudeLandByImageryLayer(d, geoCoord);
    }
};

/**
 * 도구 - 필지 높이조절
 */
DesignLayerObj.prototype.processToolLandUpdown = function(){
    //
    if(!this.toolIs(DesignLayerObj.Tool.LANDUPDOWN)){
        return;
    }

    //
    let _this = this;

    
    //
    _this.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

    //왼쪽 클릭
    _this.handler.setInputAction(function(event){
        //
        _this.extrudeLandByCtsn2(event);

    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

    //오른쪽 클릭
    _this.handler.setInputAction(function(event){        
        //
        _this.setTool(DesignLayerObj.Tool.NONE);
    }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};

/**
 * 도구 - 필지정보조회 처리
 */
DesignLayerObj.prototype.processToolIntersection = function() {
    let _getData = function(datas){
        if(Pp.isEmpty(datas)){
            return null;
        }

        //강제로 1st정보만 get
        let designLayerId = datas[0].designLayerId;

        return _this.getDataById(designLayerId);
    };


    //데이터 화면에 표시
    let _showData = function(data){
        Ppui.remove('table.design-layer-land');

        //handlerbars
        let source = $('#design-layer-land-template').html();
        let template = Handlebars.compile(source);

        //
        let html = template({'data': data});
        $('div.design-layer-land').append(html);

    };



    //
    if(!this.toolIs(DesignLayerObj.Tool.INTERSECTION)){
        return;
    }
    
    //
    let _this = this;
   
    //
    _this.setSelectionInteraction(true);
   
    //
    _this.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

     //왼쪽 클릭
    _this.handler.setInputAction(function(event){
        let lonLat = Ppmap.Convert.ctsn2ToLonLat(event.position);

        //
        if(Pp.isEmpty(lonLat) || Pp.isEmpty(lonLat.lon)){
            console.log('empty lonLat');
            return;
        }

        //
        let param = {
            'wkt': null,
            'type': 'land',
            'buffer': 0.0001,
            'maxFeatures': 10,
            'geometryInfo': [{
                'longitude': lonLat.lon,
                'latitude': lonLat.lat,
                'altitude': null
            }]
        };

        //
       $.ajax({
            url: "/api/geometry/intersection/design-layers",
            type: "POST",
            data: JSON.stringify(param),
			dataType: 'json',
			contentType: 'application/json;charset=utf-8'
        }).done(function(res) {
            debugger;
            _this.lands=[];
            //
            if(Pp.isNotEmpty(res._embedded) && Pp.isNotEmpty(res._embedded.designLayerLands)){
                _this.lands = res._embedded.designLayerLands;
            }

            //값 표시
            _this.showLandData(1);
            
            // //
            // Ppui.show('div.design-layer-land-wrapper');

            // //
            // let pageNo=1;
            // //데이터 표시
            // _this.showLandData(pageNo);
            // //페이징 표시
            // DS.pagination(_this.lands.length, pageNo, $('div.design-layer-land-wrapper .pagination'), function(_pageNo){
            //     //
            //     _this.showLandData(_pageNo);
            // }, {'pageSize':1, 'maxPages':10});


            //////////////갓도//////////////////////
           const geometry = res._embedded.designLayerLands[0].theGeom;
           //
           const parseArr = Terraformer.WKT.parse(geometry);
           const geometryInfo = [];
           const oneLot = parseArr.coordinates[0][0];
           for(let obj of oneLot) {
               geometryInfo.push({
                   'longitude': obj[0],
                   'latitude': obj[1],
                   'altitude': null
               });
           }
           let param = {
               'wkt': null,
               'type': 'building',
               'buffer': 0.0001,
               'maxFeatures': 10,
               'geometryInfo': geometryInfo
           };
           $.ajax({
               url: "/api/geometry/intersection/design-layers",
               type: "POST",
               data: JSON.stringify(param),
               dataType: 'json',
               contentType: 'application/json;charset=utf-8'
           }).done(function(res) {
               const lotArr = [];
               for(let obj of oneLot) {
                   lotArr.push(obj[0])
                   lotArr.push(obj[1])
               }
               //console.log(lotArr)
               const lotCart = Cesium.Cartesian3.fromDegreesArray(lotArr)

               const builds = res._embedded.designLayerBuildings;
               const buildsMap = {};
               let sumArea = 0;
               for(let obj of builds) {
                   const buildArr = []
                   for(let obj2 of Terraformer.WKT.parse(obj.theGeom).coordinates[0][0]) {
                       buildArr.push(obj2[0])
                       buildArr.push(obj2[1])
                   }
                   const cart = Cesium.Cartesian3.fromDegreesArray(buildArr);
                   buildsMap[obj.designLayerBuildingId] = {
                       position : cart,
                       area: getArea(cart),
                       height: obj.buildFloor
                   };
                   sumArea += buildsMap[obj.designLayerBuildingId].area;
                   //console.log(buildsMap)
               }

               const buildFloorAreaParam = [];
               for(const p in buildsMap){
                   buildFloorAreaParam.push(buildsMap[p].area)
               }

               const buildConvAreaParam = [];
               for(const p in buildsMap){
                   buildConvAreaParam.push([buildsMap[p].area, buildsMap[p].height])
               }
               const lotArea = getArea(lotCart);
                $('#nowFloorCov').text(calcFloorCoverage(buildFloorAreaParam, lotArea));
                $('#nowBuildCov').text(calcBuildCoverage(buildConvAreaParam, lotArea));

               // 필지에 대한 면적을 알고있음..
               // 필지에 대한 면적을 구한다
               // 빌딩들에 대한 면적을 알고있음..
               // 빌딩들에 대한 면적을 구한다.
           });

		});
			
    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

    //오른쪽 클릭
    _this.handler.setInputAction(function(event){
        //       
        _this.setTool(DesignLayerObj.Tool.NONE);

   }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};


/**
 * 필지 정보 표시
 * @param {number} pageNo 페이지 번호 1부터시작
 */
DesignLayerObj.prototype.showLandData = function(pageNo){
    let _this = this;
    //
    Ppui.remove('div.design-layer-land-modal');

    //
    if(0 === this.lands.length){
        toastr.info('필지정보가 없습니다.');
        return;
    }

    //handlerbars
    let source = $('#design-layer-land-template').html();
    let template = Handlebars.compile(source);

    //
    Handlebars.registerHelper('getDataName', function(designLayerId){
        if(Pp.isEmpty(designLayerId)){
            return '';
        }
        //
        return _this.getDataById(designLayerId).designLayerName;        
    });

    //
    let html = template({'data': this.lands[pageNo-1]});
    $('body').append(html);
    
    //
    $('div.design-layer-land-modal').dialog({
        autoOpen: false,
		width: 600,
		height: 400,
		modal: true,
		resizable: false,
		title : '필지정보',
		show:{
			'effect':'fade',
			'duration':500
		},
		buttons:{
			'닫기':function(){
				$(this).dialog('close')
			}
		}
    }).dialog('open');
};

/**
 * 도구 - 높이조절 처리
 */
DesignLayerObj.prototype.processToolUpdown = function(){    
    //
    if(!this.toolIs(DesignLayerObj.Tool.UPDOWN)){
        return;
    }
    
    //
    let _this = this;
    
    //
    _this.setSelectionInteraction(true);
    /**
	 * 선택된 객체(디자인 레이어)를 마우스로 높낮이 조절하는 기능
	 */
	_this.upanddown = new Mago3D.NativeUpDownInteraction();
	Ppmap.getManager().interactionCollection.add(_this.upanddown);

    _this.upanddown.setActive(true);

    //
    _this.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

    
    //오른쪽 클릭
    _this.handler.setInputAction(function (event) {
        _this.setTool(DesignLayerObj.Tool.NONE);
   }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};

/**
 * 도구 - 회전 처리
 */
DesignLayerObj.prototype.processToolRotate = function(){    
    //
    if(!this.toolIs(DesignLayerObj.Tool.ROTATE)){
        return;
    }
    
    //
    let _this = this;
    /**
	 * 선택된 객체를 마우스로 회전시키는 기능
	 */
	_this.rotate = new Mago3D.RotateInteraction();
	Ppmap.getManager().interactionCollection.add(_this.rotate);

    //
	_this.setSelectionInteraction(true);
    _this.rotate.setTargetType('native');
	_this.rotate.setActive(true);

    //
    _this.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);


    //오른쪽 클릭
    _this.handler.setInputAction(function (event) {
        _this.setTool(DesignLayerObj.Tool.NONE);
   }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};

/**
 * 도구 - 이동 처리
 */
DesignLayerObj.prototype.processToolMove = function(){    
    //
    if(!this.toolIs(DesignLayerObj.Tool.MOVE)){
        return;
    }
    
    //
    let _this = this;

    //
	_this.setSelectionInteraction(true);

    Ppmap.getManager().defaultTranslateInteraction.setTargetType('native');
    Ppmap.getManager().defaultTranslateInteraction.setActive(true);

    //
    _this.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);


    //오른쪽 클릭
    _this.handler.setInputAction(function(event){
        _this.setTool(DesignLayerObj.Tool.NONE);
   }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};



/**
 * 도구 - 삭제 처리
 */
DesignLayerObj.prototype.processToolDelete = function(){
    //삭제
    //@param {Array<ExtrusionBuilding>}
    let _delete = function(datas){
        if(Pp.isEmpty(datas)){
            return;
        }

        //
        for(let i=0; i<datas.length; i++){
            let d = datas[i];

            //
            Ppmap.getManager().modeler.removeObject(d);
        }

        //
        Ppmap.getManager().selectionManager.clearCurrents();
        Ppmap.getManager().defaultSelectInteraction.clear();
    };



    //
    if(!this.toolIs(DesignLayerObj.Tool.DELETE)){
        return;
    }
    
    //
    let _this = this;

    //
	_this.setSelectionInteraction(true);

    //
    _this.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

     //왼쪽 클릭
    _this.handler.setInputAction(function(event){
        // 선택된 데이터 라이브러리 정보 추출
        let extrusionBuildings = Ppmap.getManager().selectionManager.getSelectedGeneralArray();
        //
        _delete(extrusionBuildings);

   }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

   //오른쪽 클릭
    _this.handler.setInputAction(function(event){
       _this.setTool(DesignLayerObj.Tool.NONE);
   }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);

};


/**
 * 도구 - 선택 처리
 */
DesignLayerObj.prototype.processToolSelect = function(){
    //
    if(!this.toolIs(DesignLayerObj.Tool.SELECT)){
        return;
    }

    //
    let _this = this;

    //
	_this.setSelectionInteraction(true);

    //
    _this.handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

    //왼쪽 클릭
    _this.handler.setInputAction(function(event){
         // 선택된 데이터 라이브러리 정보 추출
         let nodes = Ppmap.getManager().selectionManager.getSelectedGeneralArray();
         //console.log(nodes);

    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

    //오른쪽 클릭
    _this.handler.setInputAction(function(event){
        _this.setTool(DesignLayerObj.Tool.NONE);
    }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
};



DesignLayerObj.prototype.getToolName = function(tool){
    let keys = Object.keys(DesignLayerObj.Tool);

    //
    for(let i=0; i<keys.length; i++){
        let k = keys[i];

        //
        if(DesignLayerObj.Tool[k] === tool){
            return k;
        }
    }

    //
    return '';
};


/**
 * 전체 디자인 레이어 그룹 목록. 동기 호출
 * @returns {Array}
 */
DesignLayerObj.prototype.getDesignLayerGroups = function(){
    let _this = this;

    //
    _this.groups = [];
    Pp.get('../api/design-layer-groups', [], function(res){
        if(Pp.isNotEmpty(res._embedded) && Pp.isNotEmpty(res._embedded.designLayerGroups)){
            _this.groups = res._embedded.designLayerGroups;
        }
        return _this.groups;
    }, {'async':false});
};


/**
 * 전체 디자인 레이어 목록 조회. 동기 호출
 * @returns {Array}
 */
DesignLayerObj.prototype.getDesignLayers = function(){
    let _this = this;

    //
    _this.datas = [];
    Pp.get('../api/design-layers', [], function(res){
        if(Pp.isNotEmpty(res._embedded) && Pp.isNotEmpty(res._embedded.designLayers)){
            _this.datas = res._embedded.designLayers;
        }
        return _this.datas;
    }, {'async':false});
};


/**
 * 도시 그룹 전체 목록 조회. 동기 호출
 * @returns {Array}
 */
DesignLayerObj.prototype.getUrbanGroups = function(){
    let _this = this;

    _this.urbanGroups = [];
    //
    Pp.get('../api/urban-groups', [], function(res){
        if(Pp.isNotEmpty(res._embedded) && Pp.isNotEmpty(res._embedded.urbanGroups)){
            _this.urbanGroups = res._embedded.urbanGroups;
        }
        return _this.urbanGroups;
    }, {'async':false});
};



/**
 * parent로 도시 그룹 목록 조회
 * @param {string|number} parent 부모
 * @returns {Array}
 */
DesignLayerObj.prototype.getUrbanGroupsByParent = function(parent){
    let arr=[];
    
    //
    for(let i=0; i<this.urbanGroups.length; i++){
        let d = this.urbanGroups[i];

        //
        if(parent == d.parent){
            arr.push(d);
        }
    }

    //
    return arr;
}


/**
 * datas를 selector에 표시하기
 * @param {string} selector 셀렉터
 * @param {Array} datas 도시 그룹 데이터 목록
 */
DesignLayerObj.prototype.renderUrbanGroup = function(selector, datas){
	let option = {
		'tkey': 'urbanGroupName',	
		'vkey': 'urbanGroupId',	
		'append': false,	
    };
    
    //
    Ppui.bindDatas(selector, datas, option);
    

    //
    Ppui.trigger(selector, 'change');
};


/**
 * urbanGroupId로 디자인 레이어 목록 조회
 * @param {string|number} urbanGroupId 도시 그룹 아이디
 * @returns {Array}
 */
DesignLayerObj.prototype.getDatasByUrbanGroupId = function(urbanGroupId){
    let arr=[];

    //
    for(let i=0; i<this.datas.length; i++){
        let d = this.datas[i];

        //
        if(urbanGroupId == d.urbanGroupId){
            arr.push(d);
        }
    }

    //
    return arr;
};


/**
 * designLayerId로 데이터 조회
 * @param {string|number} designLayerId 디자인 레이어 아이디
 */
DesignLayerObj.prototype.getDataById = function(designLayerId){
    for(let i=0; i<this.datas.length; i++){
        let d = this.datas[i];

        //
        if(designLayerId == d.designLayerId){
            return d;
        }
    }

    //
    return null;
};


/**
 * urbanGroupId에 해당하는 디자인 레이어만 화면에 표시
 * @param {string|number} urbanGroupId 도시 그룹 아이디
 */
DesignLayerObj.prototype.renderDesignLayersByUrbanGroupId = function(urbanGroupId){
    let _this = this;

    //
    let datas = this.getDatasByUrbanGroupId(urbanGroupId);

    //handlerbars
    let source = $('#design-layer-template').html();
    let template = Handlebars.compile(source);
    
    //
    let html = template({'datas': datas});
    Ppui.find('div.design-layers').innerHTML = html;

    
    //tr클릭 이벤트 등록
    Ppui.click('table.design-layers > tbody > tr', function(){
        Ppui.toggleClass(this, 'on');

        //
        Ppui.child(this, '[name=design-layer-id]').checked = Ppui.hasClass(this, 'on') ? true : false;
        //
        let designLayerId = Ppui.child(this, '[name=design-layer-id]').value;

        //
        _this.showDesignLayer(designLayerId, Ppui.hasClass(this, 'on'));
    });
};


/**
 * 해당 디자인 레이어 on/off
 * @param {string|number} designLayerId 디자인 레이어 아이디
 * @param {boolean} isShow 표시 여부
 */
DesignLayerObj.prototype.showDesignLayer = function(designLayerId, isShow){
    let data = this.getDataById(designLayerId);

    let model = {
        'id': designLayerId,
        'layername': data.designLayerKey,
        'ogctype': data.ogcWebServices,
    };

    //
    if('wms' === model.ogctype){
        this.extrusionModelWMSToggle(model, isShow);
    }
    if('wfs' === model.ogctype){
        this.extrusionModelBuildingToggle(model, isShow);
    }
};


/**
 * wms요청
 * @see extrusion.js > extrusionModelWMSToggle()
 * @param {object} model 
 * @param {boolean} isShow 화면 표시 여부
 */
DesignLayerObj.prototype.extrusionModelWMSToggle = function(model, isShow){
    let url = [LHDT.policy.geoserverDataUrl, LHDT.policy.geoserverDataStore, model.ogctype].join('/');
    //
    var imageryLayers = Ppmap.getViewer().imageryLayers;
    if(isShow) {
        var prov = new Cesium.WebMapServiceImageryProvider({
            url : url,
            parameters : {
                transparent : true,
                srs:'EPSG:4326',
                format: "image/png",
                cql_filter : 'design_layer_id=' + model.id,
            },
            layers : model.layername
        });

        var imageryLayer = new Cesium.ImageryLayer(prov/*, {alpha : 0.7}*/);
        imageryLayer.layerId = model.id;
        imageryLayers.add(imageryLayer);

        //
        this.landLayer[model.id] = imageryLayer;
    } else {
        var target = imageryLayers._layers.filter(function(layer){return layer.layerId === model.id});
        if(target.length === 1)
        {
            imageryLayers.remove(target[0]);
        }

        //
        this.landLayer[model.id] = null;

        //
        this.offExtrusionModel(model.id);
    }
};



/**
 * extrusion model(필지 바닥정보로 높이를 올린 디자인 레이어,빌딩) 지도에서 삭제하기
 * @param {*} designLayerId 
 */
DesignLayerObj.prototype.offExtrusionModel = function(designLayerId){
    var modeler = Ppmap.getManager().modeler;
        
    var models = modeler.objectsArray;
    if(Pp.isEmpty(models)){
        return;
    }
    //
    for(let i=0; i<models.length; i++){
        let building = models[i];
        if(Pp.isNull(building)){
            continue;
        }

        //console.log(building, designLayerId);
        if(building.designLayerId == designLayerId || building.layerId == designLayerId) {
            modeler.removeObject(building);
        }
    }
};

/**
 * 
 * @param {object} model 
 * @param {bool} isShow
 */
DesignLayerObj.prototype.extrusionModelBuildingToggle = function(model, isShow) {
  
    if(isShow) {
      
        let opt = {
            'typeNames': model.layername,
            'cql_filter': 'design_layer_id=' + model.id
        };
        this.getFeatures(opt, function(e){
        // var loader = new Cesium.GeoJsonDataSource().load(res).then(function(e){
            var entities = e.entities.values;
            var FLOOR_HEIGHT = 3.3;
            
                for(var i in entities) {
                    var entity = entities[i];                    
                    var polygonHierarchy  = entity.polygon.hierarchy.getValue().positions;
                    
                    /**
                     * @class Mago3D.ExtrusionBuilding
                     * Polygon geometry과 높이를 이용하여 건물을 생성
                     * 
                     * Mago3D.ExtrusionBuilding의 static method인 makeExtrusionBuildingByCartesian3Array 함수를 통해 빌딩을 생성,
                     * Cesium의 Cartesian3 배열과 높이, 스타일관련 옵션으로 건물 객체 반환
                     */
                    var building = Mago3D.ExtrusionBuilding.makeExtrusionBuildingByCartesian3Array(polygonHierarchy.reverse(), FLOOR_HEIGHT * 7)
                    
                    building.layerId = model.id; 
                    
                    /**
                     * magoManager에 속한 modeler 인스턴스의 addObject 메소드를 통해 모델 등록, 뒤의 숫자는 데이터가 표출되는 최소 레벨을 의미. 숫자가 낮을수록 멀리서 보임
                     */
                    Ppmap.getManager().modeler.addObject(building, 12);
                }
        });
    } else {
        this.offExtrusionModel(model.id);
        // var modeler = Ppmap.getManager().modeler;
        
        // var models = modeler.objectsArray;
        // if(Pp.isEmpty(models)){
        //     return;
        // }
        // //
        // for(let i=0; i<models.length; i++){
        //     let building = models[i];

        //     if(building.layerId == model.id) {
        //         /**
        //              * modeler 인스턴스의 removeObject 메소드를 통해 모델 삭제
        //              */
        //         modeler.removeObject(building);
        //     }
        // }
    }
}


/**
 * geoserver에 요청
 * @param {Object} option {'url':string, 'typeNames':string, 'cql_filter':string}
 * @param {Function} callbackFn 콜백함수
 */
DesignLayerObj.prototype.getFeatures = function(option, callbackFn){
    let opt = Pp.extend({'url':[LHDT.policy.geoserverDataUrl, LHDT.policy.geoserverDataStore, 'wfs'].join('/')}, option);

    //
    let queryParameters = {
        serivice: 'wfs',
        version: '1.0.0',
        request: 'GetFeature',
        srsName: 'EPSG:3857',
        outputFormat: 'application/json',
    };
    if(Pp.isNotEmpty(opt.typeNames)){
        queryParameters.typeNames = opt.typeNames;
    }
    if(Pp.isNotEmpty(opt.cql_filter)){
        queryParameters.cql_filter = opt.cql_filter;
    }

    //
    let req = new Cesium.Resource({
        url : opt.url,
        queryParameters : queryParameters
    });

    //
    new Cesium.GeoJsonDataSource.load(req).then(function(e){
        callbackFn(e);
    });

};


//
let designLayerObj = new DesignLayerObj();
//
window.addEventListener('load', function(){
    let intvl = setInterval(function(){
        if(Pp.isNotNull(MAGO3D_INSTANCE)){
            clearInterval(intvl);
            //
            designLayerObj.init();
        }
    }, 500);
});



const ratioStructure = {

}


// 건폐율 계산 및 view (건축면적 / 대지면적)
function buildingToLandRatioCalc() {
    if (pickedName === "") {
        alert("오브젝트를 먼저 선택해 주시기 바랍니다.");
        return;
    }
    let plottage = parseFloat(allObject[pickedName].plottage); // 대지면적
    let totalFloorArea = parseFloat(allObject[pickedName].totalFloorArea); // 총 건축면적

    if (plottage === 0.0) {
        return;
    }
    let result = (totalFloorArea / plottage) * 100.0;
    // $("#curBuildingToLandRatio").val(result.toFixed(2));
}

// 건폐율 계산
// 건물바닥면적 / 대지면적 * 100
/**
 *
 * @param floorCoverList build floor Area Calc Val List sample => [50, 20, 30, 10]
 * @param lotTargetArea lot floor Area Calc Val List  sample => 50
 * @returns {number}
 */
function calcFloorCoverage(floorCoverList, lotTargetArea) {
    // 각층 바닥 면접의 합
    // 각층 * 바닥 면접
    let sumFllor = 0;
    for(const obj of floorCoverList) {
        sumFllor += obj;
    }
    return parseInt(sumFllor / lotTargetArea * 100);
}

/**
 *
 * @param floorCoverList build floor Area with height Calc Val List sample => [[50, 10] [20, 5], [30, 5], [10, 5]]
 * @param lotTargetArea lot floor Area Calc Val List  sample => 50
 * @returns {number}
 */
function calcBuildCoverage(floorCoverList, lotTargetArea) {
    // 각층 바닥 면접의 합
    // 각층 * 바닥 면접
    let sumFloor = 0;
    for(const obj of floorCoverList) {
        sumFloor += obj[0] * obj[1];
    }
    return parseInt(sumFloor / lotTargetArea * 100);
}

// 모든 빌딩들의 연면적 합
function totalAreaCalc(entityArray) {
    let sum = 0;
    entityArray.forEach(entity => {
        sum += entity.totalBuildingFloorArea;
    });
    return sum;
}


function getArea(positions) {
    areaInMeters = 0;
    if (positions.length >= 3)
    {
        var points = [];
        for(var i = 0, len = positions.length; i < len; i++)
        {
            var cartographic = Cesium.Cartographic.fromCartesian(positions[i]);
            points.push(new Cesium.Cartesian2(cartographic.longitude, cartographic.latitude));
        }
        if(Cesium.PolygonPipeline.computeWindingOrder2D(points) === Cesium.WindingOrder.CLOCKWISE)
        {
            points.reverse();
        }

        var triangles = Cesium.PolygonPipeline.triangulate(points);

        for(var i = 0, len = triangles.length; i < len; i+=3)
        {
            areaInMeters += calArea(points[triangles[i]], points[triangles[i + 1]], points[triangles[i + 2]]);
        }
    }
    return areaInMeters;
}

function calArea(t1, t2, t3, i) {
    var r = Math.abs(t1.x * (t2.y - t3.y) + t2.x * (t3.y - t1.y) + t3.x * (t1.y - t2.y)) / 2;
    var cartographic = new Cesium.Cartographic((t1.x + t2.x + t3.x) / 3, (t1.y + t2.y + t3.y) / 3);
    var cartesian = _viewer.scene.globe.ellipsoid.cartographicToCartesian(cartographic);
    var magnitude = Cesium.Cartesian3.magnitude(cartesian);
    return r * magnitude * magnitude * Math.cos(cartographic.latitude)
}