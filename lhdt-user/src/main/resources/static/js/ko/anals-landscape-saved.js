const analsSavedEntitiy = {
    point: [],
    line: [],
    removeThis: function() {
        this.point.forEach(p => ppmap.getViewer().entities.remove(p));
        this.line.forEach(p => ppmap.getViewer().entities.remove(p));
    }
}

function showData(id) {
    $.get('http://localhost:9091/adminsvc/ls-point-rest/'+id).done(function(diffObj) {
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
            ppmap.getViewer().zoomTo(point1);
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
            ppmap.getViewer().zoomTo(resultEntitiy.line);
        }
    });
}