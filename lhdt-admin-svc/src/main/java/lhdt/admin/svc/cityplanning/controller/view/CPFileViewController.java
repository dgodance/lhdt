package lhdt.admin.svc.cityplanning.controller.view;

import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import lhdt.admin.svc.cityplanning.domain.CPLocalInfo;
import lhdt.admin.svc.cityplanning.service.CPFileInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/cp-file")
public class CPFileViewController {
    @Autowired
    private CPFileInfoService cpFileInfoService;


    @GetMapping()
    public String getCPFile(
            @RequestParam(value = "filePage", defaultValue = "1") Integer file_page,
            Model model) {
        Page<CPFileInfo> cpLocalInfoPage = cpFileInfoService
                .findAllPgByStartPg(file_page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("cpLocalInfoPage", cpLocalInfoPage);

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);
        model.addAttribute("cpFileInfoPageInfo", cpLocalPageNav);

        return "/cp-file/index";
    }

    @GetMapping("/{id}")
    public String getCPFileById(@PathVariable(value = "id") Long id, Model model) {
        var board = cpFileInfoService.findById(id);
        model.addAttribute("cpFileInfo", board);
        return "/cp-file/content";
    }

    @GetMapping("/regist")
    public String addCPFileView(Model model) {
        return "/cp-file/regist";
    }

    @PostMapping("/regist")
    public String addCPFile(Model model) {
        return "/cp-file/regist";
    }

    @GetMapping("/edit/{id}")
    public String editCPFile(@PathVariable(value = "id") Long id, Model model) {
        var board = cpFileInfoService.findById(id);
        model.addAttribute("cpFileInfo", board);
        return "/cp-file/edit";
    }

    @PostMapping("/{id}")
    public String deleteCPFile(@PathVariable(value = "id") Long id) {
        this.cpFileInfoService.delete(id);
        return "redirect:/cp-file";
    }
}
