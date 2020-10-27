var AnalsCutFill = function(viewer, magoInstance) {
    var magoManager = magoInstance.getMagoManager();
    this._viewer = viewer;
    this._scene = viewer.scene;
    var handler = new Cesium.ScreenSpaceEventHandler(viewer.canvas);

    this._totalArea = 0;
    this._polylines = [];
    this._labels = [];
    this._polyPoint = [];
    this._cutPoly = [];
    this._fillPoly = [];

    var drawingMode = undefined;
    var activeShapePoints = [];
    var activeShape;
    var activeLabel;

    $('#cutFillProcessToggle').change(function() {
        clearObj();
        clearMagoInit();
        if($('#cutFillProcessToggle').is(':checked')) {
            changeLightingAPI(MAGO3D_INSTANCE, 0.3);
            toastr.info('지도상에서 여러 점을 선택(클릭)하시기 바랍니다.<br/>마우스 오른쪽 버튼을 클릭하면 분석이 완료됩니다.');
            drawingMode = 'cutFillAvgAnals';
            startDrawPolyLine();
        } else {
            drawingMode = "";
        }
    });

    function clearObj() {
        _polylines.forEach(obj => viewer.entities.remove(obj));
        _labels.forEach(obj => viewer.entities.remove(obj));
        _polyPoint.forEach(obj => viewer.entities.remove(obj));
        _cutPoly.forEach(obj => viewer.entities.remove(obj));
        _fillPoly.forEach(obj => viewer.entities.remove(obj));
        _polylines = [];
        _labels = [];
        _polyPoint = [];
        _cutPoly = [];
        _fillPoly = [];
        _totalArea = 0;

    }

    function clearMagoInit() {
        deleteAllChangeColorAPI(magoInstance);
        changeLightingAPI(MAGO3D_INSTANCE, 0.7);
    }

    function getColor(v, min, max) {
        function getC(f, l, r) {
            return {
                r: Math.floor((1 - f) * l.r + f * r.r),
                g: Math.floor((1 - f) * l.g + f * r.g),
                b: Math.floor((1 - f) * l.b + f * r.b),
            };
        }

        var left = { r: 127, g: 191, b: 253 },
            middle = { r: 246, g: 128, b: 251 },
            right = { r: 250, g: 134, b: 127 },
            mid = (max - min) / 2;

        return v < min + mid ?
            getC((v - min) / mid, left, middle) :
            getC((v - min - mid) / mid, middle, right);
    }
    $('#cutFillProcessBtn').click(function() {
        clearObj();
        clearMagoInit()
    });

    function analsCutFill() {
        const geometryInfo = [];
        let resultGeometry = "";
        for(let i=0; i<_polyPoint.length; i++){
            let d = _polyPoint[i];
            // geometryInfo.push({'longitude': d.lon, 'latitude': d.lat});
            const strLon = d.lon + "";
            const strLat = d.lat + "";
            resultGeometry += strLon + " " + strLat + ", ";
        }
        if(_totalArea > 200000) {
            alert('총 면적을 20000 ㎡ 이하로 설정해주세요');
            return;
        }
        if(resultGeometry === "") {
            alert('좌표 정보가 없습니다. 좌표정보를 설정해주세요!');
            return;
        }
        //resultGeometry = resultGeometry.slice(0,-2)
        const dumi = _polyPoint[0].lon + " " + _polyPoint[0].lat;
        resultGeometry += dumi;
        let d = _polyPoint[0];
        geometryInfo.push({'longitude': d.lon, 'latitude': d.lat});

        const param = {
            command: "raster_cut_fill",
            parameter: {
                fileName: 'srtm_korea_dem',
                coord: '4326',
                boundingBox: [122.99986111111112, 32.999861111111116,132.0001388888889, 44.00013888888889],
                inputGeometry: resultGeometry,
                userMeanHeight : -9999
            }
        };

        // 성절토 요청하는 부분 구현 ajax로 요청
        $.ajax({
            url: "http://118.42.112.206:5000/spatial-analysis",
            type: "POST",
            data: JSON.stringify(param),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8'
        }).done(function(data) {
            // if(Pp.isEmpty(data) || Pp.isEmpty(data._embedded)){
            //     console.log('empty data', data);
            //     return;
            // }
            const cutInfo = data.result[0];
            {
                const coordinates = cutInfo.coordinates;
                const category = cutInfo.category;
                const area = cutInfo.area;
                const count = cutInfo.count;
                const volume = cutInfo.volume;
                renderCutFill(coordinates, 'cut');
                dispCutModal(area, count, volume);
            }

            const fillInfo = data.result[1];
            {
                const coordinates = fillInfo.coordinates;

                const category = fillInfo.category;
                const area = fillInfo.area;
                const count = fillInfo.count;
                const volume = fillInfo.volume;
                renderCutFill(coordinates, 'fill');
                dispFillModal(area, count, volume);
            }
            showModal();
        });
    }
    function dispCutModal(area, count, volume) {
        $('#cut-area').text(area);
        $('#cut-count').text(count);
        $('#cut-volume').text(volume);
    }
    function dispFillModal(area, count, volume) {
        $('#fill-area').text(area);
        $('#fill-count').text(count);
        $('#fill-volume').text(volume);
    }

    function renderCutFill(coordinates, type) {
        let material = new Cesium.ColorMaterialProperty(Cesium.Color.DARKGOLDENROD.withAlpha(0.5));
        if(type === 'cut') {
            material = new Cesium.ColorMaterialProperty(Cesium.Color.BLACK.withAlpha(0.5));
        }
        debugger;
        for(const obj of coordinates) {
            const polygons = obj['outerBoundaryIs'];
            const polyPos = []
            for(const polygon of polygons) {
                for(const point of polygon) {
                    const pointPos = point.split(',');
                    const lon = parseFloat(pointPos[0]);
                    const lat = parseFloat(pointPos[1]);
                    polyPos.push(lon);
                    polyPos.push(lat);
                }
            }

            const poly = viewer.entities.add({
                name     : "Polygon for volum measurement",
                polygon: {
                    hierarchy: Cesium.Cartesian3.fromDegreesArray(polyPos),
                    material: material,
                    /* height: 0.1, */
                    heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
                }
            });
            if(type === 'cut') {
                _cutPoly.push(poly);
            } else {
                _fillPoly.push(poly);
            }
        }
    }

    function clearEvent() {
        if(handler.getInputAction(2) !== undefined) {
            handler.removeInputAction(2);
            console.log('remove');
        } else if(handler.getInputAction(7) !== undefined) {
            handler.removeInputAction(7);
            console.log('remove');
        }
    }

    function startDrawPolyLine() {
        clearEvent();

        var dynamicPositions = new Cesium.CallbackProperty(function () {
            if(drawingMode === 'cutFillAvgAnals') {
                return new Cesium.PolygonHierarchy(activeShapePoints);
            } else {
                return activeShapePoints;
            }
        }, false);

        handler.setInputAction(function(event) {
            if(drawingMode === 'cutFillAvgAnals') {
                var earthPosition = viewer.scene.pickPosition(event.position);
                if (Cesium.defined(earthPosition)) {
                    var cartographic = Cesium.Cartographic.fromCartesian(earthPosition);
                    var tempPosition = Cesium.Cartesian3.fromDegrees(Cesium.Math.toDegrees(cartographic.longitude), Cesium.Math.toDegrees(cartographic.latitude));
                    activeShapePoints.push(tempPosition);

                    if (activeShapePoints.length === 1) {
                        activeShape = drawShape(dynamicPositions);
                        if (drawingMode === 'cutFillAvgAnals') {
                            activeLabel = viewer.entities.add({
                                name     : "TempLabel for area measurement",
                                position: dynamicCenter,
                                label: {
                                    text: dynamicLabel,
                                    font: 'bold 20px sans-serif',
                                    fillColor: Cesium.Color.BLUE,
                                    style: Cesium.LabelStyle.FILL,
                                    verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
                                    disableDepthTestDistance: Number.POSITIVE_INFINITY,
                                    heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
                                }
                            });
                        }
                    }
                    else {
                        this._labels.push(drawLabel(tempPosition));
                    }
                    this._polyPoint.push({
                        lon: Cesium.Math.toDegrees(cartographic.longitude),
                        lat: Cesium.Math.toDegrees(cartographic.latitude)
                    });
                    this._polylines.push(createPoint(tempPosition));
                }
            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

        handler.setInputAction(function (event) {
            if(drawingMode === 'cutFillAvgAnals') {
                terminateShape();
                analsCutFill();
                $('#cutFillProcessToggle').click();
                toastr.info('성절토 분석을 시작합니다');
            }
        }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
    }

    // Redraw the shape so it's not dynamic and remove the dynamic shape.
    function terminateShape() {
        // activeShapePoints.pop();
        lengthInMeters = 0;
        areaInMeters = 0
        this._polylines.push(drawShape(activeShapePoints));
        if (drawingMode === 'cutFillAvgAnals')  this._labels.push(drawAreaLabel());

        viewer.entities.remove(activeShape);
        viewer.entities.remove(activeLabel);

        activeShape = undefined;
        activeLabel = undefined;
        activeShapePoints = [];
    }

    function drawAreaLabel() {
        var label;
        var bs = Cesium.BoundingSphere.fromPoints(activeShapePoints);
        var position = Cesium.Ellipsoid.WGS84.scaleToGeodeticSurface(bs.center);
        _totalArea = getArea(activeShapePoints);

        label = viewer.entities.add({
            name     : "Label for area measurement",
            position: position,
            label: {
                text: _totalArea,
                font: 'bold 20px sans-serif',
                fillColor: Cesium.Color.BLUE,
                style: Cesium.LabelStyle.FILL,
                verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
                disableDepthTestDistance: Number.POSITIVE_INFINITY,
                heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
            }
        });

        return label;
    }

    function drawShape(positionData) {
        var shape;
        if (drawingMode === 'cutFillAvgAnals') {
            shape = viewer.entities.add({
                name     : "Polygon for area measurement",
                polygon: {
                    hierarchy: positionData,
                    material: new Cesium.ColorMaterialProperty(Cesium.Color.YELLOW.withAlpha(0.3)),
                    /* height: 0.1, */
                    //heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
                }
            });
        }
        return shape;
    }

    function drawLabel(positionData) {
        var label;
        // if (drawingMode === 'line') {
        label = viewer.entities.add({
            position: positionData,
            label: {
                text: getLineLength(activeShapePoints),
                font: 'bold 20px sans-serif',
                fillColor: Cesium.Color.YELLOW,
                style: Cesium.LabelStyle.FILL,
                verticalOrigin: Cesium.VerticalOrigin.BOTTOM,
                disableDepthTestDistance: Number.POSITIVE_INFINITY,
                heightReference: Cesium.HeightReference.CLAMP_TO_GROUND/*
				 * ,
				 * pixelOffset :
				 * new
				 * Cesium.Cartesian2(5,
				 * 20)
				 */
            }
        });
        // }
        return label;
    }

    function createPoint(worldPosition) {
        var entity = viewer.entities.add({
            position: worldPosition,
            point: {
                color: Cesium.Color.YELLOW,
                pixelSize: 5,
                outlineColor: Cesium.Color.BLACK,
                outlineWidth: 2,
                disableDepthTestDistance: Number.POSITIVE_INFINITY,
                heightReference: Cesium.HeightReference.CLAMP_TO_GROUND
            }
        });
        return entity;
    }

    var dynamicCenter = new Cesium.CallbackProperty(function () {
        var bs = Cesium.BoundingSphere.fromPoints(activeShapePoints);
        return Cesium.Ellipsoid.WGS84.scaleToGeodeticSurface(bs.center);
    }, false);

    var dynamicLabel = new Cesium.CallbackProperty(function () {
        return getArea(activeShapePoints);
    }, false);

    function getArea(positions) {
        areaInMeters = 0;
        if (positions.length >= 3)
        {
            var points = [];
            for(var i = 0, len = positions.length; i < len; i++)
            {
                // points.push(Cesium.Cartesian2.fromCartesian3(positions[i]));
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
                // areaInMeters +=
                // Cesium.PolygonPipeline.computeArea2D([points[triangles[i]],
                // points[triangles[i + 1]], points[triangles[i + 2]]]);
                areaInMeters += calArea(points[triangles[i]], points[triangles[i + 1]], points[triangles[i + 2]]);
            }
        }
        return formatArea(areaInMeters);
    }
    function calArea(t1, t2, t3, i) {
        var r = Math.abs(t1.x * (t2.y - t3.y) + t2.x * (t3.y - t1.y) + t3.x * (t1.y - t2.y)) / 2;
        var cartographic = new Cesium.Cartographic((t1.x + t2.x + t3.x) / 3, (t1.y + t2.y + t3.y) / 3);
        var cartesian = viewer.scene.globe.ellipsoid.cartographicToCartesian(cartographic);
        var magnitude = Cesium.Cartesian3.magnitude(cartesian);
        return r * magnitude * magnitude * Math.cos(cartographic.latitude)
    }

    function getLineLength(positions) {
        lengthInMeters = 0;
        for (var i = 1, len = positions.length; i < len; i++) {
            var startPoint = positions[i - 1];
            var endPoint = positions[i];

            lengthInMeters += Cesium.Cartesian3.distance(startPoint, endPoint);
        }
        return formatDistance(lengthInMeters);
    }

    const wktManager = {
        geoByPoint: function(geographic) {
            let wkt = 'POINT (';
            wkt += geographic.lon;
            wkt += ' ';
            wkt += geographic.lat;
            wkt += ')';
            return wkt;
        },
        geoByLINE: function(geographicList) {
            let wkt = 'LINESTRING (';
            for(let i=0,len=geographicList.length;i<len;i++) {
                if(i>0) {
                    wkt += ',';
                }
                wkt += geographic[i].lon;
                wkt += ' ';
                wkt += geographic[i].lat;
            }
            wkt += ')';
            return wkt;
        },
        geoByPOLYGON: function(geographicList) {
            let wkt = 'POLYGON ((';
            for(var i=0,len=geographicList.length;i<len;i++) {
                if(i>0) {
                    wkt += ',';
                }
                wkt += geographicList[i].lon;
                wkt += ' ';
                wkt += geographicList[i].lat;
            }
            wkt += ',';
            wkt += geographicList[0].lon;
            wkt += ' ';
            wkt += geographicList[0].lat;
            wkt += '))';
            return wkt;
        }
    }

    /**
     * 결과창 표시
     */
    function showModal(){
        //모달창 실행
        let $cutFillModal =  $('#cutFillModal').dialog({
            autoOpen: false,
            width: 500,
            modal: false,
            resizable: false,
            title : '경관 분석',
            show:{
                'effect':'fade',
                'duration':500
            }
        }).dialog('open');
    };

}