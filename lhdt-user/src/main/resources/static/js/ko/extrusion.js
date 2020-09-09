var extrusionTools = function (magoInstance){
	var magoManager = magoInstance.getMagoManager();
	
	var rotate = new Mago3D.RotateInteraction();
	magoManager.interactionCollection.add(rotate);
	
	var upanddown = new Mago3D.NativeUpDownInteraction();
	magoManager.interactionCollection.add(upanddown);
	
	
	var addStaticModelByPointInteraction = new Mago3D.ClickInteraction({
		handleUpEvent : function(e) {
			var selectedElem = $('#dataLibraryDHTML li.listElement div.data-library.on');
			var dataLibrary = selectedElem.data();
			var cartographic = e.point.geographicCoordinate;
			
			var model = {};
			model.projectId = dataLibrary.id;
			model.projectFolderName = dataLibrary.path;
			
			
			//to fix
			model.projectFolderName = model.projectFolderName.split(dataLibrary.key)[0];
			model.projectFolderName = model.projectFolderName.replace(/\/+$/, '');
			model.buildingFolderName = 'F4D_'+dataLibrary.key;
			
			if(!this.manager.isExistStaticModel(model.projectId)) this.manager.addStaticModel(model);
			
			this.manager.instantiateStaticModel({
				projectId : model.projectId,
				instanceId : parseInt(Math.random() * 1000),
				longitude : cartographic.longitude,
				latitude : cartographic.latitude,
				height : 0
			});
		}
	}); 
	magoManager.interactionCollection.add(addStaticModelByPointInteraction);
	
	var viewer = magoInstance.getViewer();
	var drawDataLibararyByLine = new LineDrawer(viewer, function(linePosition) {
		var selectedElem = $('#dataLibraryDHTML li.listElement div.data-library.on');
		var dataLibrary = selectedElem.data();
		
		var model = {};
		model.projectId = dataLibrary.id;
		model.projectFolderName = dataLibrary.path;
		
		//to fix
		model.projectFolderName = model.projectFolderName.split(dataLibrary.key)[0];
		model.projectFolderName = model.projectFolderName.replace(/\/+$/, '');
		model.buildingFolderName = 'F4D_'+dataLibrary.key;
		
		if(!magoManager.isExistStaticModel(model.projectId)) magoManager.addStaticModel(model);
		
		for(var i=0,len=linePosition.length-1;i<len;i++) {
			var line1 = worldCoordToGeographic(linePosition[i]); 
			var line2 = worldCoordToGeographic(linePosition[i+1]);
			
			var dataPositions = Mago3D.GeographicCoordSegment.getArcInterpolatedGeoCoords(line1, line2, 10);
			for(var j in dataPositions) {
				var dataPosition = dataPositions[j];
				magoManager.instantiateStaticModel({
					projectId : model.projectId,
					instanceId : parseInt(Math.random() * 1000),
					longitude : dataPosition.longitude,
					latitude : dataPosition.latitude,
					height : 0
				});
			}
		}
	}, {
		// https://cesium.com/docs/cesiumjs-ref-doc/PolylineGraphics.html?classFilter=LINEGRA
		// positions는 제외.
		lineStyle : {
			width : 3,
			clampToGround : true,
			material :  Cesium.Color.YELLOW
		}
	});
	
	var selectDataLibararyInArea = new PolygonDrawer(viewer,  function(cartesians) {
		var polygon2D = Mago3D.Polygon2D.makePolygonByCartesian3Array(cartesians);
		
		var selectionManager = magoManager.selectionManager;
		var selected = selectionManager.selectionByPolygon2D(polygon2D, 'f4d');
	});
	
	var selectExtrusionInArea = new PolygonDrawer(viewer,  function(cartesians) {
		var polygon2D = Mago3D.Polygon2D.makePolygonByCartesian3Array(cartesians);
		
		var selectionManager = magoManager.selectionManager;
		var selected = selectionManager.selectionByPolygon2D(polygon2D, 'native');
	}); 
	
	init();
	
	function init()
	{
		setEventHandler();

	}

	function extrusionModelToggle(model, on) {
		var requestType = model.ogctype;
		var toggleFunc;
		switch(requestType) {
			case 'wms' : toggleFunc = extrusionModelWMSToggle;break;
			//case 'wfs' : toggleFunc = (model.division === 'building') ? extrusionModelBuildingToggle : extrusionModelEntityToggle;break;
			case 'wfs' : toggleFunc = extrusionModelBuildingToggle;break;
		}
		
		toggleFunc.call(this, model, on);
	}
	
	function extrusionModelWMSToggle(model, on) {
		var imageryLayers = magoInstance.getViewer().imageryLayers;
		if(on) {
			var prov = new Cesium.WebMapServiceImageryProvider({
			    url : 'http://localhost:18080/geoserver/lhdt/wms',
			    parameters : {
			    	transparent : true,
			    	srs:'EPSG:4326',
			    	format: "image/png",
			    	cql_filter : `design_layer_id=${model.id}`
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
	}
	
	var wfsResource = new Cesium.Resource({
		url : 'http://localhost:18080/geoserver/lhdt/wfs',
		queryParameters : {
			service : 'wfs',
			version : '1.0.0',
			request : 'GetFeature',
			srsName : 'EPSG:3857',
			outputFormat : 'application/json'
		}
	});
	var FLOOR_HEIGHT = 3.3;
	
	function extrusionModelBuildingToggle(model, on) {
		if(on) {
			var res = wfsResource.clone();
			res.queryParameters.typeNames = model.layername;
			res.queryParameters.cql_filter = `design_layer_id=${model.id}`;
			 
			var loader = new Cesium.GeoJsonDataSource().load(res).then(function(e){
				var entities = e.entities.values;
				
	   	 		for(var i in entities) {
	   	 			var entity = entities[i];
		   	 		var polygonHierarchy  = entity.polygon.hierarchy.getValue().positions;
		   	 		
		   	 		var building = Mago3D.ExtrusionBuilding.makeExtrusionBuildingByCartesian3Array(polygonHierarchy.reverse(), FLOOR_HEIGHT * 7)
		   	 		
		   	 		building.layerId = model.id; 
		   	 		magoManager.modeler.addObject(building, 12);
	   	 		}
			});
		} else {
			var modeler = magoManager.modeler;
			
			var modelList = modeler.objectsArray;
			modelList.forEach(function(building){
				if(building.layerId === model.id) {
					modeler.removeObject(building);
				}
			});
		}
	}
	
	var DataLibraryController = function(){
		this.drawDataLibararyByPoint = function(on){
			if(on) {
				addStaticModelByPointInteraction.setActive(true);
			} else {
				addStaticModelByPointInteraction.setActive(false);
			}
		}
		this.drawDataLibararyByLine = function(on){
			if(on) {
				drawDataLibararyByLine.setActive(true);
			} else {
				drawDataLibararyByLine.setActive(false);
			}
		}
		this.selectDataLibarary = function(on){
			if(on) {
				magoManager.defaultSelectInteraction.setTargetType('f4d');
				magoManager.defaultSelectInteraction.setActive(true);
				magoManager.isCameraMoved = true;
			} else {
				magoManager.defaultSelectInteraction.setActive(false);
			}
		}
		this.selectDataLibararyInArea = function(on){
			if(on) {
				selectDataLibararyInArea.setActive(true);
			} else {
				selectDataLibararyInArea.setActive(false);
			}
		}
		this.translateDataLibarary = function(on){
			if(on) {
				magoManager.defaultTranslateInteraction.setTargetType('f4d');
				magoManager.defaultTranslateInteraction.setActive(true);
			} else {
				magoManager.defaultTranslateInteraction.setActive(false);
			}
			this.selectDataLibarary(on);
		}
		
		this.rotateDataLibarary = function(on){
			if(on) {
				rotate.setTargetType('f4d');
				rotate.setActive(true);
			} else {
				rotate.setActive(false);
			}
			this.selectDataLibarary(on);
		}
		
		this.deleteDataLibarary = function(on){
			var selectionManager = magoManager.selectionManager;
			var selectedData = magoManager.selectionManager.getSelectedF4dNode();
			if(!selectedData) {
				alert('선택된 데이터가 없습니다');
				return;
			}
			magoInstance.getF4dController().deleteF4dMember(selectedData.data.projectId, selectedData.data.nodeId);
			magoManager.defaultSelectInteraction.clear();
			selectionManager.clearCurrents();
		}
	};
	
	var ExtrusionModelController = function() {
		this.selectExtrusion = function(on) {
			if(on) {
				magoManager.defaultSelectInteraction.setTargetType('native');
				magoManager.defaultSelectInteraction.setActive(true);
				magoManager.isCameraMoved = true;
			} else {
				magoManager.defaultSelectInteraction.setActive(false);
			}
		}
		this.selectExtrusionInArea = function(on){
			if(on) {
				selectExtrusionInArea.setActive(true);
			} else {
				selectExtrusionInArea.setActive(false);
			}
		}
		this.checkDistanceExtrusion = function() {
			var selectionManager = magoManager.selectionManager;
			var selectedDatas = selectionManager.getSelectedGeneralArray();

			var checkCache = {};
			var faceMap = {};

			var loopCnt = selectedDatas.length;
			for(var i=0; i<loopCnt;i++) {
				var m1 = selectedDatas[i];
				var guid1 = m1._guid;
				if(!checkCache[guid1]) checkCache[guid1] = {};
				
				var m1GeoLocationData = m1.getCurrentGeoLocationData();
				var tmat1 = m1GeoLocationData.tMatrix;
				var mesh1 = m1.objectsArray[0];
				var bs1 = mesh1.getBoundingSphere();
				var bs1CenterWC = tmat1.transformPoint3D(bs1.centerPoint);
				var sArray1 = mesh1.surfacesArray;
				var sArray1Length = sArray1.length;
				
				for(var j=0; j<loopCnt; j++) {
					var m2 = selectedDatas[j];
					var guid2 = m2._guid;
					if(!checkCache[guid2]) checkCache[guid2] = {};
					
					if(guid1 === guid2 || checkCache[guid1][guid2] || checkCache[guid2][guid1]) continue;
					checkCache[guid1][guid2] = true;
					checkCache[guid2][guid1] = true;
					
					var m2GeoLocationData = m2.getCurrentGeoLocationData();
					var tmat2 = m2GeoLocationData.tMatrix;
					var mesh2 = m2.objectsArray[0];
					var bs2 = mesh2.getBoundingSphere();
					var bs2CenterWC = tmat2.transformPoint3D(bs2.centerPoint);
					
					var sphereDistance = bs1CenterWC.distToPoint(bs2CenterWC) - bs1.r - bs2.r;
					if(sphereDistance > 0) continue;
					
					var minSurface1;
					var minSurface2;
					var minSurfaceDistance = Infinity;
					
					var sArray2 = mesh2.surfacesArray;
					var sArray2Length = sArray2.length;
					
					for(var k=0;k<sArray1Length;k++) {
						var s1 = sArray1[k];
						var sbs1 = s1.getBoundingSphere();
						var sbs1CenterWC = tmat1.transformPoint3D(sbs1.centerPoint);
						for(var x=0;x<sArray2Length;x++) {
							s2 = sArray2[x];
							var sbs2 = s2.getBoundingSphere();
							var sbs2CenterWC = tmat2.transformPoint3D(sbs2.centerPoint);
							
							var surfaceDistance = sbs1CenterWC.distToPoint(sbs2CenterWC) - sbs1.r - sbs2.r;
							if(minSurfaceDistance > surfaceDistance) {
								minSurface1 = s1;
								minSurface2 = s2;
								minSurfaceDistance = surfaceDistance;
							}
						}
					}
					
					var face1;
					var face2;
					var fArray1 = minSurface1.facesArray;
					var fArray1Length = fArray1.length;
					var fArray2 = minSurface2.facesArray;
					var fArray2Length = fArray2.length;
					var minFaceDistance = Infinity;
					for(var k=0;k<fArray1Length;k++) {
						var f1 = fArray1[k];
						var fbs1 = f1.getBoundingSphere();
						var fbs1CenterWC = tmat1.transformPoint3D(fbs1.centerPoint);
						for(var x=0;x<fArray2Length;x++) {
							f2 = fArray2[x];
							var fbs2 = f2.getBoundingSphere();
							var fbs2CenterWC = tmat2.transformPoint3D(fbs2.centerPoint);
							
							var faceDistance = fbs1CenterWC.distToPoint(fbs2CenterWC) - fbs1.r - fbs2.r;
							if(minFaceDistance > surfaceDistance) {
								face1 = f1;
								face2 = f2;
							}
						}
					}
					if(!faceMap[face1._guid]) faceMap[face1._guid] = {face : face1, tMat : tmat1};
					if(!faceMap[face2._guid]) faceMap[face2._guid] = {face : face2, tMat : tmat2};
				}
			}
			var faceCnt = Object.keys(faceMap).length;
			
			if(faceCnt === 0) {
				alert('가까운거 없읍니다..');
				return false;
			}
			
			var r= 0, g=0, b=0;
			//New color every time it's called
			var fadeColor = new Cesium.CallbackProperty(function(time, result){
				    b += 4;
				    if(b > 255) b = 0;
			        return Cesium.Color.fromBytes(0, 0, b, 255,result);
			}, false);
			
			for(var y in faceMap) {
				var face = faceMap[y].face;
				var tmat = faceMap[y].tMat;
				var vertexArray = face.vertexArray;
				var positions = [];
				for(var v in vertexArray) {
					var vertex = vertexArray[v];
					var vP3d = tmat.transformPoint3D(vertex.point3d);
					positions.push(new Cesium.Cartesian3(vP3d.x, vP3d.y, vP3d.z));
				}
				
				magoInstance.getViewer().entities.add(new Cesium.Entity({
					wall : new Cesium.WallGraphics({
						positions : positions,
						material : new Cesium.ColorMaterialProperty(fadeColor)
					})
				}));
			}
			
			selectionManager.clearCurrents();
		}
		this.translateExtrusion = function(on){
			if(on) {
				magoManager.defaultTranslateInteraction.setTargetType('native');
				magoManager.defaultTranslateInteraction.setActive(true);
			} else {
				magoManager.defaultTranslateInteraction.setActive(false);
			}
			this.selectExtrusion(on);
		}
		
		this.rotateExtrusion = function(on){
			if(on) {
				rotate.setTargetType('native');
				rotate.setActive(true);
			} else {
				rotate.setActive(false);
			}
			this.selectExtrusion(on);
		}
		
		this.updownExtrusion = function(on) {
			if(on) {
				upanddown.setActive(true);
			} else {
				rotate.setActive(false);
			}
			this.selectExtrusion(on);
		}
		
		this.deleteExtrusion = function(){
			var selectionManager = magoManager.selectionManager;
			var selectedData = selectionManager.getSelectedGeneral();
			if(!selectedData) {
				alert('선택된 데이터가 없습니다');
				return;
			}
			magoManager.modeler.removeObject(selectedData);
			selectionManager.clearCurrents();
			magoManager.defaultSelectInteraction.clear();
		}
	}
	
	
	var dataLibraryController = new DataLibraryController();
	var extrusionModelController = new ExtrusionModelController();
	
	function setEventHandler() {
		$('#extrusionModel').on('click','#designLayerDHTML li.listElement div', function() {
			if($(this).hasClass('on')) {
				$(this).removeClass('on').css({
					backgroundColor: '#ffffff'
				});
				extrusionModelToggle($(this).data(), false);
			} else {
				$(this).addClass('on').css({
					backgroundColor: '#ff4422'
				});
			    
				extrusionModelToggle($(this).data(), true);
			}
		});
		
		$('#dataLibrary').on('click','#dataLibraryDHTML li.listElement div.data-library', function() {
			if($(this).hasClass('on')) {
				$(this).removeClass('on').css({
					backgroundColor: '#ffffff'
				});
			} else {
				$('#dataLibraryDHTML li.listElement div.data-library').each(function(){
					$(this).removeClass('on').css({
						backgroundColor: '#ffffff'
					});
				});
				$(this).addClass('on').css({
					backgroundColor: '#ff4422'
				});
			}
		});


		/*var dialogEMOn = false;
		var dialogDLOn = false;
		
		$("#designLayerToolToggle").click(function() {
			designLayerDialog.dialog( "open" );
			dataLibraryDialog.dialog( "close" );
		});
		
		$("#dataLibraryToolToggle").click(function() {
			dataLibraryDialog.dialog( "open" );
			designLayerDialog.dialog( "close" );
		});
		$('#dataLibrary li').click(function() {
			if(!dialogDLOn) {
				dialogDLOn = true;
				$('#dialogDataLibrary').show();
				dialogEMOn = false;
				$('#dialogExtrusion .btnGroup button[data-runtype="toggle"]').each(function(){
					if($(this).hasClass('on')) {
						offBtn($(this), extrusionModelController);
					}
				});
				$('#dialogExtrusion').hide();
			} else {
				
				dialogDLOn = false;
				$('#dialogDataLibrary .btnGroup button[data-runtype="toggle"]').each(function(){
					if($(this).hasClass('on')) {
						offBtn($(this), dataLibraryController);
					}
				});
				$('#dialogDataLibrary').hide();
				
			}
		});
		$('#extrusionModelToolToggle').click(function(){
			if(!dialogEMOn) {
				dialogEMOn = true;
				$('#dialogExtrusion').show();
				
				dialogDLOn = false;
				$('#dialogDataLibrary .btnGroup button[data-runtype="toggle"]').each(function(){
					if($(this).hasClass('on')) {
						offBtn($(this), dataLibraryController);
					}
				});
				$('#dialogDataLibrary').hide();
				
			} else {
				dialogEMOn = false;
				$('#dialogExtrusion .btnGroup button[data-runtype="toggle"]').each(function(){
					if($(this).hasClass('on')) {
						offBtn($(this), extrusionModelController);
					}
				});
				$('#dialogExtrusion').hide();
			}
		});*/
		
		
		
		$('#dataLibraryDialog .btnGroup button[data-runtype="toggle"]').click(function(){
			
			var selected = $('#dataLibraryDHTML li.listElement div.data-library.on');
			if(selected.length === 0) {
				alert('선택된 라이브러리가 없습니다').
				return;
			}
			
			$(this).siblings().each(function(){
				if($(this).hasClass('on')) {
					offBtn($(this), dataLibraryController);
				}
			});
			
			if($(this).hasClass('on')) {
				offBtn($(this), dataLibraryController);
			} else {
				$(this).addClass('on').css({
					color: '#fff',
					border: '1px solid #555',
					backgroundColor: '#555'
				});
				dataLibraryController[$(this).attr('id')](true);
			}
		});
		
		$('#dataLibraryDialog .btnGroup button[data-runtype="run"]').click(function(){
			dataLibraryController[$(this).attr('id')]();
		});
		
		$('#designLayerDialog .btnGroup button[data-runtype="toggle"]').click(function(){
			$(this).siblings().each(function(){
				if($(this).hasClass('on')) {
					offBtn($(this), extrusionModelController);
				}
			});
			
			if($(this).hasClass('on')) {
				offBtn($(this), extrusionModelController);
			} else {
				$(this).addClass('on').css({
					color: '#fff',
					border: '1px solid #555',
					backgroundColor: '#555'
				});
				extrusionModelController[$(this).attr('id')](true);
			}
		});
		
		$('#designLayerDialog .btnGroup button[data-runtype="run"]').click(function(){
			extrusionModelController[$(this).attr('id')]();
		});
		
		function offBtn($obj, instance) {
			$obj.removeClass('on').css({
				color: '#333',
				border: '1px solid #555',
				backgroundColor: '#fff'
			});
			instance[$obj.attr('id')](false);
		}
	}
	
	/**
	 * 스크린픽셀을 월드좌표로 변경
	 * @param screen
	 * @returns {Mago3D.Point3D}
	 */
	function screenToWorldCoord(screen){
		return Mago3D.ManagerUtils.screenCoordToWorldCoord(undefined, screen.x, screen.y, undefined, undefined, undefined, undefined, magoManager);
	}

	/**
	 * 월드좌표를 경위도좌표로 변경
	 * @param wc
	 * @returns {Mago3D.GeographicCoord}
	 */
	function worldCoordToGeographic(wc){
		return Mago3D.ManagerUtils.pointToGeographicCoord(wc, undefined, magoManager);
	}

	/**
	 * 스크린픽셀을 경위도좌표로 변경
	 * @param screen
	 * @returns {Mago3D.Point3D}
	 */
	function screenToGeographicCoord(screen){
		return worldCoordToGeographic(screenToWorldCoord(screen));
	}
};