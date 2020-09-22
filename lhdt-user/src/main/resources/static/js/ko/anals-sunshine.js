
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


            //분석결과 모달 표시
            $('button.show-result').click(function(){

                //모달 표시
                let _showModal = function(){
                    var dataGroupDialog = $( "div.sunshine-result-report-modal" ).dialog({
                        autoOpen: false,
                        height: 550,
                        width: 1000,
                        modal: true,
                        overflow : "auto",
                        resizable: false,
                        buttons:{
                            '닫기':function(){
                                $(this).dialog('close')
                            }
                        }
                    }).dialog('open');
                };

                //데이터 생성
                let _createData = function(){
                    let labels=[], datas=[];
                    for(let i=0; i<24; i++){
                        labels.push(10>i ? '0'+i : i);
                        datas.push(i);
                    }

                    //
                    return {'labels': labels, 'datas': datas};
                };

                //차트 표시
                let _showChart = function(labels, datas){
                    let config = {
                        type: 'line',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: '일조량',
                                data: datas,
                                borderColor: 'red',
                                fill: false,
                            }]
                        },
                        options: {
                            responsive: true,
                            title: {
                                display: true,
                            }
                        },
    
                    };
    
                    //
                    new Chart($('.sunshine-result-report-modal canvas.chart'), config);
                    $('.sunshine-result-report-modal canvas.chart').css('height', 350);
                }

                //표 표시
                let _showTable = function(labels, datas){
                    let s1='<th style="width:55px;">시간</th>', s2='<td>일조량</td>';
                    for(let i=0; i<labels.length; i++){
                        s1 += '<th>'+labels[i]+'</th>';
                        s2 += '<td>'+datas[i]+'</td>';
                    }
                    $('.sunshine-result-report-modal table > thead').html('<tr>'+s1+'</tr>');
                    $('.sunshine-result-report-modal table > tbody').html('<tr>'+s2+'</tr>');
                }

                //
                _showModal();
                let json = _createData();
                _showChart(json.labels, json.datas);
                _showTable(json.labels, json.datas);
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

// DEPRECATED
window.randomScalingFactor = function() {
    return Math.round(Samples.utils.rand(-100, 100));
};
