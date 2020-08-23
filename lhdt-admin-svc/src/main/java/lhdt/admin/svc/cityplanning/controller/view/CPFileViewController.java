package lhdt.admin.svc.cityplanning.controller.view;

import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import lhdt.admin.svc.cityplanning.exception.NotSupportCsvFileException;
import lhdt.admin.svc.cityplanning.model.CPFileRegistParam;
import lhdt.admin.svc.cityplanning.service.CPFileInfoService;
import lhdt.admin.svc.cityplanning.service.CPReportDetailService;
import lhdt.admin.svc.cityplanning.service.CPReportParserService;
import lhdt.admin.svc.cityplanning.service.impl.CPCsvReportParserServiceImpl;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lhdt.ds.common.misc.DsFileMaster;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cp-file")
public class CPFileViewController {
    @Autowired
    private CPFileInfoService cpFileInfoService;

    @Autowired
    private CPReportDetailService cpReportDetailService;

    @Autowired
    private CPReportParserService cpReportParserService;


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
    public String addCPFile(Model model, CPFileRegistParam cpFileRegistParam) {
        List<CPFileInfo> cpFileInfos = this.cpFileInfoService.procCPFiles(cpFileRegistParam.getFiles());
        procAddCPFile(cpFileInfos);
        return "/cp-file/regist";
    }

    @Transactional
    public void procAddCPFile(List<CPFileInfo> cpFileInfos) {
        List<CPReportDetail> cpReportDetailList = null;
        try {
            for (CPFileInfo cpFileInfo : cpFileInfos) {
                cpReportDetailList = this.cpReportParserService.procDataByCityPlanReport
                        (new CPCsvReportParserServiceImpl(), cpFileInfo.toString());
                var cpFileInfoObj = this.cpFileInfoService.regist(cpFileInfo);
                cpReportDetailList.forEach(p -> p.setCPFileInfo(cpFileInfoObj));
                this.cpReportDetailService.registAll(cpReportDetailList);
            }
        } catch (NotSupportCsvFileException e) {
            cpFileInfos.forEach(p -> new DsFileMaster(p.toString()).delete());
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
