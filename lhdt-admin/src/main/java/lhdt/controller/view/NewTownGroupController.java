package lhdt.controller.view;

import lhdt.domain.newtown.NewTownGroup;
import lhdt.domain.policy.Policy;
import lhdt.service.NewTownGroupService;
import lhdt.service.PolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/new-town-group")
public class NewTownGroupController {

	@Autowired
	private NewTownGroupService newTownGroupService;

	@Autowired
	private PolicyService policyService;

	/**
	 * 뉴타운 그룹 목록
	 * @param request
	 * @param newTownGroup
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	public String list(HttpServletRequest request, @ModelAttribute NewTownGroup newTownGroup, Model model) {
		List<NewTownGroup> newTownGroupList = newTownGroupService.getListNewTownGroup();

		model.addAttribute("newTownGroupList", newTownGroupList);

		return "/new-town-group/list";
	}

	/**
	 * 뉴타운 그룹 등록 페이지 이동
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/input")
	public String input(Model model) {
		Policy policy = policyService.getPolicy();

		List<NewTownGroup> newTownGroupList = newTownGroupService.getListNewTownGroup();

		NewTownGroup newTownGroup = new NewTownGroup();
		newTownGroup.setParentName(policy.getContentNewTownGroupRoot());
		newTownGroup.setParent(0);

		model.addAttribute("policy", policy);
		model.addAttribute("newTownGroup", newTownGroup);
		model.addAttribute("newTownGroupList", newTownGroupList);

		return "/new-town-group/input";
	}

	/**
	 * 뉴타운 그룹 수정 페이지 이동
	 * @param request
	 * @param newTownGroupId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/modify")
	public String modify(HttpServletRequest request, @RequestParam Integer newTownGroupId, Model model) {
		NewTownGroup newTownGroup = new NewTownGroup();
		newTownGroup.setNewTownGroupId(newTownGroupId);
		newTownGroup = newTownGroupService.getNewTownGroup(newTownGroup);
		Policy policy = policyService.getPolicy();
		List<NewTownGroup> newTownGroupList = newTownGroupService.getListNewTownGroup();

		if(newTownGroup.getParent() == 0) {
			newTownGroup.setParentName(policy.getContentNewTownGroupRoot());
		}

		model.addAttribute("policy", policy);
		model.addAttribute("newTownGroup", newTownGroup);
		model.addAttribute("newTownGroupList", newTownGroupList);

		return "/new-town-group/modify";
	}

	/**
	 * 뉴타운 그룹 삭제
	 * @param newTownGroupId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/delete")
	public String delete(@RequestParam("newTownGroupId") Integer newTownGroupId, Model model) {
		// TODO validation 체크 해야 함
		NewTownGroup newTownGroup = new NewTownGroup();
		newTownGroup.setNewTownGroupId(newTownGroupId);

		newTownGroupService.deleteNewTownGroup(newTownGroup);

		return "redirect:/new-town-group/list";
	}
}
