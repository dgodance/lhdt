var AnalsCityPlanning = function(viewer, magoInstance) {
    var magoManager = magoInstance.getMagoManager();
    this._viewer = viewer;
    this._scene = viewer.scene;
    startDrawPolyLine();

    $('#cityPlanAreaTestPoint').click(function(e) {
    })

    $('#cityPlanAreaTestBtn').click(function(e) {
        $.ajax({
            url: "http://localhost:8090/anals/gis/get_cityplan_data_by_point"
        }).done(function(data) {
            const jsonData = JSON.parse(data);
            debugger;
            const geojsonInfo = jsonData[0].geojson_info;
            const geoJsonObj = JSON.parse(geojsonInfo);
            viewer.dataSources.add(geoJsonObj);
        })
    })

    function startDrawPolyLine() {
        handler = new Cesium.ScreenSpaceEventHandler(viewer.canvas);

        handler.setInputAction(function (event) {
            var earthPosition = viewer.scene.pickPosition(event.position);
            if (Cesium.defined(earthPosition)) {
                var cartographic = Cesium.Cartographic.fromCartesian(earthPosition);
                var tempPosition = Cesium.Cartesian3.fromDegrees(Cesium.Math.toDegrees(cartographic.longitude), Cesium.Math.toDegrees(cartographic.latitude));
                console.log(Cesium.Math.toDegrees(cartographic.longitude), Cesium.Math.toDegrees(cartographic.latitude))

            }
        }, Cesium.ScreenSpaceEventType.LEFT_CLICK);

        handler.setInputAction(function (event) {
            console.log('RIGHT CLICK');
        }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
    }

    //input cartesian position
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

};