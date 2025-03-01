var UserPolicy = function(magoInstance) {
	
	//레이블 온오프
	$("input[name='labelDisplay']").change(function(e){
		var flag = JSON.parse($(this).val().toLowerCase());
		changeLabelAPI(magoInstance, flag);
	});
	
	//오리진 표시 유무 변경
	$("input[name='originDisplay']").change(function(e){
		var flag = JSON.parse($(this).val().toLowerCase());
		changeOriginAPI(magoInstance, flag);
	});
	
	//바운딩박스 표시 유무 변경
	$("input[name='bboxDisplay']").change(function(e){
		var flag = JSON.parse($(this).val().toLowerCase());
		changeBoundingBoxAPI(magoInstance, flag);
	});
	
	//중심 반경 이슈 변경
	$("input[name='nearIssueDisplay']").change(function(e){
		var flag = JSON.parse($(this).val().toLowerCase());
		var campos = getCameraCurrentPositionAPI(magoInstance);
		if(campos.alt > 5000 && flag) {
			alert(JS_MESSAGE["policy.camera.location"]);
			$('#nearIssueDisplayN').prop('checked',true);
			return false;
		}
		
		if(flag) {
			getCenterRadiusIssue();
		} else {
			clearCenterRadiusIssue();
		}
	});
	
	//그림자 표시 유무 변경
	$("input[name='shadowDisplay']").change(function(e){
		var flag = JSON.parse($(this).val().toLowerCase());
		changeShadowAPI(magoInstance, flag);
	});
	
	$("input[name='objectMoveMode']").change(function(e){
		changeObjectMoveAPI(magoInstance, $(this).val());
	});
	
	//lod 변경
	$('#changeLodButton').click(function(e){
		var lod0 = $("#geoLod0").val();
		var lod1 = $("#geoLod1").val();
		var lod2 = $("#geoLod2").val();
		var lod3 = $("#geoLod3").val();
		var lod4 = $("#geoLod4").val();
		var lod5 = $("#geoLod5").val();
		if(isNaN(lod0) || isNaN(lod1) || isNaN(lod2)|| isNaN(lod3) || isNaN(lod4) || isNaN(lod5)) {
			alert(JS_MESSAGE["number.constraint"]);
			return;
		}
		
		changeLodAPI(magoInstance, lod0, lod1, lod2, lod3, lod4, lod5);
	});
	
	//ssao 변경
	$('#changeSsaoButton').click(function(e){
		var ssao = $('#ssaoRadius').val();
		if(isNaN(ssao)) {
			alert(JS_MESSAGE["number.constraint"]);
			return;
		} 
		
		changeSsaoRadiusAPI(magoInstance, ssao);
	});
	
	// 지도에서 선택
	$("#findStartPoint").click(function(e){
		var magoManager = MAGO3D_INSTANCE.getMagoManager();
		// TODO event 삭제 필요 
		magoManager.on(Mago3D.MagoManager.EVENT_TYPE.CLICK, function(result) {

			var longitude = result.point.geographicCoordinate.longitude;
			var latitude = result.point.geographicCoordinate.latitude;
			var altitude = getCameraCurrentPositionAPI(MAGO3D_INSTANCE).alt;

			var x = result.point.worldCoordinate.x;
			var y = result.point.worldCoordinate.y;
			var z = result.point.worldCoordinate.z;

			var pointGraphic = new Cesium.PointGraphics({
				pixelSize : 10,
				heightReference : Cesium.HeightReference.CLAMP_TO_GROUND,
				color : Cesium.Color.AQUAMARINE,
				outlineColor : Cesium.Color.WHITE,
				outlineWidth : 2
			});

			var viewer = MAGO3D_INSTANCE.getViewer();
			var addedEntity = viewer.entities.add({
				position : new Cesium.Cartesian3(x, y, z),
				point : pointGraphic
			});
			
			$("#initLatitude").val(latitude);
			$("#initLongitude").val(longitude);
			$("#initAltitude").val(altitude);
		});
	});
	

	//카메라 위치 기반 이슈 가져오기
	function getCenterRadiusIssue(){
		LHDT.issueController.getCenterRadiusIssue();
	}
	
	//이슈 클리어, layer 도입해야함
	function clearCenterRadiusIssue(){
		LHDT.issueController.clearIssue();
	}
}

var updateUserPolicyFlag = true;
function userPolicyUpdate() {
	if(updateUserPolicyFlag) {
		updateUserPolicyFlag = false;
		var url = "/user-policy/update";
		var formData = $("#userPolicy").serialize();
		$.ajax({
			url: url,
			type: "POST",
			headers: {"X-Requested-With": "XMLHttpRequest"},
			data: formData,
			dataType: "json",
			success: function(msg) {
				if(msg.statusCode <= 200) {
					alert(JS_MESSAGE["save"]);
				} else {
					alert(msg.message);
					console.log("---- " + msg.message);
				}
				updateUserPolicyFlag = true;
			},
			error: function(request, status, error) {
				alert(JS_MESSAGE["ajax.error.message"]);
				updateUserPolicyFlag = true;
			}
		});
	} else {
		alert(JS_MESSAGE["button.dobule.click"]);
	}
}