var AnalsVisibility = function (viewer, magoInstance) {
    var magoManager = magoInstance.getMagoManager();
    this._viewer = viewer;
    this._scene = viewer.scene;
    var handler = new Cesium.ScreenSpaceEventHandler(viewer.canvas);
    this._xyz1 = {};
    this._xyz2 = {};

    $('#visibleProcessToggle').change(function() {
        initEvent();
        if($('#visibleProcessToggle').is(':checked')) {
            toastr.info('지도상에서 두점을 선택(클릭)하시기 바랍니다.');
            // drawingMode = 'cutFillAvgAnals';
            analsVisiblity();
        } else {
            // drawingMode = "";
        }
    });


    function analsVisiblity() {
        createTwoPoint();
    }

    function initEvent() {
        //마우스 이벤트 삭제
        if(handler.getInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK) !== undefined) {
            handler.removeInputAction(Cesium.ScreenSpaceEventType.LEFT_CLICK);
            console.log('clear left click');
        } else if(handler.getInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE) !== undefined) {
            handler.removeInputAction(Cesium.ScreenSpaceEventType.MOUSE_MOVE);
            console.log('clear mouse move');
        }
        //모든 엔티티 삭제
        Ppmap.removeAllEntities();
    }

    function createTwoPoint() {
        const self = this;

        //
        self._xyz1 = {};
        self._xyz2 = {};

        //
        Ppmap.setCursor('pointer');

        //마우스 왼쪽 클릭 이벤트 등록
        handler.setInputAction( function(click) {

                //점1 세팅
                if(Pp.isEmpty(self._xyz1.lon)){
                    self._xyz1 = Ppmap.cartesian2ToLonLatAlt(click.position);
                    self._xyz1.alt = self._xyz1.alt + new lsAnalsMoveInputBox().getHeight();
                    const point1 = Pp.extend({}, self._xyz1);
                    //
                    Ppmap.createPointAndAlt('ls-anals-auto-xyz1', point1.lon, point1.lat, point1.alt);
                    //
                    return;
                }

                //점2 세팅
                if(Pp.isEmpty(self._xyz2.lon)){
                    self._xyz2 = Ppmap.cartesian2ToLonLatAlt(click.position);
                    self._xyz2.alt = self._xyz2.alt + new lsAnalsMoveInputBox().getHeight();
                    const point2 = Pp.extend({}, self._xyz2);
                    //
                    Ppmap.createPointAndAlt('ls-anals-auto-xyz2', point2.lon, point2.lat, point2.alt);
                }

                //
                if(Pp.isNotEmpty(self._xyz1.lon) && Pp.isNotEmpty(self._xyz2.lon)){

                    //
                    Ppmap.restoreCursor();

                    self._xyz1 = {};
                    //
                    // Ppui.find('.ds-create-two-points').disabled = false;

                    // self._xyz1 = {};
                    // self._xyz2 = {};
                    // //분석. 0.5초 지연
                    // setTimeout(function(){
                    //     // self.doAnals();
                    //     debugger;
                    //     console.log(1);
                    //     // 분석을 실행합니다!!
                    // }, 500);
                }

            },
            Cesium.ScreenSpaceEventType.LEFT_CLICK
        );


        //마우스 이동
        handler.setInputAction( function(e) {
                //
                let xyz = Ppmap.cartesian2ToLonLat(e.endPosition);
                //console.log(e.endPosition, xyz);

                //
                if(Pp.isEmpty(self._xyz1.lon) || Pp.isEmpty(xyz.lon)){
                    return;
                }


                //
                Ppmap.removeEntity(window['entity']);

                //
                let entity = MAGO3D_INSTANCE.getViewer().entities.add({
                    polyline: {
                        // This callback updates positions each frame.
                        positions: Cesium.Cartesian3.fromDegreesArray([self._xyz1.lon, self._xyz1.lat, xyz.lon, xyz.lat]),
                        width: 10,
                        clampToGround: true,
                        material: new Cesium.PolylineOutlineMaterialProperty({
                            color: Cesium.Color.YELLOW,
                        })
                    },
                });
                //
                window['entity'] = entity;
            },
            Cesium.ScreenSpaceEventType.MOUSE_MOVE
        );

    }
}