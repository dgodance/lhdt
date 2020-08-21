package lhdt.admin.svc.cityplanning.controller.view;

import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.domain.CPLocalInfo;
import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
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

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/cp")
public class CpAdminViewController {
    @Autowired
    private CPLocalInfoService cpLocalInfoService;
    @Autowired
    private CPDistricInfoService cpDistricInfoService;

    @GetMapping()
    public String getNoticePage(
            @RequestParam(value = "localPage", defaultValue = "1") Integer local_page,
            @RequestParam(value = "districPage", defaultValue = "1") Integer distric_page,
            Model model) {
        Page<CPLocalInfo> cpLocalInfoPage = cpLocalInfoService
                .findAllPgByStartPg(local_page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("cpLocalInfoPage", cpLocalInfoPage);

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);
        model.addAttribute("cpLocalInfoPageInfo", cpLocalPageNav);

        Page<CPDistricInfo> cpDistricInfoPage = cpDistricInfoService
                .findAllPgByStartPg(distric_page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("cpDistricInfoPage", cpDistricInfoPage);

        DSPaginatorInfo cpDistricPageNav = DSPaginator.getPaginatorMap(cpDistricInfoPage, DSPageSize.NOTICE);
        model.addAttribute("cpDistricInfoPageInfo", cpDistricPageNav);

        return "/cityplanning/index";
    }

    @GetMapping("/local-info/{id}")
    public String getCPLocalInfo(@PathVariable(value = "id") Long id, Model model) {
        CPLocalInfo board = cpLocalInfoService.findById(id);
        model.addAttribute("cpLocalInfo", board);
        return "/cityplanning/cp-local-content";
    }

    @GetMapping("/distric-info/{id}")
    public String getCPDistricInfo(@PathVariable(value = "id") Long id, Model model) {
        CPDistricInfo board = cpDistricInfoService.findById(id);
        model.addAttribute("cpDistricInfo", board);
        return "/cityplanning/cp-distric-content";
    }

    @GetMapping("/regist")
    public String addCityInfo(Model model) {
        var board = cpLocalInfoService.findAll();
        model.addAttribute("cpLocalInfoList", board);
        return "/cityplanning/cp-info-regist";
    }

    @PostMapping("/local-info")
    public String addCPLocalInfo(@Valid @ModelAttribute CPLocalInfo boardForm) {
        this.cpLocalInfoService.regist(boardForm);
        return "redirect:/cp/regist";
    }

    @PostMapping("/distric-info")
    public String addCPDistricInfo(@Valid @ModelAttribute CPDistricInfo boardForm) {
        this.cpDistricInfoService.regist(boardForm);
        return "redirect:/cp";
    }

    @DeleteMapping("/local-info/{id}")
    public String deleteCPLocalInfo(@PathVariable(value = "id") Long id) {
        this.cpLocalInfoService.delete(id);
        return "redirect:/cp";
    }

    @DeleteMapping("/distric-info/{id}")
    public String deleteCPDistricInfo(@PathVariable(value = "id") Long id) {
        this.cpDistricInfoService.delete(id);
        return "redirect:/cp";
    }

    @PutMapping("/local-info-edit")
    public String modifyCPLocalInfo(@Valid @ModelAttribute CPLocalInfo boardForm) {
        this.cpLocalInfoService.update(boardForm.getId(), boardForm);
        return "redirect:/cp";
    }

    @PutMapping("/distric-info-edit")
    public String modifyCPDistricInfo(@Valid @ModelAttribute CPDistricInfo boardForm) {
        this.cpDistricInfoService.update(boardForm.getId(), boardForm);
        return "redirect:/cp";
    }

    @GetMapping("/local-info-edit/{id}")
    public String getLocalInfoEdit(@PathVariable(value = "id") Long id, Model model) {
        var board = cpLocalInfoService.findById(id);
        model.addAttribute("cpLocalInfo", board);
        return "/cityplanning/cp-local-info-edit";
    }

    @GetMapping("/distric-info-edit/{id}")
    public String getDistricInfoEdit(@PathVariable(value = "id") Long id, Model model) {
        var board = cpDistricInfoService.findById(id);
        model.addAttribute("cpDistricInfo", board);
        return "/cityplanning/cp-distric-info-edit";
    }
}
