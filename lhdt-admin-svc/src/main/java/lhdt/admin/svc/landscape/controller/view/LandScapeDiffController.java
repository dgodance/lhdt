package lhdt.admin.svc.landscape.controller.view;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.file.service.FileInfoService;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lhdt.cmmn.misc.CmmnPageSize;
import lhdt.cmmn.misc.CmmnPaginator;
import lhdt.cmmn.misc.CmmnPaginatorInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/ls-diff")
public class LandScapeDiffController {
    @Autowired
    private LandScapeDiffService landScapeDiffService;

    @GetMapping()
    public String getNoticePage(
            @RequestParam(value = "lsDiffPage", defaultValue = "1") Integer landscape_page,
            Model model) {
        Page<LandScapeDiff> cpLocalInfoPage = landScapeDiffService
                .findAllPgByStartPg(landscape_page -1, CmmnPageSize.NOTICE.getContent());
        model.addAttribute("lsDiffPage", cpLocalInfoPage);

        CmmnPaginatorInfo cpLocalPageNav = CmmnPaginator.getPaginatorMap(cpLocalInfoPage, CmmnPageSize.NOTICE);
        model.addAttribute("lsDiffPageInfo", cpLocalPageNav);

        return "landscape-diff/index";
    }
    @GetMapping(
            value = "/img/{id}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable String id) throws IOException{
        var lsDiff = landScapeDiffService.findById(Long.valueOf(id));
        String fileName = lsDiff.getLsDiffImgInfo().getFileName();
        String filePath = lsDiff.getLsDiffImgInfo().getFilePath();
        String fileExt = lsDiff.getLsDiffImgInfo().getFileExtention();
        String fullPath = filePath + "/" + fileName + "." + fileExt;
        FileInputStream fis = new FileInputStream(fullPath);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }
}
