package lhdt.admin.svc.landscape.controller.view;

import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lhdt.ds.common.misc.DsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/ls-diff-group")
public class LandScapeDiffGroupController {
    @Autowired
    private LandScapeDiffGroupService landScapeDiffGroupService;

    @GetMapping()
    public String getNoticePage(
            @RequestParam(value = "lsGroupPage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapeDiffGroup> cpLocalInfoPage = landScapeDiffGroupService
                .findAllPgByStartPg(landscape_page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("lsGroupPage", cpLocalInfoPage);

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);
        model.addAttribute("lsGroupPageInfo", cpLocalPageNav);

        return "/landscape-diff-group/index";
    }

    @GetMapping("/{id}")
    public String getCPLocalInfo(@PathVariable(value = "id") Long id, Model model) {
        LandScapeDiffGroup board = landScapeDiffGroupService.findById(id);
        model.addAttribute("lsGroup", board);
        return "/landscape-diff-group/content";
    }

    @PostMapping()
    public String addCityInfoByParam(Model model, LandScapeDiffGroup landScapeDiffGroup) {
        this.landScapeDiffGroupService.regist(landScapeDiffGroup);
        return "redirect:/ls-diff-group";
    }

    @DeleteMapping("/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        landScapeDiffGroupService.delete(id);
        return "redirect:/ls-diff-group";
    }

    @PutMapping()
    public String modifyNotice(@Valid @ModelAttribute LandScapeDiffGroup boardForm) {
        landScapeDiffGroupService.update(boardForm.getId(), boardForm);
        return "redirect:/ls-diff-group";
    }

    @GetMapping("/edit")
    public String rgistDiffGroup(Model model) {
        return "/landscape-diff-group/edit";
    }

    @GetMapping("/edit/{id}")
    public String getEditDiffGroup(@PathVariable(value = "id") Long id, Model model) {
        LandScapeDiffGroup board = landScapeDiffGroupService.findById(id);
        model.addAttribute("lsGroup", board);
        return "/landscape-diff-group/edit";
    }

}
