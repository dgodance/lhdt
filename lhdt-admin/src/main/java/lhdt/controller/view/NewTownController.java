package lhdt.controller.view;

import lhdt.controller.AuthorizationController;
import lhdt.domain.Key;
import lhdt.domain.PageType;
import lhdt.domain.common.Pagination;
import lhdt.domain.newtown.NewTown;
import lhdt.domain.newtown.NewTownGroup;
import lhdt.domain.policy.Policy;
import lhdt.domain.role.RoleKey;
import lhdt.domain.user.UserGroup;
import lhdt.domain.user.UserInfo;
import lhdt.domain.user.UserSession;
import lhdt.domain.user.UserStatus;
import lhdt.service.PolicyService;
import lhdt.service.NewTownGroupService;
import lhdt.service.NewTownService;
import lhdt.support.PasswordSupport;
import lhdt.support.SQLInjectSupport;
import lhdt.utils.DateUtils;
import lhdt.utils.FormatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 뉴타운
 * @author kimhj
 *
 */
@Slf4j
@Controller
@RequestMapping("/new-town")
public class NewTownController implements AuthorizationController {

	@Autowired
	private NewTownService newTownService;

	@Autowired
	private NewTownGroupService newTownGroupService;

	@Autowired
	private PolicyService policyService;

	/**
	 * 뉴타운 목록
	 * @param request
	 * @param newTown
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(HttpServletRequest request, @RequestParam(defaultValue="1") String pageNo, NewTown newTown, Model model) {
		newTown.setSearchWord(SQLInjectSupport.replaceSqlInection(newTown.getSearchWord()));
		newTown.setOrderWord(SQLInjectSupport.replaceSqlInection(newTown.getOrderWord()));
		
//		String roleCheckResult = roleValidate(request);
//    	if(roleValidate(request) != null) return roleCheckResult;

    	String today = DateUtils.getToday(FormatUtils.YEAR_MONTH_DAY);
		if(StringUtils.isEmpty(newTown.getStartDate())) {
			newTown.setStartDate(today.substring(0,4) + DateUtils.START_DAY_TIME);
		} else {
			newTown.setStartDate(newTown.getStartDate().substring(0, 8) + DateUtils.START_TIME);
			newTown.setStartDate(newTown.getStartDate().substring(0, 8) + DateUtils.START_TIME);
		}
		if(StringUtils.isEmpty(newTown.getEndDate())) {
			newTown.setEndDate(today + DateUtils.END_TIME);
		} else {
			newTown.setEndDate(newTown.getEndDate().substring(0, 8) + DateUtils.END_TIME);
		}

    	long totalCount = newTownService.getNewTownTotalCount(newTown);
    	Pagination pagination = new Pagination(request.getRequestURI(), getSearchParameters(PageType.LIST, newTown), totalCount, Long.parseLong(pageNo), newTown.getListCounter());
    	newTown.setOffset(pagination.getOffset());
    	newTown.setLimit(pagination.getPageRows());

		List<NewTown> newTownList = new ArrayList<>();
		if(totalCount > 0l) {
			newTownList = newTownService.getListNewTown(newTown);
		}

		model.addAttribute(pagination);
		model.addAttribute("newTownList", newTownList);
		return "/new-town/list";
	}

	/**
	 * 뉴타운 등록  페이지 이동
	 */
	@GetMapping(value = "/input")
	public String input(Model model) {
		Policy policy = policyService.getPolicy();
		List<NewTownGroup> newTownGroupList = newTownGroupService.getListNewTownGroup();

		model.addAttribute("policy", policy);
		model.addAttribute("newTownGroupList", newTownGroupList);
		model.addAttribute("newTown", new NewTown());
		return "/user/input";
	}

	/**
	 * 뉴타운 수정  페이지 이동
	 * @param request
	 * @param newTownId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/modify")
	public String modify(HttpServletRequest request, @RequestParam Integer newTownId, Model model) {
		String roleCheckResult = roleValidate(request);
    	if(roleValidate(request) != null) return roleCheckResult;

        Policy policy = policyService.getPolicy();
        NewTown newTown = newTownService.getNewTown(newTownId);
		List<NewTownGroup> newTownGroupList = newTownGroupService.getListNewTownGroup();

        model.addAttribute("policy", policy);
        model.addAttribute("newTown", newTown);
        model.addAttribute("newTownGroupList", newTownGroupList);

        return "/user/modify";
	}
	
	/**
	 * 검색 조건
	 * @param newTown
	 * @return
	 */
	private String getSearchParameters(PageType pageType, NewTown newTown) {
		StringBuffer buffer = new StringBuffer(newTown.getParameters());
		boolean isListPage = true;
		if(pageType == PageType.MODIFY || pageType == PageType.DETAIL) {
			isListPage = false;
		}

//		if(!isListPage) {
//			buffer.append("pageNo=" + request.getParameter("pageNo"));
//			buffer.append("&");
//			buffer.append("list_count=" + uploadData.getList_counter());
//		}

		return buffer.toString();
	}

	private String roleValidate(HttpServletRequest request) {
    	UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		int httpStatusCode = getRoleStatusCode(userSession.getUserGroupId(), RoleKey.ADMIN_EXTRUSION_MODEL_MANAGE.name());
		if(httpStatusCode > 200) {
			log.info("@@ httpStatusCode = {}", httpStatusCode);
			request.setAttribute("httpStatusCode", httpStatusCode);
			return "/error/error";
		}

		return null;
    }
}
