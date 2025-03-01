package lhdt.controller.view;

import lhdt.domain.Key;
import lhdt.domain.PageType;
import lhdt.domain.common.Pagination;
import lhdt.domain.data.DataGroup;
import lhdt.domain.data.DataInfo;
import lhdt.domain.data.DataInfoLog;
import lhdt.domain.user.UserSession;
import lhdt.service.DataGroupService;
import lhdt.service.DataLogService;
import lhdt.service.DataService;
import lhdt.support.SQLInjectSupport;
import lhdt.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Data
 * @author jeongdae
 *
 */
@Slf4j
@Controller
@RequestMapping("/data-log")
public class DataLogController {
	
	@Autowired
	private DataService dataService;
	@Autowired
	private DataGroupService dataGroupService;
	@Autowired
	private DataLogService dataLogService;
	
	/**
	 * 사용자 데이터 수정 화면
	 * @param request
	 * @param dataId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/modify")
	public String modify(HttpServletRequest request, @RequestParam("dataId") Long dataId, Model model) {
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		
		DataInfo dataInfo = new DataInfo();
		//dataInfo.setUserId(userSession.getUserId());
		dataInfo.setDataId(dataId);
		
		dataInfo = dataService.getData(dataInfo);
		
		model.addAttribute("dataInfo", dataInfo);
		
		return "/data-log/modify";
	}

	/**
	 * 데이터 목록
	 * @param locale
	 * @param request
	 * @param dataInfoLog
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(Locale locale, HttpServletRequest request, DataInfoLog dataInfoLog, @RequestParam(defaultValue="1") String pageNo, Model model) {
		dataInfoLog.setSearchWord(SQLInjectSupport.replaceSqlInection(dataInfoLog.getSearchWord()));
		dataInfoLog.setOrderWord(SQLInjectSupport.replaceSqlInection(dataInfoLog.getOrderWord()));
		
		log.info("@@ dataInfoLog = {}", dataInfoLog);
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		
		DataGroup dataGroup = new DataGroup();
		dataGroup.setUserId(userSession.getUserId());
		dataInfoLog.setUserId(userSession.getUserId());
		List<DataGroup> dataGroupList = dataGroupService.getListDataGroup(dataGroup);
		
		if(!StringUtils.isEmpty(dataInfoLog.getStartDate())) {
			dataInfoLog.setStartDate(dataInfoLog.getStartDate().substring(0, 8) + DateUtils.START_TIME);
		}
		if(!StringUtils.isEmpty(dataInfoLog.getEndDate())) {
			dataInfoLog.setEndDate(dataInfoLog.getEndDate().substring(0, 8) + DateUtils.END_TIME);
		}

		long totalCount = dataLogService.getDataInfoLogTotalCount(dataInfoLog);
		Pagination pagination = new Pagination(	request.getRequestURI(),
												getSearchParameters(PageType.LIST, dataInfoLog), 
												totalCount, 
												Long.parseLong(pageNo), 
												dataInfoLog.getListCounter());
		log.info("@@ pagination = {}", pagination);
		
		dataInfoLog.setOffset(pagination.getOffset());
		dataInfoLog.setLimit(pagination.getPageRows());
		List<DataInfoLog> dataInfoLogList = new ArrayList<>();
		if(totalCount > 0L) {
			dataInfoLogList = dataLogService.getListDataInfoLog(dataInfoLog);
		}
		
		model.addAttribute(pagination);
		model.addAttribute("dataGroupList", dataGroupList);
		model.addAttribute("dataInfoLogList", dataInfoLogList);
		
		return "/data-log/list";
	}
	
	/**
	 * 검색 조건
	 * @param pageType
	 * @param dataInfoLog
	 * @return
	 */
	private String getSearchParameters(PageType pageType, DataInfoLog dataInfoLog) {
		return dataInfoLog.getParameters();
	}
}