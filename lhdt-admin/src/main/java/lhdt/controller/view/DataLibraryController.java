//package lhdt.controller.view;
//
//import lhdt.domain.Key;
//import lhdt.domain.PageType;
//import lhdt.domain.common.Pagination;
//import lhdt.domain.data.DataLibraryGroup;
//import lhdt.domain.data.DataLibrary;
//import lhdt.domain.policy.Policy;
//import lhdt.domain.uploaddata.UploadData;
//import lhdt.domain.user.UserSession;
//import lhdt.service.DataLibraryGroupService;
//import lhdt.service.DataService;
//import lhdt.service.PolicyService;
//import lhdt.support.SQLInjectSupport;
//import lhdt.utils.DateUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 데이터 라이브러리
// */
//@Slf4j
//@Controller
//@RequestMapping("/data-library")
//public class DataLibraryController {
//
//	@Autowired
//	private DataLibraryGroupService dataLibraryGroupService;
//
//	@Autowired
//	private DataService dataLibraryService;
//
//	@Autowired
//	private PolicyService policyService;
//
//	/**
//	 * 데이터 라이브러리 목록
//	 * @param request
//	 * @param dataLibrary
//	 * @param pageNo
//	 * @param model
//	 * @return
//	 */
//	@GetMapping(value = "/list")
//	public String list(HttpServletRequest request, DataLibrary dataLibrary, @RequestParam(defaultValue="1") String pageNo, Model model) {
//		DataLibraryGroup dataLibraryGroup = DataLibraryGroup.builder().available(true).build();
//		List<DataLibraryGroup> dataLibraryGroupList = dataLibraryGroupService.getListDataLibraryGroup(dataLibraryGroup);
//
//		dataLibrary.setSearchWord(SQLInjectSupport.replaceSqlInection(dataLibrary.getSearchWord()));
//		dataLibrary.setOrderWord(SQLInjectSupport.replaceSqlInection(dataLibrary.getOrderWord()));
//
//		log.info("@@ dataLibrary = {}, pageNo = {}", dataLibrary, pageNo);
//
////		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
////		dataLibrary.setUserId(userSession.getUserId());
//
//		if(!StringUtils.isEmpty(dataLibrary.getStartDate())) {
//			dataLibrary.setStartDate(dataLibrary.getStartDate().substring(0, 8) + DateUtils.START_TIME);
//		}
//		if(!StringUtils.isEmpty(dataLibrary.getEndDate())) {
//			dataLibrary.setEndDate(dataLibrary.getEndDate().substring(0, 8) + DateUtils.END_TIME);
//		}
//
//		long totalCount = dataLibraryService.getDataLibraryTotalCount(dataLibrary);
//		Pagination pagination = new Pagination(request.getRequestURI(), getSearchParameters(PageType.LIST, dataLibrary),
//				totalCount, Long.parseLong(pageNo), dataLibrary.getListCounter());
//		dataLibrary.setOffset(pagination.getOffset());
//		dataLibrary.setLimit(pagination.getPageRows());
//
//		List<DataLibrary> dataLibraryList = new ArrayList<>();
//		if(totalCount > 0l) {
//			dataLibraryList = dataLibraryService.getListDataLibrary(dataLibrary);
//		}
//
//		model.addAttribute("dataLibraryGroupList", dataLibraryGroupList);
//		model.addAttribute(pagination);
//		model.addAttribute("dataLibraryList", dataLibraryList);
//		return "/data-library/list";
//	}
//
//	/**
//	 * 데이터 라이브러리 등록 화면
//	 */
//	@GetMapping(value = "/input")
//	public String input(HttpServletRequest request, Model model) {
//		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
//
//		Policy policy = policyService.getPolicy();
//		UploadData uploadData = new UploadData();
//
//		DataLibraryGroup dataLibraryGroup = new DataLibraryGroup();
//		dataLibraryGroup.setUserId(userSession.getUserId());
//		List<DataLibraryGroup> dataLibraryGroupList = dataLibraryGroupService.getListDataLibraryGroup(dataLibraryGroup);
//
//		model.addAttribute("policy", policy);
//		model.addAttribute("dataLibraryGroupList", dataLibraryGroupList);
//		model.addAttribute("uploadData", uploadData);
//
//		return "/data-library/input";
//	}
//
//	/**
//	 * 데이터 라이브러리 정보
//	 * @param dataLibrary
//	 * @param model
//	 * @return
//	 */
//	@GetMapping(value = "/detail")
//	public String detail(HttpServletRequest request, DataLibrary dataLibrary, Model model) {
//		log.info("@@@ detail-info dataLibrary = {}", dataLibrary);
//
//		String listParameters = getSearchParameters(PageType.DETAIL, dataLibrary);
//
//		dataLibrary =  dataLibraryService.getDataLibrary(dataLibrary);
//		Policy policy = policyService.getPolicy();
//
//		model.addAttribute("policy", policy);
//		model.addAttribute("listParameters", listParameters);
//		model.addAttribute("dataLibrary", dataLibrary);
//
//		return "/data-library/detail-data";
//	}
//
//	/**
//	 * 데이터 라이브러리 수정 화면
//	 * @param request
//	 * @param dataLibraryId
//	 * @param model
//	 * @return
//	 */
//	@GetMapping(value = "/modify")
//	public String modify(HttpServletRequest request, @RequestParam("dataLibraryId") Long dataId, Model model) {
//		//UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
//
//		DataLibrary dataLibrary = new DataLibrary();
//		//dataLibrary.setUserId(userSession.getUserId());
//		dataLibrary.setDataLibraryId(dataLibraryId);
//
//		dataLibrary = dataLibraryService.getDataLibrary(dataLibrary);
//
//		model.addAttribute("dataLibrary", dataLibrary);
//
//		return "/data-library/modify";
//	}
//
//	/**
//	 * 데이터 라이브러리 삭제
//	 * @param dataLibraryId
//	 * @param model
//	 * @return
//	 */
//	@GetMapping(value = "/delete")
//	public String delete(HttpServletRequest request, @RequestParam("dataLibraryId") Long dataLibraryId, Model model) {
//		//UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
//
//		// TODO validation 체크 해야 함
//		if(dataLibraryId == null) {
//			log.info("@@@ validation error dataLibraryId = {}", dataLibraryId);
//			return "redirect:/data-library/list";
//		}
//
//		DataLibrary dataLibrary = new DataLibrary();
//		dataLibrary.setDataLibraryId(dataLibraryId);
//		//dataLibrary.setUserId(userId);
//
//		dataLibraryService.deleteDataLibrary(dataLibrary);
//
//		return "redirect:/data-library/list";
//	}
//
//	/**
//     * 검색 조건
//	 * @param pageType
//     * @param dataLibrary
//     * @return
//     */
//	private String getSearchParameters(PageType pageType, DataLibrary dataLibrary) {
//		StringBuffer buffer = new StringBuffer(dataLibrary.getParameters());
//		boolean isListPage = true;
//		if(pageType == PageType.MODIFY || pageType == PageType.DETAIL) {
//			isListPage = false;
//		}
//
////		if(!isListPage) {
////			buffer.append("pageNo=" + request.getParameter("pageNo"));
////			buffer.append("&");
////			buffer.append("list_count=" + uploadData.getList_counter());
////		}
//
//		return buffer.toString();
//	}
//}
