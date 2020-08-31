/**
 * 업무 공통 js
 * @author	gravity
 * @since	20200824	init
 */
 let DsObj = function(){
 	
 };
 
 
 /**
  * 페이징 관련 처리
  * 	1. 페이징 계산
  * 	2. 계산 결과로 화면에 페이징 관련 html 표시
  * 	3. 페이지 번호 클릭시 콜백함수 호출
  * @param {number} totalItems 전체 아이탬 갯수
  * @param {number} currentPage 현재 페이지 번호
  * @param {Node} html를 표시할 엘리먼트
  * @param {Function} 페이지 번호 클릭시 호출할 콜백함수
  * @author gravity
  * @since	20200824	init
  */
 DsObj.prototype.pagination = function(totalItems, currentPage, $el, callbackFn){
 	let paging = Pp.paginate(totalItems, currentPage, 10, 5);
 	
 	let s = '';
	s += '<ul class="pagination">';
	
	//
	s += '	<li class="ico first" data-page-no="'+paging.startPage+'">처음</li>';
	
	//
	for(let i=0; i<paging.pages.length; i++){
		let pageNo = paging.pages[i];
		
		//
		if(currentPage == pageNo){		
			s += '	<li class="on"	data-page-no="'+pageNo+'">'+pageNo+'</li>';
		}else{
			s += '	<li class=""	data-page-no="'+pageNo+'">'+pageNo+'</li>';
		}
	}
	
	//
	s += '	<li class="ico end" data-page-no="'+paging.totalPages+'">마지막</li>';
	
	s += '</ul>';
 	
 	//화면에 표시
 	$el.append(s);
 	
 	//페이지 클릭 이벤트
 	$('ul.pagination > li').click(function(){
 		let pageNo = $(this).data('page-no');
 		callbackFn(pageNo);
 	});
 };
 

 
 //
 let dsObj = new DsObj();