package lhdt.svc.cityplanning.controller.view;

import lhdt.svc.cityplanning.domain.CPFileInfo;
import lhdt.svc.cityplanning.model.MultipartFileMaster;
import lhdt.svc.cityplanning.service.CPFileInfoService;
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
public class CPFileInfoController  extends SvcController {
    private final CPFileInfoService cpFileInfoService;

    @GetMapping("/cp-file-info")
    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {

        Page<CPFileInfo> localInfoPage =
                cpFileInfoService.findAllPgByStartPg(page - 1, PageSize.NOTICE.getContent());
        model.addAttribute("cpFileInfoPage", localInfoPage);

        PaginatorInfo pageNav = Paginator.getPaginatorMap(localInfoPage, PageSize.NOTICE);
        model.addAttribute("cpFileInfoPageInfo", pageNav);

        return "/cp-file-info/index";
    }

    @GetMapping("/cp-file-info/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        CPFileInfo cpFileInfo = cpFileInfoService.findOneById(id);
        model.addAttribute("cpFileInfo", cpFileInfo);
        return "cp-file-info/content";
    }

    /**
     * 추가 개발 예정
     * @param cpFileInfo
     * @return
     */
    @Deprecated
    @PostMapping("/cp-file-info")
    public String addNotice(@Valid @ModelAttribute MultipartFileMaster cpFileInfo) {
        this.cpFileInfoService.registByUk(new CPFileInfo());
        return "redirect:/cp-file-info";
    }

    @DeleteMapping("/cp-file-info/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        this.cpFileInfoService.deleteAllById(id);
        return "redirect:/cp-file-info";
    }

    @PutMapping("/cp-file-info")
    public String modifyNotice(@Valid @ModelAttribute CPFileInfo cpFileInfo) {
        // ???
        var obj = this.cpFileInfoService.findOneById(cpFileInfo.getId());
//        obj.setFileName(cpFileInfo.getLocalName());
//        obj.setFileName(cpFileInfo.getLocalName());
        this.cpFileInfoService.update(obj);
        return "redirect:/cp-file-info";
    }

    @GetMapping("/cp-file-info-edit")
    public String getEditForm() {
        return "/cp-file-info/edit";
    }

    @GetMapping("/cp-file-info-edit/{id}")
    public String getEditForm(@PathVariable(value = "id") Long id, Model model) {
        CPFileInfo cpFileInfo = cpFileInfoService.findOneById(id);
        model.addAttribute("cpFileInfo", cpFileInfo);
        return "/cp-file-info/edit";
    }
}