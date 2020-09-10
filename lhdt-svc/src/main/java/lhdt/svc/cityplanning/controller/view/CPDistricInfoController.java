package lhdt.svc.cityplanning.controller.view;

import lhdt.svc.cityplanning.domain.CPDistricInfo;
import lhdt.svc.cityplanning.service.CPDistricInfoService;
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
public class CPDistricInfoController extends SvcController {
    private final CPDistricInfoService cpDistricInfoService;

    @GetMapping("/cp-distric-info")
    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {

        Page<CPDistricInfo> districPage =
                cpDistricInfoService.findAllPgByStartPg(page -1, PageSize.NOTICE.getContent());
        model.addAttribute("cpDistricInfoPage", districPage);

        PaginatorInfo pageNav = Paginator.getPaginatorMap(districPage, PageSize.NOTICE);
        model.addAttribute("cpDistricInfoPageInfo", pageNav);

        return "/cp-distric-info/index";
    }

    @GetMapping("/cp-distric-info/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        CPDistricInfo cpDistricInfo = cpDistricInfoService.findOneById(id);
        model.addAttribute("cpDistricInfo", cpDistricInfo);
        return "cp-distric-info/content";
    }

    @PostMapping("/cp-distric-info")
    public String addNotice(@Valid @ModelAttribute CPDistricInfo cpLocalInfo) {
        this.cpDistricInfoService.registByUk(cpLocalInfo);
        return "redirect:/cp-distric-info";
    }

    @DeleteMapping("/cp-distric-info/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        this.cpDistricInfoService.deleteAllById(id);
        return "redirect:/cp-distric-info";
    }

    @PutMapping("/cp-distric-info")
    public String modifyNotice(@Valid @ModelAttribute CPDistricInfo boardForm) {
        // ???
        var cpDistricInfo = this.cpDistricInfoService.findOneById(boardForm.getId());
        cpDistricInfo.setDistrictName(boardForm.getDistrictName());
        this.cpDistricInfoService.update(cpDistricInfo);
        return "redirect:/cp-distric-info";
    }

    @GetMapping("/cp-distric-info-edit")
    public String getEditForm() {
        return "/cp-distric-info/edit";
    }

    @GetMapping("/cp-distric-info-edit/{id}")
    public String getEditForm(@PathVariable(value = "id") Long id, Model model) {
        CPDistricInfo cpDistricInfo = cpDistricInfoService.findOneById(id);
        model.addAttribute("cpDistricInfo", cpDistricInfo);
        return "/cp-distric-info/edit";
    }
}