package lhdt.admin.svc.cityplanning.controller.view;

import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.domain.CPLocalInfo;
import lhdt.admin.svc.cityplanning.model.CPManagedRegistParam;
import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
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
                .findAllPgByStartPg(local_page -1, CmmnPageSize.NOTICE.getContent());
        model.addAttribute("cpLocalInfoPage", cpLocalInfoPage);

        CmmnPaginatorInfo cpLocalPageNav = CmmnPaginator.getPaginatorMap(cpLocalInfoPage, CmmnPageSize.NOTICE);
        model.addAttribute("cpLocalInfoPageInfo", cpLocalPageNav);

        Page<CPDistricInfo> cpDistricInfoPage = cpDistricInfoService
                .findAllPgByStartPg(distric_page -1, CmmnPageSize.NOTICE.getContent());
        model.addAttribute("cpDistricInfoPage", cpDistricInfoPage);

        CmmnPaginatorInfo cpDistricPageNav = CmmnPaginator.getPaginatorMap(cpDistricInfoPage, CmmnPageSize.NOTICE);
        model.addAttribute("cpDistricInfoPageInfo", cpDistricPageNav);

        return "/cp/index";
    }

    @GetMapping("/local-info/{id}")
    public String getCPLocalInfo(@PathVariable(value = "id") Long id, Model model) {
        CPLocalInfo board = cpLocalInfoService.findById(id);
        model.addAttribute("cpLocalInfo", board);
        return "/cp/cp-local-content";
    }

    @GetMapping("/distric-info/{id}")
    public String getCPDistricInfo(@PathVariable(value = "id") Long id, Model model) {
        CPDistricInfo board = cpDistricInfoService.findById(id);
        model.addAttribute("cpDistricInfo", board);
        return "/cp/cp-distric-content";
    }

    @GetMapping("/regist")
    public String addCityInfo(Model model) {
        var board = cpLocalInfoService.findAll();
        model.addAttribute("cpLocalInfoList", board);
        return "/cp/cp-info-regist";
    }

    @PostMapping("/local-info")
    public String addCPLocalInfo(@Valid @ModelAttribute CPLocalInfo boardForm) {
        this.cpLocalInfoService.regist(boardForm);
        return "redirect:/cp/regist";
    }

    @PostMapping("/distric-info")
    public String addCPDistricInfo(@Valid @ModelAttribute CPManagedRegistParam cpManagedParam) {
        var cdi = new CPDistricInfo();
        cdi.setDistrictName(cpManagedParam.getDistricName());
        var cli = this.cpLocalInfoService.findById(cpManagedParam.getLocalInfoId());
        cdi.setCpLocalInfo(cli);
        this.cpDistricInfoService.regist(cdi);
        return "redirect:/cp";
    }

    @PostMapping("/local-info/{id}")
    public String deleteCPLocalInfo(@PathVariable(value = "id") Long id) {
        this.cpLocalInfoService.delete(id);
        return "redirect:/cp";
    }

    @PostMapping("/distric-info/{id}")
    public String deleteCPDistricInfo(@PathVariable(value = "id") Long id) {
        this.cpDistricInfoService.delete(id);
        return "redirect:/cp";
    }

    @PostMapping("/local-info-edit")
    public String modifyCPLocalInfo(@Valid @ModelAttribute CPManagedRegistParam boardForm) {
        var cpObj = new CPLocalInfo();
        cpObj.setLocalName(boardForm.getLocalInfoName());
        this.cpLocalInfoService.update(boardForm.getLocalInfoId(), cpObj);
        return "redirect:/cp";
    }

    @PostMapping("/distric-info-edit")
    public String modifyCPDistricInfo(@Valid @ModelAttribute CPManagedRegistParam boardForm) {
        var cpObj = new CPDistricInfo();
        cpObj.setDistrictName(boardForm.getDistricName());
        cpObj.getFileInfos().clear();
        this.cpDistricInfoService.update(boardForm.getDistricId(), cpObj);
        return "redirect:/cp";
    }

    @GetMapping("/local-info-edit/{id}")
    public String getLocalInfoEdit(@PathVariable(value = "id") Long id, Model model) {
        var board = cpLocalInfoService.findById(id);
        model.addAttribute("cpLocalInfo", board);
        return "/cp/cp-local-info-edit";
    }

    @GetMapping("/distric-info-edit/{id}")
    public String getDistricInfoEdit(@PathVariable(value = "id") Long id, Model model) {
        var board = cpDistricInfoService.findById(id);
        model.addAttribute("cpDistricInfo", board);
        return "/cp/cp-distric-info-edit";
    }
}
