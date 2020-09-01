
const lsFreeAnalsWidget = function() {
    this._ele = '#lsFreeAnalsWidget'
}

lsFreeAnalsWidget.prototype.genHTML = function() {
    return $(this._ele).html();
}

lsFreeAnalsWidget.prototype.defaultRender = function () {
    $('#lsAnalsContent').empty()
    const templateHtml = Handlebars.compile(this.genHTML());
    $('#lsAnalsContent').append(templateHtml());
}


const lsSavedAnalsWidget = function() {
    this._ele = '#lsSavedAnalsWidget'
}

lsSavedAnalsWidget.prototype.genHTML = function() {
    return $(this._ele).html();
}

lsSavedAnalsWidget.prototype.defaultRenderByData = function (data) {
    $('#lsAnalsContent').empty()
    const templateHtml = Handlebars.compile(this.genHTML());
    $('#lsAnalsContent').append(templateHtml(data));
}

lsSavedAnalsWidget.prototype.reqeustDataBylsAnalsPg = function(lsAnalsPg) {
    const that = this;
    let param = '';
    if(lsAnalsPg !== undefined) {
        param += '?lsDiffPage='+lsAnalsPg;
    }
    $.ajax({
        url: 'http://localhost:9091/adminsvc/ls-point-rest' + param,
        method: 'GET'
    }).done(function(data) {
        that.defaultRenderByData(data);
    })
}

function paginSavedAnalsList(paginNum) {
    const p = new lsSavedAnalsWidget();
    p.reqeustDataBylsAnalsPg(paginNum)
}

const lsDrawLingComponent = function() {
    this._ele = '#lsDrawLineChk';
}

lsDrawLingComponent.prototype.evenctInit = function() {

}

lsDrawLingComponent.prototype.isChecked = function() {
    return $(this._ele).is(":checked");
}

lsDrawLingComponent.prototype.checked = function() {
    $(this._ele).prop('checked', true);
}

lsDrawLingComponent.prototype.unChecked = function() {
    $(this._ele).prop('checked', false);
}

lsDrawLingComponent.prototype.drawLine = function() {

}

const lsAnalsBtn = function() {
    this._ele = '#landscapeAnalsBtn'
}

lsAnalsBtn.prototype.init = function() {
    this.eventHandler();
}

lsAnalsBtn.prototype.eventHandler = function() {
    $(this._ele).click(function() {
        debugger;
        const startPos = cesiumMouseEvt.pos.start;
        const endPos = cesiumMouseEvt.pos.end;

        const pos1 = {
            lon: startPos.long,
            lat: startPos.lat,
            alt: startPos.alt
        }

        const pos2 = {
            lon: endPos.long,
            lat: endPos.lat,
            alt: endPos.alt
        }

        const p = new SkylineObj();
        p.execCalcViewPoint(pos1, pos2);
        p.autoCaptureAllMenual();


    })
}


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

$(function() {
    const viewer = ppmap.viewer;
    const scene = ppmap.viewer.scene;
    const canvas = ppmap.viewer.scene.canvas;
    cesiumMouseEvt.init({
        viewer: viewer,
        scene: scene,
        canvas: canvas,
    });
    render.init();

    const p = new lsAnalsBtn();
    p.init();
})