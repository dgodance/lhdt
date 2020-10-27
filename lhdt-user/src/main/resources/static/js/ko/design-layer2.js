/**
 * 디자인 레이어
 * 20201023 init
 */
let DesignLayer2Obj = function(){
	this.$container = null;
	
	this.datasetId = '';
	
	this.datas = [];
	
	//show된 필지 레이어
	this.landLayer = {};
};

/**
 * 
 */
DesignLayer2Obj.OgcType = {
    WMS: {value:0, text:'wms'},
    WFS: {value:1, text:'wfs'},
}



/**
 * 
 */
DesignLayer2Obj.prototype.init = function(){
	this.$container = $('.design-layer-container');
	
	console.log(this, '<<.init');	
};



/**
 * 
 */
DesignLayer2Obj.prototype.setEventHandler = function(){
	let self = this;
	
	
	
	
	//선택 체크 클릭
	this.$container.find('.design-layer-cbx').click(function(){
		let $tr = $(this).closest('tr');
		$tr.toggleClass('on');
		
		let designLayerId = $tr.data('design-layer-id');
		
		let b = $(this).is(':checked')	;
		
		
		self.showDesignLayer(designLayerId, b);
		
		
		if(b){
			$tr.find('.design-layer-height,.design-layer-label')
				.prop('disabled', false);
				
		}else{
			$tr.find('.design-layer-height,.design-layer-label')
				.prop('disabled', true)
				.prop('checked', b);
		}
	});
	
	
	
	//레이어명 클릭
	this.$container.find('.design-layer-name').click(function(){
		$(this).closest('tr').find('.design-layer-cbx').click();	
	});
	
	
	
	//높이 체크 클릭
	this.$container.find('.design-layer-height').click(function(){
		let designLayerId = $(this).closest('tr').data('design-layer-id');
        let imageryLayer = self.getImageryLayer(designLayerId);
        let data = self.getDataById(designLayerId);

		let b = $(this).prop('checked');
        self.toggleExtrusionBuilding({'data':data, 'layer':imageryLayer}, b);
	});
	
	
	
	//라벨 체크 클릭
	this.$container.find('.design-layer-label').click(function(){
		let designLayerId = $(this).closest('tr').data('design-layer-id');
        let imageryLayer = self.getImageryLayer(designLayerId);
        let data = self.getDataById(designLayerId);

		let b = $(this).prop('checked');

	    let model = {
	        'id': designLayerId,
	        'layername': data.designLayerKey,
	        'ogctype': 'wfs',
	    };
		
        self.extrusionModelWMSLabelToggle(model, b);
	});
};




/**
 * designLayerId로 데이터 조회
 * @param {string|number} designLayerId 디자인 레이어 아이디
 */
DesignLayer2Obj.prototype.getDataById = function(designLayerId){
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
 * geoserver에 요청
 * @param {Object} option {'url':string, 'typeNames':string, 'cql_filter':string}
 * @param {Function} callbackFn 콜백함수
 */
DesignLayer2Obj.prototype.getFeatures = function(option, callbackFn){
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




/**
 * 화면 표시 레이어의 imageryLayer값 구하기
 * @param {number|string} designLayerId
 * @returns {ImageryLayer}
 */
DesignLayer2Obj.prototype.getImageryLayer = function(designLayerId){
	return this.landLayer[designLayerId];
};




/**
 * Mago3D Polygon2D으로 ExtrusionBuilding 목록 구하기
 * @param {Mago3D.Polygon2D} polygon2d 좌표 목록
 * @returns {Array<ExtrusionBuilding>}
 */
DesignLayer2Obj.prototype.getBuildingsByPolygon2D = function(polygon2d){
    //
    return Ppmap.getManager()
				.frustumVolumeControl
				.selectionByPolygon2D(polygon2d, 'native', function(model) {
    				return model.hasOwnProperty('type') || model.type === 'land'
    			}
			);
}




/**
 * 레이어 라벨 토글, 속도때문에 최초 로드 시 에만 LABEL 객체 생성
 * @param {object} model 
 * @param {boolean} isShow 화면 표시 여부
 */
DesignLayer2Obj.prototype.extrusionModelWMSLabelToggle = function(model, isShow){
    let url = [LHDT.policy.geoserverDataUrl, LHDT.policy.geoserverDataStore, model.ogctype].join('/');
    //
    let imageryLayers = Ppmap.getViewer().imageryLayers;
    let labelLayerId = model.id;
    let labelDS = _findLabelDataSource(labelLayerId);
    if(isShow) {
    	
    	var viewer = Ppmap.getViewer();
    	
    	if(labelDS) {
    		labelDS.show = true;
    	} else {
    		var currentCqlFilter = `design_layer_id=${model.id} AND enable_yn='Y'`;
            
            /**
             * wms labeling 
             */
            $.ajax({
    			url : `/api/design-layers/${model.id}`,
    			type: "GET",
                headers: {"X-Requested-With": "XMLHttpRequest"},
                dataType: "json",
                success: function(json){
                	var req = new Cesium.Resource({
          				url : url,
          				queryParameters : {
          					service : model.ogctype,
          					version : '1.0.0',
          					request : 'GetFeature',
          					typeNames : model.layername,
          					srsName : 'EPSG:3857',
          					outputFormat : 'application/json',
          					cql_filter : currentCqlFilter
          				}
          			});
                	
                	
                	var designLayerGroupType = json.designLayerGroupType;
                	startLoading();
                	new Cesium.GeoJsonDataSource().load(req).then(function(e) {
          				var entities = e.entities.values;
          				var ds = new Cesium.CustomDataSource();
          				ds.labelLayerId = labelLayerId;
          				
          				if(designLayerGroupType === 'land') {
          					var designLayerName = json.designLayerName;
          					if(designLayerName.includes("주거생활") || designLayerName.includes("도심형") || designLayerName.includes("창의혁신")) {
          						var entity = entities[0];
          						var label;
          						if(designLayerName.includes("주거생활")) {
          							label = _defaultLabelOption('주거ㆍ생활 컴플렉스존');
          							label.backgroundColor = Cesium.Color.fromCssColorString('#f1eb37');
          						} else if( designLayerName.includes("도심형")) {
          							label = _defaultLabelOption('도심형 마켓 존');
          							label.backgroundColor = Cesium.Color.fromCssColorString('#f0575a');
          						} else if(designLayerName.includes("창의혁신")) {
          							label = _defaultLabelOption('창의혁신ㆍ산업 클러스터 존');
          							label.backgroundColor = Cesium.Color.fromCssColorString('#8c5a99');
          							label.pixelOffset = new Cesium.Cartesian2(120,0);
          						}
          						label.showBackground = true;
          						label.font = "normal normal bolder 44px Helvetica white",
          						label.distanceDisplayCondition = new Cesium.DistanceDisplayCondition(0.0, 1500)
          						
          						ds.entities.add({
    	               	 			position :  _getPolygonEntityBoundingSphereCenter(entity),
    	               	 			label :  label
    	          				});
          					} else {
          						for(var i in entities) {
                       	 			var entity = entities[i];
                       	 			var properties = entity.properties;
                       	 			
                       	 			var cRatio = properties.building_coverage_ratio.getValue();
                       	 			var fRatio = properties.floor_area_ratio.getValue();
                       	 			var labelText;
                       	 			if(!fRatio || !cRatio) {
                       	 				labelText = `${properties.landuse_zoning.getValue()}`;
                       	 			} else {
                       	 				labelText = `${properties.lot_code.getValue()}\n건폐율 : ${cRatio}\n용적률 : ${fRatio}`;
                       	 			}
                   	 			
                       	 			ds.entities.add({
        	               	 			position :  _getPolygonEntityBoundingSphereCenter(entity),
        	               	 			label :  _defaultLabelOption(labelText)
        	          				});
                       	 		}
                       	 		ds.clustering.enabled = true;
                       	 		ds.clustering.pixelRange = 40;
                       	 		ds.clustering.minimumClusterSize = 5;
                       	 		ds.clustering.clusterPoints = false;
                       	 		ds.clustering.clusterBillboards = false; 
                       	 		
                       	 		ds.clustering.clusterEvent.addEventListener(function (clusteredEntities, cluster, e) {
                       	 			if(cluster.label.id.length > 5) {
                       	 				cluster.label.show = false;
                       	 			} else {
                       	 				cluster.label.show = true;
                       	 			}
                       	        });
          					}
          				} else if(designLayerGroupType === 'building_height') {
          					var designLayerName = json.designLayerName;
                   	 		for(var i in entities) {
                   	 			var entity = entities[i];
                   	 			var properties = entity.properties;
                   	 			
                   	 			var labelText = designLayerName;
                   	 			var maxFloor = properties.build_maximum_floors.getValue();
                   	 			if(maxFloor) labelText += `\n층수 제한 : ${maxFloor}`;  
                   	 			
    	               	 		ds.entities.add({
    	               	 			position : _getPolygonEntityBoundingSphereCenter(entity),
    	               	 			label : _defaultLabelOption(labelText)
    	          				});
                   	 		}
          				}

          				/**
          				 * @fire stopLoading()
          				 */
          				viewer.dataSources.add(ds);
          			});
                }
    		});
    	}

        function _getPolygonEntityBoundingSphereCenter(cEntity) {
        	if(!cEntity || !(cEntity instanceof Cesium.Entity)) {
        		return;
        	}
        	
        	var positions = cEntity.polygon.hierarchy.getValue().positions;
 			var center = Cesium.BoundingSphere.fromPoints(positions).center;
 			Cesium.Ellipsoid.WGS84.scaleToGeodeticSurface(center, center);
 			
 			return center
        }
        
        function _defaultLabelOption(lText) {
        	return {
   	 			text: lText,
				scale :0.5,
				font: "normal normal bolder 22px Helvetica",
				fillColor: Cesium.Color.BLACK,
				outlineColor: Cesium.Color.WHITE,
				outlineWidth: 1,
				//scaleByDistance : new Cesium.NearFarScalar(500, 1.2, 1200, 0.0),
				heightReference : Cesium.HeightReference.CLAMP_TO_GROUND,
				style: Cesium.LabelStyle.FILL_AND_OUTLINE,
				//translucencyByDistance : new Cesium.NearFarScalar(1200, 1.0, 2000, 0.0),
				distanceDisplayCondition : new Cesium.DistanceDisplayCondition(0.0, 800)
 			}
        }
    } else {
        //라벨 제거
    	if(labelDS) {
    		labelDS.show = false;
    	}
    	/*	
        let dataSources = Ppmap.getViewer().dataSources;
        let filter = dataSources._dataSources.filter(function(ds) {
			return ds.labelLayerId  === model.id; 
		})[0];
		
		dataSources.remove(filter, true);*/
    }
    
    function _findLabelDataSource(id)
    {
    	let dataSources = Ppmap.getViewer().dataSources;
        let filter = dataSources._dataSources.filter(function(ds) {
			return ds.labelLayerId  === id; 
		})[0];
        
    	return filter;
    }
};



/**
 * 해당 디자인 레이어 on/off
 * @param {string|number} designLayerId 디자인 레이어 아이디
 * @param {boolean} isShow 표시 여부
 */
DesignLayer2Obj.prototype.showDesignLayer = function(designLayerId, isShow){
    let data = this.getDataById(designLayerId);

    let model = {
        'id': designLayerId,
        'layername': data.designLayerKey,
        'ogctype': data.ogcWebServices,
    };

    //
    if(DesignLayer2Obj.OgcType.WMS['text'] === model.ogctype){
        this.toggleWms(model, isShow);
    }

    if(DesignLayer2Obj.OgcType.WFS['text'] === model.ogctype){
    	this.toggleWfs(model, isShow);
    }
};


/**
 * 
 */
DesignLayer2Obj.prototype.showDatas = function(datasetId){
	this.datasetId = datasetId;
	
	//
	this.getDatasAsync(datasetId);
};



/**
 * 필지 폴리곤 정보로 건물에 제한 정보 설정
 * @param {Array<Cesium.Cartesin3>} polygonHierarchy 영역제한 정보 및 건물 찾기 용도
 * @param {number} h 높이 제한 값
 */
DesignLayer2Obj.prototype.setLimitInfoByPolygon = function(polygonHierarchy, h) {
	
	let geographicCoordsList = Mago3D.GeographicCoordsList.fromCartesians(polygonHierarchy); 
    let polygon2ds = Mago3D.Polygon2D.makePolygonByGeographicCoordArray(geographicCoordsList.geographicCoordsArray);
    
    let buildings = this.getBuildingsByPolygon2D(polygon2ds);
    
    if(buildings.length === 0) return;
    
    for(let i in buildings) {
    	let building = buildings[i];
    	building.setLimitationGeographicCoords(geographicCoordsList.geographicCoordsArray);
    	building.setLimitationHeight(h);
    }
}




/**
 * @param {object} model {id:string, layername:string, ogctype:string}
 * @param {boolean} isShow 화면 표시 여부
 */
DesignLayer2Obj.prototype.toggleWfs = function(model, isShow){	
	
	if(!isShow){
		this.offExtrusionModel(model.id);
		return;
	}
	
	
    let opt = {
        'typeNames': model.layername,
        'cql_filter': 'design_layer_id=' + model.id
    };


	this.getFeatures(opt, function(e){
        var entities = e.entities.values;


		for(let i=0; i<entities.length; i++){
			var entity = entities[i];                    

			let building = entityToMagoExtrusionBuilding(entity, model.layername);
            if(!building) {
				continue;	
			}
			
            
            building.layerId = model.id; 
            building['__originHeight'] = Pp.nvl(building.getHeight(), 0.0);
            //면적
            if(Pp.isNotEmpty(entity.properties['build_area'])){
                building.area = parseFloat(entity.properties['build_area'].getValue());
            }else{
                building.area = 0.0;
            }   
            //unit type
            if(Pp.isNotEmpty(entity.properties['build_unit_type'])){
                building.unitType = entity.properties['build_unit_type'].getValue();
                //console.log('unittype', building);
            }else{
                building.unitType = '0';
            }
            //unit count
            if(Pp.isNotEmpty(entity.properties['build_unit_count'])){
                building.unitCount = parseInt(entity.properties['build_unit_count'].getValue());
            }else{
                building.unitCount = 0;
            }
            /**
             * magoManager에 속한 modeler 인스턴스의 addObject 메소드를 통해 모델 등록, 뒤의 숫자는 데이터가 표출되는 최소 레벨을 의미. 숫자가 낮을수록 멀리서 보임
             */
            Ppmap.getManager().modeler.addObject(building, 12);
		}
	});	
	

};


/**
 * wms요청
 * @see extrusion.js > extrusionModelWMSToggle()
 * @param {object} model {id:string, layername:string, ogctype:string}
 * @param {boolean} isShow 화면 표시 여부
 */
DesignLayer2Obj.prototype.toggleWms = function(model, isShow){
    let url = [LHDT.policy.geoserverDataUrl, LHDT.policy.geoserverDataStore, model.ogctype].join('/');
    //
    var imageryLayers = Ppmap.getViewer().imageryLayers;


    if(isShow) {
    	var currentCqlFilter = `design_layer_id=${model.id} AND enable_yn='Y'`;
        var prov = new Cesium.WebMapServiceImageryProvider({
            url : url,
            parameters : {
                transparent : true,
                srs:'EPSG:4326',
                format: "image/png",
                cql_filter : currentCqlFilter,
            },
            layers : model.layername
        });

        var imageryLayer = new Cesium.ImageryLayer(prov/*, {alpha : 0.7}*/);
        imageryLayer.layerId = model.id;
        imageryLayers.add(imageryLayer);

        //show된 레이어 목록
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
 * toggle extrusion model(building)
 * @param {Object} d {'data':object, 'layer':ImageryLayer}
 * @param {Boolean} isShow
 */
DesignLayer2Obj.prototype.toggleExtrusionBuilding = function(d, isShow){
    
    //높이 값 구하기
    //designLayerGroupType에 따라 property 속성이 다름
    let _height = function(data, entity){
        let h = null;

        //필지
        if(DesignLayerObj.GroupType.LAND['text'] == data.designLayerGroupType){
            h = Pp.nvl(entity.properties._maximum_building_floors.getValue(), null);
        }
        //건축물 높이
        if(DesignLayerObj.GroupType.BUILDING_HEIGHT['text'] == data.designLayerGroupType){
            h = Pp.nvl(entity.properties.build_maximum_floors.getValue(), null);
        }

        //
        return Pp.isEmpty(h) ? null : (h * HEIGHT_PER_FLOOR);
    };

    
    /**
     * get 색
     * 용도지역에 따른 색 리턴
     * @param {Object} data
     * @param {Entity} entity
     */ 
    let _color = function(data, entity){

        if(DesignLayerObj.GroupType.BUILDING_HEIGHT['text'] == data.designLayerGroupType){
            return data.layerFillColor;
        }
        
        let color={};
        color['가스공급설비'] = '#df6fc3';
        color['경관녹지'] = '#81fe02';
        color['공공녹지'] = '#a5dd00';
        color['공공청사'] = '#0080ff';
        color['공동주택(아파트)'] = '#febd00';
        color['공동주택(연립)'] = '#fee07e';
        color['공영차고지'] = '#e3e3e3';
        color['공장용지'] = '#de7fff';
        color['광장'] = '#dec171';
        color['근린공원'] = '#00de01';
        color['근린생활시설용지'] = '#fefa03';
        color['농업관련용지'] = '#bdfe7c';
        color['단독주택'] = '#ffff81';
        color['도로'] = '#ffffff';
        color['도서관'] = '#7e9fff';
        color['도시지원용지'] = '#00a5db';
        color['문화시설'] = '#7e9fff';
        color['보행자전용도로'] = '#d4a617';
        color['복합용지'] = '#ff809e';
        color['사회복지시설'] = '#7e9fff';
        color['상업용지'] = '#fd0002';
        color['수도용지'] = '#01dddd';
        color['시장'] = '#fe0002';
        color['어린이공원'] = '#00de01';
        color['업무시설'] = '#0080ff';
        color['연결녹지'] = '#81fe02';
        color['열공급설비'] = '#df6fc3';
        color['완충녹지'] = '#81fe02';
        color['운동장'] = '#00ba89';
        color['유원지'] = '#baff10';
        color['유통업무설비'] = '#ff00be';
        color['자동차정류장'] = '#de6d89';
        color['재활용회수시설'] = '#c1dd6f';
        color['저류지'] = '#7fe0ff';
        color['전기공급설비'] = '#df6fc3';
        color['종교용지'] = '#ff80ff';
        color['종합의료시설'] = '#7fbffd';
        color['주유소'] = '#dda46f';
        color['주제공원'] = '#00de01';
        color['주차장'] = '#c8c8c8';
        color['준주거용지'] = '#fefa03';
        color['체육공원'] = '#00de01';
        color['체육시설용지'] = '#6fdca3';
        color['폐기물처리시설'] = '#df6fc3';
        color['하수도시설'] = '#01dddd';
        color['하천'] = '#00c0fe';
        color['학교'] = '#01fffd';

        let landuseZoning = entity._properties._landuse_zoning._value;

        if(Pp.isNotEmpty(landuseZoning)){
            let c = color[landuseZoning];
            if(Pp.isEmpty(c)){
                console.log(landuseZoning);
                return '#efefef';
            }else{
                return c;
            }
        }

        //
        return '#efefef';
    };


    //
    if(!isShow){
        this.offExtrusionModel(d.data.designLayerId);
        this.offLimitInfo();
        //
        return;
    }

	//
    let imageryProvider = d.layer.imageryProvider;
    let layerName = imageryProvider.layers;
    let currentCqlFilter = imageryProvider._resource.queryParameters.cql_filter;

    let self = this;
    startLoading();
    //feature 정보 요청
    this.getFeatures({ 'typeNames': layerName, 'cql_filter': currentCqlFilter }, function (e) {
        let entities = e.entities.values;
        
        //
        for (let i = 0; i < entities.length; i++) {
            let entity = entities[i];
            var polygonHierarchy = entity.polygon.hierarchy.getValue().positions;

            let h = _height(d.data, entity);
            if(null == h){
                continue;
            }
            
            // let color = new Mago3D.Color.fromHexCode(d.data.layerFillColor);
            let color = new Mago3D.Color.fromHexCode(_color(d.data, entity));
            color.a = 0.5;
            var building = Mago3D.ExtrusionBuilding.makeExtrusionBuildingByCartesian3Array(polygonHierarchy.reverse(), h, {	
                color: color, /*new Mago3D.Color(color.r, color.b, color.b, 0.4)*/
                wireframeColor4 : color,
                heightReference : Mago3D.HeightReference.CLAMP_TO_GROUND
            });

            building.type = d.data.designLayerGroupType;
            building.layerId = entity.id;
            building.designLayerId = d.data.designLayerId;
            
            /**
             * magoManager에 속한 modeler 인스턴스의 addObject 메소드를 통해 모델 등록, 뒤의 숫자는 데이터가 표출되는 최소 레벨을 의미. 숫자가 낮을수록 멀리서 보임
             */
            Ppmap.getManager().modeler.addObject(building, 12);
            
            /**
             * 필지 폴리곤 정보로 건물에 제한 정보 설정
             */ 
            if(entity.properties.landuse_zoning && entity.properties.landuse_zoning.getValue() === '공동주택(아파트)') {
            	polygonHierarchy.pop();
                self.setLimitInfoByPolygon(polygonHierarchy, h);
            }
        }
        stopLoading();
    });
};



/**
 * extrusion model(필지 바닥정보로 높이를 올린 디자인 레이어) 지도에서 삭제하기
 * @param {*} designLayerId 
 */
DesignLayer2Obj.prototype.offExtrusionModel = function(designLayerId){
    var modeler = Ppmap.getManager().modeler;
        
    var extrusionBuildings = modeler.objectsArray;
    if(Pp.isEmpty(extrusionBuildings)){
        return;
    }


    //
    for(let i=0; i<extrusionBuildings.length; i++){
        let d = extrusionBuildings[i];
        if(Pp.isNull(d)){
            continue;
        }


        //console.log(building, designLayerId);
        if(d.designLayerId == designLayerId || d.layerId == designLayerId) {
            modeler.removeObject(d);
        }
    }
};



/**
 * 건물 제한 정보 해제, 임시로 건물 전체 해제
 */
DesignLayer2Obj.prototype.offLimitInfo = function() {
	Ppmap.getManager()
		.modeler
		.objectsArray
		.forEach(function(building) {
			building.setLimitationGeographicCoords(undefined);
			building.setLimitationHeight(0);
		}
	);
}



/**
 * 
 */
DesignLayer2Obj.prototype.getDatasAsync = function(datasetId){
	let self = this;
	
	
	$.get('../api/design-layers', function(res){
		let datas = [];
		
		if(res && res._embedded && res._embedded.designLayers){
			datas = res._embedded.designLayers;
		}
	
		self.getDatasCompleted(datas);
	});
};


/**
 * 
 */
DesignLayer2Obj.prototype.getDatasCompleted = function(datas){
	this.datas = datas;
	
	
	//화면에 표시
	var source = $("#design-layer-list-template").html();
	var template = Handlebars.compile(source);
	let html = template({datas: this.datas});
	this.$container.find('.design-layer-list-container').html(html);
	
	
	//이벤트 등록
	this.setEventHandler();
};


let designLayer2Obj = new DesignLayer2Obj();