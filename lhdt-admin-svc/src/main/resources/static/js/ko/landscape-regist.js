$(()=> {
    const cesiumObj = cesiumInit.init();
    cesiumMouseEvt.init(cesiumObj);
    render.init();
    lsDropDownList.init();
    lsDropDownList.initDropDownVal();
    editEvt();

    const p = new asideMenuComponent('ls-point-wrap', 'ls-diff-point');
    p.setMenu();
});



const landScapeTypeSelect = {
    ele: '#landScapeTypeSelect',
    init: function() {
        $(this.ele).change(function() {
            if(this.value === 0) {
                render.renderType = RenderType.DOT
            } else {
                render.renderType = RenderType.LINE
            }
        });
    }
};

const landScapeNameInput = {
    id: '#landScapeNameInput',
    getLandScapeName: function() {
        return $(this.id).val();
    }
}

const lsDropDownList = {
    ele: '#lsDropDownMenuButton',
    subEle: '#lsDropDownMenu',
    init: function() {
        $(this.subEle+' a').click(function() {
            const thisVal = $(this).attr('value');
            lsDropDownList.setDropDownVal(thisVal);
        })
    },
    initDropDownVal() {
        $(lsDropDownList.ele).val('점');
        $(lsDropDownList.ele).text('점');
        render.renderType = RenderType.DOT;
    },
    setDropDownVal(EnumVal) {
        $(lsDropDownList.ele).val(EnumVal);
        $(lsDropDownList.ele).text(EnumVal);
        if(EnumVal === '점') {
            render.renderType = RenderType.DOT;
        } else {
            render.renderType = RenderType.LINE;
        }
    }
}

function editEvt() {
    debugger;
}

function registForm() {
    var form = $('#lsAnalsForm')[0];

    // Create an FormData object
    var data = new FormData(form);
    let renderTypeParam = undefined;
    if(render.renderType === RenderType.DOT) {
        renderTypeParam = "점";
    } else {
        renderTypeParam = "선";
    }
    if(landScapeNameInput.getLandScapeName() === ''){
        alert('경관명을 입력해주세요');
        return;
    }

    let sendParam = undefined;
    if(render.renderType === RenderType.DOT) {
        if(cesiumMouseEvt.pos.start === undefined) {
            alert('경관 점을 선택해주세요!')
            return;
        }
        sendParam = {
            landScapeAnalsName: landScapeNameInput.getLandScapeName(),
            landScapeAnalsType: renderTypeParam,
            startPosX: cesiumMouseEvt.pos.start.long,
            startPosY: cesiumMouseEvt.pos.start.lat,
            startPosZ: cesiumMouseEvt.pos.start.alt,
        }
    } else {
        if(cesiumMouseEvt.pos.start === undefined) {
            alert('경관 시작 점을 선택해주세요!')
            return;
        }
        if(cesiumMouseEvt.pos.end === undefined) {
            alert('경관 종료 점을 선택해주세요!')
            return;
        }
        sendParam = {
            landScapeAnalsName: landScapeNameInput.getLandScapeName(),
            landScapeAnalsType: renderTypeParam,
            startPosX: cesiumMouseEvt.pos.start.long,
            startPosY: cesiumMouseEvt.pos.start.lat,
            startPosZ: cesiumMouseEvt.pos.start.alt,
            endPosX: cesiumMouseEvt.pos.end.long,
            endPosY: cesiumMouseEvt.pos.end.lat,
            endPosZ: cesiumMouseEvt.pos.end.alt,
        }
    }
    if(sendParam === undefined) {
        alert('파라미터를 확인해주세요!');
        return;
    }

    $.ajax({
        type: "POST",
        url: "/adminsvc/ls-point-rest/edit",
        data: sendParam,
        success: function (data) {
            window.location.href = data;
        },
        error: function (e) {
            console.log("ERROR : ", e);
            alert("fail");
        }
    });
}

const cesiumInit = {
    ele: 'cesiumContainer',
    viewer: undefined,
    scene: undefined,
    canvas: undefined,
    mago: {
        MAGO3D_INSTANCE: undefined,
        NDTP : {
            policy : {},
            dataGroup : {},
            baseLayers : {}
        }
    },
    init: function() {
        const extent = Cesium.Rectangle.fromDegrees(117.896284, 31.499028, 139.597380, 43.311528);

        Cesium.Camera.DEFAULT_VIEW_RECTANGLE = extent;
        Cesium.Camera.DEFAULT_VIEW_FACTOR = 0.7;
        Cesium.Ion.defaultAccessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiO' +
            'iIxODQ0NTUxYi1mODg3LTQxZTEtYmU2Zi00NzQ0ODI3YjI1ZDIiLCJpZCI6MTUxODY' +
            'sInNjb3BlcyI6WyJhc2wiLCJhc3IiLCJhc3ciLCJnYyJdLCJpYXQiOjE1Njc0MDU5MDJ9.g' +
            'qA_lEtPeiKI_Tn6WbBKfcaSaiHmj0f1GmcD0VBtmPc';
        this.viewer = new Cesium.Viewer(this.ele,
            {
                terrainProvider: new Cesium.CesiumTerrainProvider({
                    url: Cesium.IonResource.fromAssetId(85669),
                }),
                timeline : true,
                animation : true,
                selectionIndicator : true,
                navigationHelpButton : false,
                infoBox : false,
                shadows: true,
                navigationInstructionsInitiallyVisible : true,
                baseLayerPicker : true
            });
        this.viewer.imageryLayers.addImageryProvider(
            new Cesium.IonImageryProvider({ assetId: 4 })
        );
        this.viewer.clock.shouldAnimate = true;
        this.scene = this.viewer.scene;
        this.canvas = this.viewer.canvas;
        return this;
    },
    magoInit: function() {
        const that = this;
        initPolicy(function(policy, baseLayers){
            that.mago.NDTP.policy = policy;
            that.mago.NDTP.baseLayers = baseLayers;
            that.mago.MAGO3D_INSTANCE = new Mago3D.Mago3d(that.ele, that.mago.NDTP.policy,
                {loadend : that.magoLoadEnd}, {});
        });

    },
    magoLoadEnd(e) {
        const that = this;
        debugger;
        var magoInstance = e;
        var geoPolicyJson = that.mago.NDTP.policy;
        var viewer = magoInstance.getViewer();
        var magoManager = magoInstance.getMagoManager();
        var f4dController = magoInstance.getF4dController();

        // TODO : 세슘 MAP 선택 UI 제거,엔진에서 처리로 변경 예정.
        if(viewer.baseLayerPicker) viewer.baseLayerPicker.destroy();
        viewer.scene.globe.depthTestAgainstTerrain = true;
        //viewer.scene.screenSpaceCameraController.minimumZoomDistance = 10;

        magoManager.on(Mago3D.MagoManager.EVENT_TYPE.MOUSEMOVE, function(result) {
            //console.info(result);
        });


    },
    initPolicy: function(callback, dataId) {
        if (!dataId) dataId = "";
        $.ajax({
            url: "/geopolicies/user?dataId=" + dataId,
            type: "GET",
            headers: {"X-Requested-With": "XMLHttpRequest"},
            dataType: "json",
            success: function (msg) {
                if (msg.statusCode <= 200) {
                    callback(msg.geoPolicy, msg.baseLayers);
                } else {
                    alert(JS_MESSAGE[msg.errorCode]);
                }
            },
            error: function (request, status, error) {
                alert(JS_MESSAGE["ajax.error.message"]);
            }
        });

    }
};

const cesiumMouseEvt = {
    viewer: undefined,
    scene: undefined,
    canvas: undefined,
    pos: {
        start: undefined,
        end: undefined,
        move: undefined
    },
    entity: {
        start: undefined,
        end: undefined,
        line: undefined
    },
    workType: undefined,
    init: function(cesiumObj) {
        cesiumMouseEvt.viewer = cesiumObj.viewer;
        cesiumMouseEvt.scene = cesiumObj.scene;
        cesiumMouseEvt.canvas = cesiumObj.canvas;
        cesiumMouseEvt.workType = LandsDirecWorkType.WAIT;
        cesiumMouseEvt.leftMouseDBClick();
        cesiumMouseEvt.mouseMove();
    },
    clearEntity: function() {
        cesiumMouseEvt.pos.start = undefined;
        cesiumMouseEvt.pos.end = undefined;
        debugger;
        const entityLine = cesiumMouseEvt.entity.line;
        const entityStartDot = cesiumMouseEvt.entity.start;
        const entityEndDot = cesiumMouseEvt.entity.end;

        if (entityLine !== undefined) {
            cesiumMouseEvt.viewer.entities.remove(entityLine);
            cesiumMouseEvt.entity.line = undefined;
        }
        if (entityStartDot !== undefined) {
            cesiumMouseEvt.viewer.entities.remove(entityStartDot);
            cesiumMouseEvt.entity.start = undefined;
        }
        if (entityEndDot !== undefined) {
            cesiumMouseEvt.viewer.entities.remove(entityEndDot);
            cesiumMouseEvt.entity.end = undefined;
        }
    },
    leftMouseDBClick: function() {
        const handler = new Cesium.ScreenSpaceEventHandler(cesiumMouseEvt.canvas);
        handler.setInputAction( (click) => {
                if ( render.renderType === RenderType.DOT ) {
                    cesiumMouseEvt.action.dot(click);
                } else {
                    cesiumMouseEvt.action.line(click);
                }
            },
            Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK
        );
    },
    mouseMove: function() {
        const handler = new Cesium.ScreenSpaceEventHandler(cesiumMouseEvt.canvas);
        handler.setInputAction( (move) => {
            console.log(move);
                cesiumMouseEvt.pos.move = cesiumMouseEvt.posByEvt(move.endPosition);
            },
            Cesium.ScreenSpaceEventType.MOUSE_MOVE
        );
    },
    action: {
        dot: function(click) {
            cesiumMouseEvt.clearEntity();
            const pos = cesiumMouseEvt.posByEvt(click.position);
            cesiumMouseEvt.pos.start = pos;
            cesiumMouseEvt.entity.start = render.dot(cesiumMouseEvt.viewer, pos);
        },
        line: function(click) {
            const pos = cesiumMouseEvt.posByEvt(click.position);
            const workType = cesiumMouseEvt.workType;
            if( workType === LandsDirecWorkType.WAIT) {
                cesiumMouseEvt.clearEntity();
                console.log(`wait`); // line drawing
                cesiumMouseEvt.pos.start = pos;
                cesiumMouseEvt.entity.start = render.dot(cesiumMouseEvt.viewer, pos);
                cesiumMouseEvt.entity.line = render.line(cesiumMouseEvt.viewer, pos);
                cesiumMouseEvt.workType = LandsDirecWorkType.RUN;
            } else if(workType === LandsDirecWorkType.RUN) {
                console.log(`run`);
                cesiumMouseEvt.workType = LandsDirecWorkType.WAIT;
                cesiumMouseEvt.entity.end = render.dot(cesiumMouseEvt.viewer, pos);
                cesiumMouseEvt.pos.end = pos;
            }
        }
    },
    posByEvt: function(e) {
        const cartesian = cesiumMouseEvt.scene.pickPosition(e);
        if (cartesian) {
            const cartographic = Cesium.Cartographic.fromCartesian(cartesian);
            const longitudeString = Cesium.Math.toDegrees(
                cartographic.longitude
            );
            const latitudeString = Cesium.Math.toDegrees(
                cartographic.latitude
            );
            const height = cartographic.height;
            return  {
                long: longitudeString,
                lat: latitudeString,
                alt: height
            };
        }
    }
};

const render = {
    renderType: undefined,
    init: function() {
        this.renderType = RenderType.LINE
    },
    dot: function(viewer, pos) {
        const result = viewer.entities.add({
            position: new Cesium.Cartesian3.fromDegrees(pos.long, pos.lat, pos.alt),
            ellipsoid: {
                radii: new Cesium.Cartesian3(10, 10, 10),
                material: Cesium.Color.RED,
            },
        });
        return result;
    },
    line: (viewer, pos) => {
        const result = viewer.entities.add({
            polyline: {
                // This callback updates positions each frame.
                positions: new Cesium.CallbackProperty(() => {
                    const resTimeMousePos = cesiumMouseEvt.pos.move;
                    let p;
                    if (cesiumMouseEvt.workType === LandsDirecWorkType.WAIT) {
                        p = Cesium.Cartesian3.fromDegreesArrayHeights(this.polyLinelastPos);
                        return p;
                    }
                    this.polyLinelastPos = [pos.long, pos.lat, pos.alt, resTimeMousePos.long,
                        resTimeMousePos.lat, resTimeMousePos.alt];
                    p = Cesium.Cartesian3.fromDegreesArrayHeights(
                        this.polyLinelastPos
                    );
                    return p;
                }, false),
                width: 10,
                clampToGround: true,
                material: new Cesium.PolylineOutlineMaterialProperty({
                    color: Cesium.Color.YELLOW,
                })
            },
        });
        return result;
    }
};
const LandsDirecWorkType = {
    WAIT : 0,
    RUN : 1,
    FINISH : 2,
    POLY : 3
};
const MouseEvtType = {
    UP : 0,
    DOWN : 1,
    MOVE : 2
};
const RenderType = {
    DOT : 0,
    LINE : 1
};
