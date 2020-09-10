package lhdt.admin.svc.lowinfo.controller.view;

import lhdt.admin.svc.common.PaginatorInfo;
import lhdt.admin.svc.lowinfo.domain.LowInfo;
import lhdt.admin.svc.lowinfo.domain.LowInfoDet;
import lhdt.admin.svc.lowinfo.service.LowInfoDetService;
import lhdt.admin.svc.lowinfo.service.LowInfoService;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/low-info-det")
public class LowInfoDetViewController {
    @Autowired
    private LowInfoDetService lowInfoDetService;
    @Autowired
    private LowInfoService lowInfoService;

    @GetMapping()
    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {
        Page<LowInfoDet> noticePage = lowInfoDetService.findAllPgByStartPg(page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("lowInfoDetPage", noticePage);

        DSPaginatorInfo pageNav = DSPaginator.getPaginatorMap(noticePage, DSPageSize.NOTICE);
        model.addAttribute("lowInfoDetPageInfo", pageNav);

        return "/low-info-det/index";
    }

    @GetMapping("/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        LowInfoDet board = lowInfoDetService.findById(id);
        model.addAttribute("lowInfo", board);
        return "/low-info-det/content";
    }

    @PostMapping()
    public String addNotice(@Valid @ModelAttribute LowInfoDet boardForm) {
        this.lowInfoDetService.regist(boardForm);
        return "redirect:/low-info-det";
    }

    @DeleteMapping("/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        this.lowInfoDetService.delete(id);
        return "redirect:/low-info-det";
    }

    @PutMapping()
    public String modifyNotice(@Valid @ModelAttribute LowInfoDet boardForm) {
        // ???
        this.lowInfoDetService.update(boardForm.getId(), boardForm);
        return "redirect:/low-info-det";
    }

    @GetMapping("/edit")
    public String getEditForm(Model model) {
        List<LowInfo> lowInfoList = lowInfoService.findAll();
        model.addAttribute("lowInfoList", lowInfoList);
        return "/low-info-det/edit";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable(value = "id") Long id, Model model) {
        LowInfoDet board = lowInfoDetService.findById(id);
        model.addAttribute("lowInfoDet", board);
        return "/low-info-det/edit";
    }
}
