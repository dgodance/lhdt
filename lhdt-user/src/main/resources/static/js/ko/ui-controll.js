$(function() {
	var observerTarget = document.getElementById('contentsWrap');
	if(observerTarget) {
		var observerConfig = { attributes: true, attributeFilter:['style'],subtree: false};
		
		var observer = new MutationObserver(function(mutations) {
			var mutation = mutations[0];
			var display = mutation.target.style.display;
			var navWidth = document.getElementsByClassName('nav')[0].offsetWidth;
			var contentWidth = observerTarget.offsetWidth;
			
			var divided = document.getElementById('mapCtrlDivide').className.indexOf('on') > -1;
			var offsetWidth = (display === 'none') ? navWidth : navWidth+contentWidth;
			offsetWidth = divided ? offsetWidth / 2 : offsetWidth;
			
			var widthPrct = !divided ? '100%':'50%';
			
			var cssWidth = `calc(${widthPrct} - ${offsetWidth}px)`;
			$('#magoContainer').css('width',cssWidth);
			
			var diviedElem = document.getElementById('magoDivideContainer');
			if(diviedElem.style.display !== 'none') {
				cssWidth = `calc(${widthPrct} - ${offsetWidth+3}px)`;
				diviedElem.style.width = cssWidth;
			}
		});
		observer.observe(observerTarget, observerConfig);
	}
	
	var currentUrl = location.href;
	var defaultMenuId = "";
	
	$("ul.nav li[data-nav]:not(:empty)").not($(this)).each(function() {
        $(this).removeClass("on");
        $("#" + $(this).attr("data-nav")).hide();
    });
	
	if(currentUrl === undefined || currentUrl === null || currentUrl === "") {
		// default 활성화
		$("#dataMenu").addClass('on');
		$('#dataContent').toggle(true);
		$('#contentsWrap').toggle(true);
	} else {
		$('button#closeLeftBtn').toggle(true);
		// 다른거 활성화
		if( currentUrl.indexOf("/data/map") >= 0) {
			//var weatherEnable = false;
			if( currentUrl.indexOf("#search") >= 0) {
				$("#searchMenu").addClass('on');
				$('#searchContent').toggle(true);
			} else if( currentUrl.indexOf("#spatial") >= 0) {
				$("#spatialMenu").addClass('on');
				$('#spatialContent').toggle(true);
			} else if( currentUrl.indexOf("#extrusion") >= 0) {
				$("#extrusionMenu").addClass('on');
				$('#extrusionContent').toggle(true);
			} else if( currentUrl.indexOf("#userPolicy") >= 0) {
				$("#userPolicyMenu").addClass('on');
				$('#userPolicyContent').toggle(true);
			} else if( currentUrl.indexOf("#layer") >= 0) {
				$("#layerMenu").addClass('on');
				$('#layerContent').toggle(true);
			/*} else if( currentUrl.indexOf("#iot") >= 0) {
				$("#iotMenu").addClass('on');
				$('#iotContent').toggle(true);
				weatherEnable = true;*/
			} else {
				$("#dataMenu").addClass('on');
				$('#dataContent').toggle(true);

			}
			//showWeather(weatherEnable);
			$('#contentsWrap').toggle(true);
		} else {
			// 데이터 변환
			$("#converterMenu").addClass('on');
			//$('#contentsWrap').toggle(true);
			// 데이터 변환 탭 변경 시
			$(".tab > li").siblings().removeClass("on");
			if(location.href.indexOf("/data-group/list") > 0
					|| location.href.indexOf("/data-group/modify") > 0) { 
				$("#tabDataGroupList").addClass("on");
			} else if (location.href.indexOf("/data-group/input") > 0) {
				$("#tabDataGroupInput").addClass("on");
			} else if (location.href.indexOf("/upload-data/input") > 0) {
				$("#tabUploadDataInput").addClass("on");
			} else if (location.href.indexOf("/upload-data/list") > 0
					|| location.href.indexOf("/upload-data/modify") > 0) {
				$("#tabUploadDataList").addClass("on");
			} else if (location.href.indexOf("/converter/list") > 0) {
				$("#tabConverterList").addClass("on");
			} else if (location.href.indexOf("/data/list") > 0 
					|| location.href.indexOf("/data/modify") > 0
					|| location.href.indexOf("/data-adjust-log/modify") > 0) {
				$("#tabDataList").addClass("on");
			} else if (location.href.indexOf("/data-log/list") > 0) {
				$("#tabDataLogList").addClass("on");
			} else if (location.href.indexOf("/converter-job-file-list") > 0) {
				$("#tabConverterJobFileList").addClass("on");
			}
		}
		
	}
	
	// 상세 메뉴 닫기
	$('button#closeLeftBtn').click(function() {
		//$('ul.nav li[data-nav]').removeClass('on');
		$('#contentsWrap').hide();
		$('ul.nav li.on').removeClass('on');
		$(this).hide();
	});

	/***** NAV WRAP: 메뉴 *****/
    // 상세 메뉴 클릭 시 기본 동작
    $("ul.nav li[data-nav]:not(:empty)").click(function() {
        var active = $(this).attr('data-nav');
        var display = $(this).toggleClass('on').hasClass('on');
        
        // 변환(upload-data)이 아닌 컨텐츠 클릭시 다시 지도 페이지로 돌아감 
        if(location.href.indexOf("upload") > 0 
        	|| location.href.indexOf("converter") > 0 
        	|| location.href.indexOf("group") > 0 
        	|| location.href.indexOf("/data/list") > 0 
        	|| location.href.indexOf("/data/modify") > 0 
        	|| location.href.indexOf("/data-adjust-log") > 0
        	|| location.href.indexOf("/data-log") > 0) {
        	$(this).removeClass('on');
        	var classId = $(this).attr('class');
        	window.location="../data/map#" + classId;
        	return;
        }
        
        // 변환 클릭 이벤트시 url 변경 
        if(active === "converterContent") {
        	window.location="../upload-data/list";
        	return;
        }

        $("ul.nav li[data-nav]:not(:empty)").not($(this)).each(function() {
            $(this).removeClass('on');
            $('#' + $(this).attr('data-nav')).hide();
        });

        $('#'+ active).toggle(display);
        $('#contentsWrap').toggle(display);
        $('button#closeLeftBtn').toggle(display);

        // 데이터 검색 필터가 열려있다면 닫기
        if ($('#searchFilterContent').is(':visible')) {
			$('#searchFilterContent').hide();
		}

        /*if(active === "iotContent") {
        	showWeather(display);
		} else {
        	showWeather(false);
		}*/
    });

    
/***** Contents Wrap: 공간분석 *****/	
	// 공간분석 그룹 클릭 시	
	$('#spatialContent ul.listDrop li > p').click(function(e) {
		var parentObj = $(this).parent();
		var index = parentObj.index();
		$('#spatialContent ul.listDrop > li').eq(index).toggleClass('on');
	});
	
	// 공간분석 위치 관련 버튼 클릭 시
	$('#spatialContent button[class*="draw"]').click(function(e) {
		$(this).toggleClass('on');
		
		$('#spatialContent button[class*="draw"]').not($(this)).each(function(i,a){
			$(this).removeClass('on');
		});
	});
	
/***** Contents Wrap: 공간분석 *****/	
	// 시뮬레이션 그룹 클릭 시	
	$('#simulationContent ul.listDrop li > p').click(function() {
		var parentObj = $(this).parent();
		var index = parentObj.index();
		$('#simulationContent ul.listDrop > li').eq(index).toggleClass('on');
	});

	/*
	$('div.district').hover(function() {
		$('div.districtWrap').css('display', 'block');
	}, function(){
		$('div.districtWrap').css('display', 'none');
	});
	$('div.districtWrap').hover(function() {
		$('div.districtWrap').css('display', 'block');
	}, function(){
		$('div.districtWrap').css('display', 'none');
	});
	 */
});

function allMenuDisplay() {
	$("ul.nav li[data-nav]:not(:empty)").not($(this)).each(function() {
        $(this).removeClass('on');
        $('#' + $(this).attr('data-nav')).hide();
    });

    $('#'+ active).toggle(display);
    $('#contentsWrap').toggle(display);
}
