package lhdt.admin.svc.lowinfo.controller.view;

import lhdt.admin.svc.lhdt.domain.UserInfo;
import lhdt.admin.svc.lowinfo.domain.LowInfo;
import lhdt.admin.svc.lowinfo.service.LowInfoService;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lhdt.ds.common.misc.DsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/low-info")
public class LowInfoViewController {
    @Autowired
    private LowInfoService lowInfoService;

    @GetMapping()
    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {
        Page<LowInfo> noticePage = lowInfoService.findAllPgByStartPg(page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("lowInfoPage", noticePage);

        DSPaginatorInfo pageNav = DSPaginator.getPaginatorMap(noticePage, DSPageSize.NOTICE);
        model.addAttribute("lowInfoPageInfo", pageNav);

        return "/low-info/index";
    }

    @GetMapping("/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        LowInfo board = lowInfoService.findById(id);
        model.addAttribute("lowInfo", board);
        return "/low-info/content";
    }

    @PostMapping()
    public String addNotice(@Valid @ModelAttribute LowInfo boardForm) {
        this.lowInfoService.regist(boardForm);
        return "redirect:/low-info";
    }

    @DeleteMapping("/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        this.lowInfoService.delete(id);
        return "redirect:/low-info";
    }

    @PutMapping()
    public String modifyNotice(@Valid @ModelAttribute LowInfo boardForm) {
        // ???
        this.lowInfoService.update(boardForm.getId(), boardForm);
        return "redirect:/low-info";
    }

    @GetMapping("/edit")
    public String getEditForm() {
        return "/low-info/edit";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable(value = "id") Long id, Model model) {
        LowInfo board = lowInfoService.findById(id);
        model.addAttribute("lowInfo", board);
        return "/low-info/edit";
    }
}
