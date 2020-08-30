package lhdt.controller.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import lhdt.config.PropertiesConfig;
import lhdt.domain.data.DataLibraryGroup;
import lhdt.domain.Key;
import lhdt.domain.policy.Policy;
import lhdt.domain.user.UserSession;
import lhdt.service.DataLibraryGroupService;
import lhdt.service.PolicyService;
import lhdt.support.SQLInjectSupport;

/**
 * 데이터 라이브러리 그룹
 */
@Slf4j
@Controller
@RequestMapping("/data-library-group")
public class DataLibraryGroupController {

	@Autowired
	private DataLibraryGroupService dataLibraryGroupService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private PropertiesConfig propertiesConfig;

	/**
	 * 데이터 라이브러리 그룹 목록
	 * @param request
	 * @param dataLibraryGroup
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(HttpServletRequest request, @ModelAttribute DataLibraryGroup dataLibraryGroup, Model model) {
		dataLibraryGroup.setSearchWord(SQLInjectSupport.replaceSqlInection(dataLibraryGroup.getSearchWord()));
		dataLibraryGroup.setOrderWord(SQLInjectSupport.replaceSqlInection(dataLibraryGroup.getOrderWord()));

		log.info("@@ dataLibraryGroup = {}", dataLibraryGroup);

		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		dataLibraryGroup.setUserId(userSession.getUserId());
		List<DataLibraryGroup> dataLibraryGroupList = dataLibraryGroupService.getListDataLibraryGroup(dataLibraryGroup);

		model.addAttribute("dataLibraryGroupList", dataLibraryGroupList);

		return "/data-library-group/list";
	}

	/**
	 * 데이터 라이브러리 그룹 등록 페이지 이동
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/input")
	public String input(HttpServletRequest request, Model model) {
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());

		DataLibraryGroup dataLibraryGroup = new DataLibraryGroup();
		dataLibraryGroup.setUserId(userSession.getUserId());
		List<DataLibraryGroup> dataLibraryGroupList = dataLibraryGroupService.getListDataLibraryGroup(dataLibraryGroup);

		Policy policy = policyService.getPolicy();
		dataLibraryGroup.setParentName(policy.getContentDataLibraryGroupRoot());
		dataLibraryGroup.setParent(0);
		dataLibraryGroup.setParentDepth(0);

		model.addAttribute("policy", policy);
		model.addAttribute("dataLibraryGroup", dataLibraryGroup);
		model.addAttribute("dataLibraryGroupList", dataLibraryGroupList);

		return "/data-library-group/input";
	}

	/**
	 * 데이터 라이브러리 그룹 수정 페이지 이동
	 * @param request
	 * @param dataLibraryGroupId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/modify")
	public String modify(HttpServletRequest request, @RequestParam("dataLibraryGroupId") Integer dataLibraryGroupId, Model model) {
		DataLibraryGroup dataLibraryGroup = new DataLibraryGroup();
		dataLibraryGroup.setDataLibraryGroupId(dataLibraryGroupId);
		dataLibraryGroup = dataLibraryGroupService.getDataLibraryGroup(dataLibraryGroup);

		if(StringUtils.isEmpty(dataLibraryGroup.getParentName())) {
			Policy policy = policyService.getPolicy();
			dataLibraryGroup.setParentName(policy.getContentDataLibraryGroupRoot());
		}
		dataLibraryGroup.setOldDataLibraryGroupKey(dataLibraryGroup.getDataLibraryGroupKey());

		model.addAttribute("dataLibraryGroup", dataLibraryGroup);

		return "/data-library-group/modify";
	}

	/**
	 * 데이터 라이브러리 그룹 삭제
	 * @param dataLibraryGroupId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/delete")
	public String delete(@RequestParam("dataLibraryGroupId") Integer dataLibraryGroupId, Model model) {
		// TODO validation 체크 해야 함
		if(dataLibraryGroupId == null) {
			log.info("@@@ validation error dataLibraryGroupId = {}", dataLibraryGroupId);
			return "redirect:/data-library-group/list";
		}

		DataLibraryGroup dataLibraryGroup = new DataLibraryGroup();
		dataLibraryGroup.setDataLibraryGroupId(dataLibraryGroupId);

		dataLibraryGroupService.deleteDataLibraryGroup(dataLibraryGroup);

		return "redirect:/data-library-group/list";
	}
}
