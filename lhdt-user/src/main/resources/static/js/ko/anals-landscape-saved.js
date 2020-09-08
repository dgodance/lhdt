const analsSavedEntitiy = {
    point: [],
    line: [],
    removeThis: function() {
        this.point.forEach(p => MAGO3D_INSTANCE.getViewer().entities.remove(p));
        this.line.forEach(p => MAGO3D_INSTANCE.getViewer().entities.remove(p));
    }
}


/**
 * 표시
 * @param {string} id 경관 아이디
 */
function showData(id) {
	let _callback = function(diffObj){
		if(Pp.isEmpty(diffObj)){
			console.log(diffObj);
			alert('관련 정보가 존재하지 않습니다.');
			return;
		}
		
		//
		console.log(diffObj);
		
		//
		Ppmap.removeAll();
		//
		let entity = null;
		
		//
		if('점' === diffObj.landScapePointType){
			//점 생성
			entity = Ppmap.createPoint('', diffObj.startLandScapePos.x, diffObj.startLandScapePos.y);
		}else{
			//
			let xyz1 = {
				'lon': diffObj.startLandScapePos.x, 
				'lat': diffObj.startLandScapePos.y
			};
			//
			let xyz2 = {
				'lon': diffObj.endLandScapePos.x, 
				'lat': diffObj.endLandScapePos.y
			};
			
			//선 생성
			entity = Ppmap.createPolyline('ls-diff', [xyz1, xyz2]);			
		}
		
		//이동
		MAGO4D_INSTANCE.getViewer().zoomTo(entity);
		
		
		//
		//new SkylineObj().init().process(xyz1, xyz2);
		
		//
		/*
        analsSavedEntitiy.removeThis();
        if(diffObj.landScapePointType === '점') {
            const startAlt = diffObj.startAltitude;
            const endAlt = diffObj.endAltitude;

            const pos = {
                lon: diffObj.startLandScapePos.x,
                lat: diffObj.startLandScapePos.y
            }

            const p = new SkylineObj();
            const point1 = p.drawPoint(pos.lon, pos.lat);
            analsSavedEntitiy.point.push(point1);
            MAGO3D_INSTANCE.getViewer().zoomTo(point1);
        } else {

            const xyz1 = {
                lon: diffObj.startLandScapePos.x,
                lat: diffObj.startLandScapePos.y
            }
            const xyz2 = {
                lon: diffObj.endLandScapePos.x,
                lat: diffObj.endLandScapePos.y
            }

            const p = new SkylineObj();
            const resultEntitiy = p.drawTwoPointLine(xyz1, xyz2);
            analsSavedEntitiy.point.push(resultEntitiy.point1);
            analsSavedEntitiy.point.push(resultEntitiy.point2);
            analsSavedEntitiy.line.push(resultEntitiy.line);
            MAGO3D_INSTANCE.getViewer().zoomTo(resultEntitiy.line);
        }
		*/
	};
	
	
    $.get(LS_POINT_REST_URL + '/'+id).done(function(diffObj) {
		_callback(diffObj);
    });
}