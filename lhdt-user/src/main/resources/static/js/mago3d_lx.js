Mago3D.ManagerUtils.geographicToWkt = function(geographic, type) {
	var wkt = '';
	
	switch(type) {
		case 'POINT' : {
			wkt = 'POINT (';
			wkt += geographic.longitude;
			wkt += ' ';
			wkt += geographic.latitude;
			wkt += ')';
			break;
		}
		case 'LINE' : {
			wkt = 'LINESTRING (';
			for(var i=0,len=geographic.length;i<len;i++) {
				if(i>0) {
					wkt += ',';
				}
				wkt += geographic[i].longitude;
				wkt += ' ';
				wkt += geographic[i].latitude;
			}
			wkt += ')';
			break;
		}
		case 'POLYGON' : {
			wkt = 'POLYGON ((';
			for(var i=0,len=geographic.length;i<len;i++) {
				if(i>0) {
					wkt += ',';
				}
				wkt += geographic[i].longitude;
				wkt += ' ';
				wkt += geographic[i].latitude;
			}
			wkt += ',';
			wkt += geographic[0].longitude;
			wkt += ' ';
			wkt += geographic[0].latitude;
			wkt += '))';
			break;
		}
	}
	
	function coordToString(coord,str) {
		var text = str ? str : '';
		if(Array.isArray(coord)) {
			for(var i=0,len=coord.length;i<len;i++) {
				coordToString(coord[i],text);
			}
		} else {
			if(text) {
				text += ',';
			}
			text += coord.longitude;
			text += ' ';
			text += coord.latitude;
		}
		
		return text;
	}
	
	return wkt;
}

Mago3D.ManagerUtils.getCoordinateFromWKT = function(wkt, type) {
	switch(type) {
		case 'POINT' : {
			var removePrefix = wkt.replace(/\bpoint\b\s*\(/i, "");
			var removeSuffix = removePrefix.replace(/\s*\)\s*$/, "");
			var coordinates = removeSuffix.match(/[+-]?\d*(\.?\d+)/g);
			return coordinates;
		}
	}
}

Mago3D.MagoManager.prototype.validTerrainHeight = function() {
	var allVisible = this.frustumVolumeControl.getAllVisiblesObject();
	var nodeMap = allVisible.nodeMap;
	var nativeMap = allVisible.nativeMap;
	var that = this;
	for(var k in nodeMap) {
		if(nodeMap.hasOwnProperty(k)) {
			var node = nodeMap[k];
			var data = node.data;
			if(!node.bboxAbsoluteCenterPos || data.valid) continue;

			new Mago3D.Promise(function(resolve) {
				node.data.valid = true;
				resolve({mm:that,n:node});
			}).then(function(obj){
				var n = obj.n;
				var bbox = n.getBBox();
				var gg = Mago3D.ManagerUtils.pointToGeographicCoord(n.bboxAbsoluteCenterPos);
				var viewer = MAGO3D_INSTANCE.getViewer();
				var promise = Cesium.sampleTerrain(viewer.terrainProvider, 17, [Cesium.Cartographic.fromDegrees(gg.longitude, gg.latitude)]);
				promise.then(function(t){
					var h = t[0].height;
					var cp = n.getCurrentGeoLocationData().geographicCoord;
					n.changeLocationAndRotation(cp.latitude, cp.longitude, h - bbox.minZ, 0,0,0, obj.mm);
				});
			});
		}
	}

	for(var k in nativeMap) {
		if(nativeMap.hasOwnProperty(k)) {
			var model = nativeMap[k];
			if(model.valid) continue;
			
			new Mago3D.Promise(function(resolve) {
				model.valid = true;
				resolve({m:model});
			}).then(function(obj){
				var m = obj.m;
				var geoLocData = m.getCurrentGeoLocationData();
				var geoCoord = geoLocData.geographicCoord;
				var viewer = MAGO3D_INSTANCE.getViewer();
				var promise = Cesium.sampleTerrain(viewer.terrainProvider, 17, [Cesium.Cartographic.fromDegrees(geoCoord.longitude, geoCoord.latitude)]);
				promise.then(function(t){
					m.setTerrainHeight(t[0].height);
				});
			});
		}
	}
	
}