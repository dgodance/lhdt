package lhdt.controller.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import lhdt.domain.cache.CacheManager;
import lhdt.domain.converter.ConverterJob;
import lhdt.domain.Key;
import lhdt.domain.PageType;
import lhdt.domain.Pagination;
import lhdt.domain.user.UserSession;
import lhdt.service.ConverterService;
import lhdt.support.RoleSupport;
import lhdt.support.SQLInjectSupport;
import lhdt.utils.DateUtils;

/**
 * Data Converter
 * @author jeongdae
 *
 */
@Slf4j
@Controller
@RequestMapping("/converter")
public class ConverterController {
	
	@Autowired
	private ConverterService converterService;

	/**
	 * converter job 목록
	 * @param request
	 * @param converterJob
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(HttpServletRequest request, ConverterJob converterJob, @RequestParam(defaultValue="1") String pageNo, Model model) {
		converterJob.setSearchWord(SQLInjectSupport.replaceSqlInection(converterJob.getSearchWord()));
		converterJob.setOrderWord(SQLInjectSupport.replaceSqlInection(converterJob.getOrderWord()));
		
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		converterJob.setUserId(userSession.getUserId());		
		log.info("@@ converterJob = {}", converterJob);
		
		if(!StringUtils.isEmpty(converterJob.getStartDate())) {
			converterJob.setStartDate(converterJob.getStartDate().substring(0, 8) + DateUtils.START_TIME);
		}
		if(!StringUtils.isEmpty(converterJob.getEndDate())) {
			converterJob.setEndDate(converterJob.getEndDate().substring(0, 8) + DateUtils.END_TIME);
		}
		
		long totalCount = converterService.getConverterJobTotalCount(converterJob);
		
		Pagination pagination = new Pagination(	request.getRequestURI(), 
												getSearchParameters(PageType.LIST, converterJob), 
												totalCount, 
												Long.parseLong(pageNo),
												converterJob.getListCounter());
		log.info("@@ pagination = {}", pagination);
		
		converterJob.setOffset(pagination.getOffset());
		converterJob.setLimit(pagination.getPageRows());
		List<ConverterJob> converterJobList = new ArrayList<>();
		if(totalCount > 0L) {
			converterJobList = converterService.getListConverterJob(converterJob);
		}
		
		model.addAttribute(pagination);
		model.addAttribute("converterJobList", converterJobList);
		
		return "/converter/list";
	}
	
//	/**
//	 * converter job 파일 목록
//	 * @param request
//	 * @param membership_id
//	 * @param pageNo
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value = "list-converter-job-file.do")
//	public String listConverterJobFile(HttpServletRequest request, ConverterJobFile converterJobFile, @RequestParam(defaultValue="1") String pageNo, Model model) {
//		
//		UserSession userSession = (UserSession)request.getSession().getAttribute(UserSession.KEY);
//		converterJobFile.setUser_id(userSession.getUser_id());		
//		log.info("@@ converterJobFile = {}", converterJobFile);
//		
//		if(StringUtil.isNotEmpty(converterJobFile.getStart_date())) {
//			converterJobFile.setStart_date(converterJobFile.getStart_date().substring(0, 8) + DateUtil.START_TIME);
//		}
//		if(StringUtil.isNotEmpty(converterJobFile.getEnd_date())) {
//			converterJobFile.setEnd_date(converterJobFile.getEnd_date().substring(0, 8) + DateUtil.END_TIME);
//		}
//		long totalCount = converterService.getListConverterJobFileTotalCount(converterJobFile);
//		
//		Pagination pagination = new Pagination(request.getRequestURI(), getSearchParametersConverterJobFile(converterJobFile), totalCount, Long.parseLong(pageNo));
//		log.info("@@ pagination = {}", pagination);
//		
//		converterJobFile.setOffset(pagination.getOffset());
//		converterJobFile.setLimit(pagination.getPageRows());
//		List<ConverterJobFile> converterJobFileList = new ArrayList<>();
//		if(totalCount > 0l) {
//			converterJobFileList = converterService.getListConverterJobFile(converterJobFile);
//		}
//		
//		model.addAttribute(pagination);
//		model.addAttribute("converterJobFileList", converterJobFileList);
//		return "/converter/list-converter-job-file";
//	}

	/**
	 * 검색 조건
	 * @param pageType
	 * @param converterJob
	 * @return
	 */
	private String getSearchParameters(PageType pageType, ConverterJob converterJob) {
		StringBuilder builder = new StringBuilder(converterJob.getParameters());
		boolean isListPage = true;
		if(pageType == PageType.MODIFY || pageType == PageType.DETAIL) {
			isListPage = false;
		}
		
//		if(!isListPage) {
//			builder.append("pageNo=" + request.getParameter("pageNo"));
//			builder.append("&");
//			builder.append("list_count=" + uploadData.getList_counter());
//		}
		
		return builder.toString();
	}
	
	private String roleValidator(HttpServletRequest request, Integer userGroupId, String roleName) {
		List<String> userGroupRoleKeyList = CacheManager.getUserGroupRoleKeyList(userGroupId);
        if(!RoleSupport.isUserGroupRoleValid(userGroupRoleKeyList, roleName)) {
			log.info("---- Role 이 존재하지 않습니다. 확인 하세요. ");
        	request.setAttribute("httpStatusCode", 403);
			return "/error/error";
		}
		return null;
	}
}