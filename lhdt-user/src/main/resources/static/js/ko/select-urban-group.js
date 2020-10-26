/**
  * 도시 그룹 select 관련 처리
  * 20201021 init
  */
 let SelectUrbanGroupObj = function(){
	 
 };
 
 /**
  * 초기화
  */
 SelectUrbanGroupObj.prototype.init = function(){
	 this.setEventHandler();
	 
	 
	 this.getUrbanGroupsAsync();

	console.log(this, '<<.init');
 };
 
 /**
  * 이벤트 등록
  */
 SelectUrbanGroupObj.prototype.setEventHandler = function(){
	//change 이벤트 등록
	$('select.urban-group').unbind('click').click(function(){
		let lon = $(this).find('option:selected').data('lon');
		let lat = $(this).find('option:selected').data('lat');
		
		if(Pp.isEmpty(lon) || Pp.isEmpty(lat)){
			return;
		}
		
		//해당 위치로 이동
		Ppmap.getManager().flyTo(lon, lat, 3000, 1);
		
		
		
		//TODO 선택된 도시 그룹에 해당하는 데이터셋 목록 조회
			//select 생성
			//change 이벤트 등록
				//선택된 데이터 셋으로 모든 데이터 설정
		
		
		
		
		setTimeout(function(){
			$('select.urban-group').val('');
			
		}, 1000);
	});
 };
 
 /**
  * 도시 그룹 목록 조회
  */
 SelectUrbanGroupObj.prototype.getUrbanGroupsAsync = function(){
	 let self = this;
	 
	 
	 $.get('../api/urban-groups', function(res){					
		if(Pp.isEmpty(res) || Pp.isEmpty(res._embedded) || Pp.isEmpty(res._embedded.urbanGroups)){
			self.getUrbanGroupsCompleted([]);
			return;
		}
		
		
		self.getUrbanGroupsCompleted(res._embedded.urbanGroups);
	 });
 };
 
 
 /**
  * 도시 그룹 목록 조회되면 자동으로 호출됨
  */
 SelectUrbanGroupObj.prototype.getUrbanGroupsCompleted = function(urbanGroups){
	 //console.log(urbanGroups);
	 
	//parent0 목록만 추출
	let parent0UrbanGroups = this.getUrbanGroupsByParent(urbanGroups, 0);
	
	//children 목록 추출 by parent0
	for(let i=0; i<parent0UrbanGroups.length; i++){
		let d = parent0UrbanGroups[i];
		
		d.children = this.getUrbanGroupsByParent(urbanGroups, d.urbanGroupId);
	}
	
	//select생성
	let htmlString = this.createSelectHtml(parent0UrbanGroups);
	
	
	//화면에 표시
	$('span.select-urban-group-container').append(htmlString);
	
	//이벤트 등록
	this.setEventHandler();
	
 };
 
 
 
 /**
  * parent값으로 도시 그룹 목록 추출
  * @param {object|Array} urbanGroups
  * @param {string|number} parent
  * @returns {object|Array}
  */
 SelectUrbanGroupObj.prototype.getUrbanGroupsByParent = function(urbanGroups, parent){
	let arr=[];
	
	for(let i=0; i<urbanGroups.length; i++){
		let d = urbanGroups[i];
		
		if(parent == d.parent){
			arr.push(d);
		}
	}
	
	return arr;
};


/**
 * 도시 그룹 선택 html 문자열 생성
 * @param {object|Array} parent0List
 * @returns {string}
 */
SelectUrbanGroupObj.prototype.createSelectHtml = function(parent0List){
	var source = $("#select-urban-group-template").html();
	var template = Handlebars.compile(source);
	return template({datas:parent0List});
	
};


 
//인스턴스 생성
let selectUrbanGroupObj = new SelectUrbanGroupObj();		 
 