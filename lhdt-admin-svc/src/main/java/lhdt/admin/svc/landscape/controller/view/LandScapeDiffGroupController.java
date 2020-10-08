package lhdt.admin.svc.landscape.controller.view;

import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.cmmn.misc.CmmnPageSize;
import lhdt.cmmn.misc.CmmnPaginator;
import lhdt.cmmn.misc.CmmnPaginatorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 경관비교 그룹 정보를 통해
 */
@Slf4j
@Controller
@RequestMapping("/ls-diff-group")
public class LandScapeDiffGroupController {
    @Autowired
    private LandScapeDiffGroupService landScapeDiffGroupService;

    /**
     * 경관비교 그룹 페이지를 가시화 합니다
     * @param landscape_page
     * @param model
     * @return
     */
    @GetMapping()
    public String viewLsDiffGroup(
            @RequestParam(value = "lsGroupPage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapeDiffGroup> cpLocalInfoPage = landScapeDiffGroupService
                .findAllPgByStartPg(landscape_page -1, CmmnPageSize.NOTICE.getContent());
        model.addAttribute("lsGroupPage", cpLocalInfoPage);

        CmmnPaginatorInfo cpLocalPageNav = CmmnPaginator.getPaginatorMap(cpLocalInfoPage, CmmnPageSize.NOTICE);
        model.addAttribute("lsGroupPageInfo", cpLocalPageNav);

        return "landscape-diff-group/index";
    }

    /**
     * Id를 통해 내용 페이지를 가시화 합니다
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String getCPLocalInfo(@PathVariable(value = "id") Long id, Model model) {
        LandScapeDiffGroup board = landScapeDiffGroupService.findById(id);
        model.addAttribute("lsGroup", board);
        return "/landscape-diff-group/content";
    }

    /**
     * 경관비교 정보를 저장합니다
     * @param model
     * @param landScapeDiffGroup
     * @return
     */
    @PostMapping()
    public String addLsDiffGroup(Model model, LandScapeDiffGroup landScapeDiffGroup) {
        this.landScapeDiffGroupService.regist(landScapeDiffGroup);
        return "redirect:/ls-diff-group";
    }

    /**
     * 경관비교 정보를 삭제합니다
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public String deleteLsDiffGroup(@PathVariable(value = "id") Long id) {
        landScapeDiffGroupService.delete(id);
        return "redirect:/ls-diff-group";
    }

    /**
     * 경관비교 정보를 수정합니다
     * @param boardForm
     * @return
     */
    @PostMapping("/edit/{id}")
    public String editLsDiffGroup(@Valid @ModelAttribute LandScapeDiffGroup boardForm) {
        landScapeDiffGroupService.update(boardForm.getId(), boardForm);
        return "redirect:/ls-diff-group";
    }

    /**
     * 경관비교 그룹 수정 페이지로 이동합니다
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String viewLsDiffGroup(Model model) {
        return "/landscape-diff-group/edit";
    }

    /**
     * id를 통해 LsDiffGroup 수정 페이지로 이동합니다
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String getEditDiffGroup(@PathVariable(value = "id") Long id, Model model) {
        LandScapeDiffGroup board = landScapeDiffGroupService.findById(id);
        model.addAttribute("lsGroup", board);
        return "/landscape-diff-group/edit";
    }

}
