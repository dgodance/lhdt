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
    Ppui.click('[class*=design-layer-tool]', function(){
        //
        let _getTool = function(el){
            for(let i=0; i<el.classList.length; i++){
                let className = el.classList[i];
    
                let keys = Object.keys(DesignLayerObj.Tool);
                for(let j=0; j<keys.length; j++){
                    let k = keys[j];
    
                    //
                    if(-1 != className.indexOf(k.toLowerCase())){
                        return DesignLayerObj.Tool[k];
                    }
                }
            }
    
            //
            return DesignLayerObj.Tool.NONE;
        };
    
        //
        let className = 'active';
        let b = Ppui.hasClass(this, className);
    
        //
        Ppui.removeClass('[class*=design-layer-tool]', className);
    
        //
        let tool = DesignLayerObj.Tool.NONE;
        //
        if(!b){
            Ppui.addClass(this, 'active');
            tool = _getTool(this);
        }
    
        //
        _this.setTool(tool);
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

    //
    toastr.info('선택된 도구 : ' + this.getToolName(this.tool));
};


/**
 * 설정된 도구 조회
 * @returns {DesignLayerObj.Tool}
 */
DesignLayerObj.prototype.getTool = function(){
    return this.tool;
};


DesignLayerObj.prototype.isTool = function(tool){
    return this.tool === tool;
}


/**
 * 도구 변경시 호출됨   
 * @param {number} beforeTool 변경 전 도구
 * @param {number} afterTool 변경 후 도구
 */
DesignLayerObj.prototype.toolChanged = function(beforeTool, afterTool){
    //선택
    if(this.isTool(DesignLayerObj.Tool.SELECT)){
        this.processToolSelect();
    }

    //삭제
    if(this.isTool(DesignLayerObj.Tool.DELETE)){
        this.processToolDelete();
    }
    
    //이동
    if(this.isTool(DesignLayerObj.Tool.MOVE)){
        this.processToolMove();
    }
    
};


DesignLayerObj.prototype.setSelectionInteraction = function(onOff){
    if(onOff){
        //
        Ppmap.getManager().defaultSelectInteraction.setTargetType('native');
        Ppmap.getManager().defaultSelectInteraction.setActive(onOff);
        Ppmap.getManager().isCameraMoved = true;
    }else{
        Ppmap.getManager().defaultSelectInteraction.setActive(onOff);
    }
};


/**
 * 도구 - 이동 처리
 */
DesignLayerObj.prototype.processToolMove = function(){    
    //
    if(!this.isTool(DesignLayerObj.Tool.MOVE)){
        return;
    }
    
    //
    let _this = this;

    //
	Ppmap.getManager().defaultSelectInteraction.setTargetType('native');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;

    //
    let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

     //왼쪽 클릭
     handler.setInputAction(function(event){
        Ppmap.getManager().defaultTranslateInteraction.setTargetType('native');
		Ppmap.getManager().defaultTranslateInteraction.setActive(true);

    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

    //오른쪽 클릭
    handler.setInputAction(function(event){
       handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
       handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
       //
       Ppmap.getManager().defaultTranslateInteraction.setActive(false);
       Ppmap.getManager().defaultSelectInteraction.setActive(false);

       //
       Ppui.trigger('.design-layer-tool-none', 'click');
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
    if(!this.isTool(DesignLayerObj.Tool.DELETE)){
        return;
    }
    
    //
    let _this = this;

    //
	Ppmap.getManager().defaultSelectInteraction.setTargetType('native');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;

    //
    let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

     //왼쪽 클릭
     handler.setInputAction(function(event){
        // 선택된 데이터 라이브러리 정보 추출
        let extrusionBuildings = Ppmap.getManager().selectionManager.getSelectedGeneralArray();
        //
        _delete(extrusionBuildings);

   }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

   //오른쪽 클릭
   handler.setInputAction(function(event){
       handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
       handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
       //
       Ppmap.getManager().defaultSelectInteraction.setActive(false);

       //
       Ppui.trigger('.design-layer-tool-none', 'click');
   }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);

};


/**
 * 도구 - 선택 처리
 */
DesignLayerObj.prototype.processToolSelect = function(){
    //
    if(!this.isTool(DesignLayerObj.Tool.SELECT)){
        return;
    }

    //
    let _this = this;

    //
	Ppmap.getManager().defaultSelectInteraction.setTargetType('native');
	Ppmap.getManager().defaultSelectInteraction.setActive(true);
	Ppmap.getManager().isCameraMoved = true;

    //
    let handler = new Cesium.ScreenSpaceEventHandler(Ppmap.getViewer().scene.canvas);

    //왼쪽 클릭
    handler.setInputAction(function(event){
         // 선택된 데이터 라이브러리 정보 추출
         let nodes = Ppmap.getManager().selectionManager.getSelectedGeneralArray();
         console.log(nodes);

    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

    //오른쪽 클릭
    handler.setInputAction(function(event){
        handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
        handler.removeInputAction(Cesium.ScreenSpaceEventType.RIGHT_CLICK);
        //
		Ppmap.getManager().defaultSelectInteraction.setActive(false);

        //
        Ppui.trigger('.design-layer-tool-none', 'click');
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
    Pp.get('../api/design-layer-groups', [], function(res){
        _this.groups = res._embedded.designLayerGroups;;
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
    Pp.get('../api/design-layers', [], function(res){
        _this.datas = res._embedded.designLayers;
        return _this.datas;
    }, {'async':false});
};


/**
 * 도시 그룹 전체 목록 조회. 동기 호출
 * @returns {Array}
 */
DesignLayerObj.prototype.getUrbanGroups = function(){
    let _this = this;

    //
    Pp.get('../api/urban-groups', [], function(res){
        _this.urbanGroups = res._embedded.urbanGroups;
        return _this.datas;
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
    let url = [NDTP.policy.geoserverDataUrl, NDTP.policy.geoserverDataStore, model.ogctype].join('/');
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
    } else {
        var target = imageryLayers._layers.filter(function(layer){return layer.layerId === model.id});
        if(target.length === 1)
        {
            imageryLayers.remove(target[0]);
        }
    }
};


/**
 * 
 * @param {object} model 
 * @param {bool} isShow
 */
DesignLayerObj.prototype.extrusionModelBuildingToggle = function(model, isShow) {
    /**
	 * wfs 요청(건물 등의 데이터를 요청할 때 사용) 기본 객체
	 */
	let _wfsResource = new Cesium.Resource({
        //
		url : [NDTP.policy.geoserverDataUrl, NDTP.policy.geoserverDataStore, model.ogctype].join('/'),
		queryParameters : {
			service : 'wfs',
			version : '1.0.0',
			request : 'GetFeature',
			srsName : 'EPSG:3857',
			outputFormat : 'application/json'
		}
    });
    
    if(isShow) {
        /**
         * 위에서 생성한 기본 wfs 요청 객체를 복사,
         */
        var res = _wfsResource.clone();
        
        /**
         * 대상 레이어를 typeNames에 선언
         */
        res.queryParameters.typeNames = model.layername;
        /**
         * 대상 레이어의 id를 찾는 쿼리를  cql_filter에 선언
         */
        res.queryParameters.cql_filter = 'design_layer_id=' + model.id;
        
        /**
         * Cesium GeoJsonDataSource 클래스를 이용하여 wfs요청. 자동으로 geojson을 파싱하여 객체화 해주어 비즈니스로직만 구현하면 됨.
         */
        var loader = new Cesium.GeoJsonDataSource().load(res).then(function(e){
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
        var modeler = Ppmap.getManager().modeler;
        
        var models = modeler.objectsArray;
        if(Pp.isEmpty(models)){
            return;
        }
        //
        for(let i=0; i<models.length; i++){
            let building = models[i];

            if(building.layerId === model.id) {
                /**
                     * modeler 인스턴스의 removeObject 메소드를 통해 모델 삭제
                     */
                modeler.removeObject(building);
            }
        }
    }
}


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