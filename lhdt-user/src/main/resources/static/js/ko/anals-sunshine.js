
const solarTermDropDown = function() {
    this._ele = "#solarTermDropDown";
    this._date = undefined;
}
solarTermDropDown.prototype.init = function() {
    this._date = moment().format('YYYY-MM-DD');
    this.change();
}

solarTermDropDown.prototype.change = function () {
    let that = this;
    $(that._ele).change(function() {
        const val = that._val = $(this).val();
        if(val === "0") {
            this._date = '';
        } else if (val === "1") {
            that._height = 1.5;
        } else {
            that._height = 15;
        }
        lsAnalsMoveInputBox.setText(that._height);
    });
}
solarTermDropDown.prototype.getHeight = function() {
    return this._height;
}


function AnalsSunshine(viewer, magoInstance) {
    const magoManager = magoInstance.getMagoManager();
    $('#solarAnalysis').click(function() {
        timeSlider.init(viewer);
    });
    $('#solarAnalysisReset').click(function() {
        timeSlider.remove();
    })

    const timeSlider = {
        ele: '',
        obj: undefined,
        init: function(viewer) {
            const that = this;
            if(!that.obj) {
                that.obj = new KotSlider('timeInput');
                that.obj.setMin(1);
                that.obj.setMax(24);
                that.obj.setDuration(200);
                let html = '';
                viewer.shadows = true;
                viewer.softShadows = true;
                for(let i=1;i<25;i++) {
                    if(i === 1 || 1 === 10) {
                        html += '<span style="margin-left:22px;">' + i + '</span>';
                    } else if(i < 10) {
                        html += '<span style="margin-left:27px;">' + i + '</span>';
                    } else {
                        html += '<span style="margin-left:19px;">' + i + '</span>';
                    }
                }

                $('#saRange .rangeWrapChild.legend').html(html);
                $('#saRange .rangeWrapChild.legend').on('click','span',function(){
                    const hourNum = parseInt($(this).index())+1;
                    that.obj.setValue(hourNum);
                    that.changeSunShineByHour(hourNum);
                });
                $('#timeInput').on('change', function() {
                    const hourNum = parseInt(this.value)+1;
                    that.changeSunShineByHour(hourNum);
                });
                $('#timeInput').on('input', function() {
                    const hourNum = parseInt(this.value)+1;
                    that.changeSunShineByHour(hourNum);
                });
            }

            let currentHour = new Date().getHours();
            currentHour  = currentHour === 0 ? 24 : currentHour;
            that.obj.setValue(currentHour);

            //레인지 보이기
            $('#saRange').show();
            MAGO3D_INSTANCE.getViewer().scene.globe.enableLighting = true;
            MAGO3D_INSTANCE.getMagoManager().sceneState.setApplySunShadows(true);
            // solarMode = true;
        },
        changeSunShineByHour: function(hourNum) {
            const dateTimeDate = changeDateTimePicker.obj.getDate();
            dateTimeDate.setHours(hourNum);
            changeDateTimePicker.setCesiumDate(dateTimeDate);
        },
        changeSunShineByDate: function() {
            const dateTimeDate = changeDateTimePicker.obj.getDate();
            dateTimeDate.setHours(this.obj.getValue());
            changeDateTimePicker.setCesiumDate(dateTimeDate);
        },
        remove: function() {
            const that = this;
            if(that.obj) {
                $('#saRange').hide();
                MAGO3D_INSTANCE.getViewer().scene.globe.enableLighting = false;
                MAGO3D_INSTANCE.getMagoManager().sceneState.setApplySunShadows(false);
            }
        }
    }

    const changeDateTimePicker = {
        ele: '#sunshineDatePicker',
        obj: undefined,
        init: function() {
            this.obj = new tui.DatePicker('#sunshineDatePicker', {
                date: new Date(),
                input: {
                    element: '#sunshine-datepicker-input',
                    format: 'yyyy-MM-dd'
                }
            });
            this.obj.on('change', function() {
                timeSlider.changeSunShineByDate();
            });
        },
        getDate: function(data) {

        },
        setCesiumDate: function(date) {
            const jd = Cesium.JulianDate.fromDate(date);
            magoInstance.getViewer().clock.currentTime = jd;
            magoManager.sceneState.sunSystem.setDate(date);
        }
    }

    $(document).ready(function() {
        changeDateTimePicker.init();
    });
}