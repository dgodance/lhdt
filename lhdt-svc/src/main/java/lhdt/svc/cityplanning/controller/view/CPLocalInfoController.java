package lhdt.svc.cityplanning.controller.view;

import lhdt.svc.cityplanning.domain.CPLocalInfo;
import lhdt.svc.cityplanning.service.CPLocalInfoService;
import lhdt.svc.common.PageSize;
import lhdt.svc.common.Paginator;
import lhdt.svc.common.PaginatorInfo;
import lhdt.svc.common.SvcController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CPLocalInfoController extends SvcController {
    private final CPLocalInfoService cpLocalInfoService;

    @GetMapping("/cp-local-info")
    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {

        Page<CPLocalInfo> localInfoPage =
                cpLocalInfoService.findAllPgByStartPg(page -1, PageSize.NOTICE.getContent());
        model.addAttribute("cpLocalInfoPage", localInfoPage);

        PaginatorInfo pageNav = Paginator.getPaginatorMap(localInfoPage, PageSize.NOTICE);
        model.addAttribute("cpLocalInfoPageInfo", pageNav);

        return "/cp-local-info/index";
    }

    @GetMapping("/cp-local-info/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        CPLocalInfo cpLocalInfo = cpLocalInfoService.findOneById(id);
        model.addAttribute("cpLocalInfo", cpLocalInfo);
        return "cp-local-info/content";
    }

    @PostMapping("/cp-local-info")
    public String addNotice(@Valid @ModelAttribute CPLocalInfo cpLocalInfo) {
        this.cpLocalInfoService.registByUk(cpLocalInfo);
        return "redirect:/cp-local-info";
    }

    @DeleteMapping("/cp-local-info/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        this.cpLocalInfoService.deleteAllById(id);
        return "redirect:/cp-local-info";
    }

    @PutMapping("/cp-local-info")
    public String modifyNotice(@Valid @ModelAttribute CPLocalInfo boardForm) {
        // ???
        var cpLocalInfo = this.cpLocalInfoService.findOneById(boardForm.getId());
        cpLocalInfo.setLocalName(boardForm.getLocalName());
        this.cpLocalInfoService.update(cpLocalInfo);
        return "redirect:/cp-local-info";
    }

    @GetMapping("/cp-local-info-edit")
    public String getEditForm() {
        return "/cp-local-info/edit";
    }

    @GetMapping("/cp-local-info-edit/{id}")
    public String getEditForm(@PathVariable(value = "id") Long id, Model model) {
        CPLocalInfo cpLocalInfo = cpLocalInfoService.findOneById(id);
        model.addAttribute("cpLocalInfo", cpLocalInfo);
        return "/cp-local-info/edit";
    }
}
