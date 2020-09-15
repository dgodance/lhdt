function AnalsLandScapeView(viewer, magoInstance) {
    const LSViewAnalsWidget = function() {
        this._ele = '#lsViewAnalsWidget'
    }

    LSViewAnalsWidget.prototype.genHTML = function() {
        return $(this._ele).html();
    }

    LSViewAnalsWidget.prototype.defaultRender = function () {
        const templateHtml = Handlebars.compile(this.genHTML());
        $('#lsViewContent').append(templateHtml());
    }

    const LSViewAnals = function() {
        this._xyzList = [{}];
    }

    /**
     * 초기
     */
    LSViewAnals.prototype.init = function(){
        this.setEventHandler();

        //
        console.log('LsAnalsAutoObj', '<<.init');
    };

    /**
     * 이벤트 등록
     */
    LsAnalsAutoObj.prototype.setEventHandler = function(){
        let _this = this;
        console.log(Ppui.find('#landscapeViewAnalsBtn'));

        //분석 버튼 클릭
        // Ppui.click('#landscapeViewAnalsBtn', function(){
        //     _this.doAnals();
        // });

        //여러점 선택 버튼 클릭
        Ppui.click('.ds-create-many-points', function(){
            let el = this;

            //
            Ppmap.resetRotate(function(){
                //
                toastr.info('지도상에서 여러 경관점을 클릭하시기 바랍니다.');
                //
                el.disabled = true;

                //
                _this.createTwoPoints();
            });
        });
    };



    const lsViewAnalsWidget = new LSViewAnalsWidget();
    lsViewAnalsWidget.defaultRender();
}
