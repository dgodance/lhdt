package lhdt.admin.svc.cityplanning.controller.view;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import lhdt.admin.svc.cityplanning.exception.NotSupportCsvFileException;
import lhdt.admin.svc.cityplanning.model.CPFileRegistParam;
import lhdt.admin.svc.file.service.FileInfoService;
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
    private FileInfoService fileInfoService;

    @Autowired
    private CPReportDetailService cpReportDetailService;

    @Autowired
    private CPReportParserService cpReportParserService;


    @GetMapping()
    public String getCPFile(
            @RequestParam(value = "filePage", defaultValue = "1") Integer file_page,
            Model model) {
        Page<FileInfo> cpLocalInfoPage = fileInfoService
                .findAllPgByStartPg(file_page -1, DSPageSize.NOTICE.getContent());
        model.addAttribute("cpLocalInfoPage", cpLocalInfoPage);

        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);
        model.addAttribute("cpFileInfoPageInfo", cpLocalPageNav);

        return "/cp-file/index";
    }

    @GetMapping("/{id}")
    public String getCPFileById(@PathVariable(value = "id") Long id, Model model) {
        var board = fileInfoService.findById(id);
        model.addAttribute("cpFileInfo", board);
        return "/cp-file/content";
    }

    @GetMapping("/regist")
    public String addCPFileView(Model model) {
        return "/cp-file/regist";
    }

    @PostMapping("/regist")
    public String addCPFile(Model model, CPFileRegistParam cpFileRegistParam) {
        List<FileInfo> fileInfos = this.fileInfoService.procCPFiles(cpFileRegistParam.getFiles());
        procAddCPFile(fileInfos);
        return "/cp-file/regist";
    }

    @Transactional
    public void procAddCPFile(List<FileInfo> fileInfos) {
        List<CPReportDetail> cpReportDetailList = null;
        try {
            for (FileInfo fileInfo : fileInfos) {
                cpReportDetailList = this.cpReportParserService.procDataByCityPlanReport
                        (new CPCsvReportParserServiceImpl(), fileInfo.toString());
                var cpFileInfoObj = this.fileInfoService.regist(fileInfo);
                cpReportDetailList.forEach(p -> p.setCpfileInfo(cpFileInfoObj));
                this.cpReportDetailService.registAll(cpReportDetailList);
            }
        } catch (NotSupportCsvFileException e) {
            fileInfos.forEach(p -> new DsFileMaster(p.toString()).delete());
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/edit/{id}")
    public String editCPFile(@PathVariable(value = "id") Long id, Model model) {
        var board = fileInfoService.findById(id);
        model.addAttribute("cpFileInfo", board);
        return "/cp-file/edit";
    }

    @PostMapping("/{id}")
    public String deleteCPFile(@PathVariable(value = "id") Long id) {
        this.fileInfoService.delete(id);
        return "redirect:/cp-file";
    }
}
