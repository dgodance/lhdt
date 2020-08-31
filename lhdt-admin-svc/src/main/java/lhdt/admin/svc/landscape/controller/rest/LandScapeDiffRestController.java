package lhdt.admin.svc.landscape.controller.rest;

import lhdt.admin.svc.common.model.PageParam;
import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.file.service.FileInfoService;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.model.LandScapeDiffParam;
import lhdt.admin.svc.landscape.service.LandScapeBizService;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lhdt.ds.common.misc.DSPageSize;
import lhdt.ds.common.misc.DSPaginator;
import lhdt.ds.common.misc.DSPaginatorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ls-diff-rest")
public class LandScapeDiffRestController {
    @Autowired
    private LandScapeDiffGroupService landScapeDiffGroupService;
    @Autowired
    private LandScapeDiffService landScapeDiffService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private LandScapeBizService landScapeBizService;

    @GetMapping("/group")
    private List<LandScapeDiffGroup> getLSDiffGroup() {
        return this.landScapeDiffGroupService.findAll();
    };

    /**
     * 그룹 Id를 받아와 처리한 후 페이징 처리가 가능한 경관 비교 세부 데이터를 사용자에게 전달합니다.
     * @param id 그룹Id
     * @param nowPageNum
     * @return
     */
    @GetMapping("/{id}")
    public PageParam<LandScapeDiffDefault> getNoticePage(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "lsDiffPage", defaultValue = "1") Integer nowPageNum) {
        var landScapeDiffGroup = this.landScapeDiffGroupService.findById(id);
        Page<LandScapeDiffDefault> cpLocalInfoPage = landScapeDiffService
                .findAllByLandScapeDiffGroup(landScapeDiffGroup, nowPageNum -1,
                        DSPageSize.NOTICE.getContent());
        DSPaginatorInfo cpLocalPageNav = DSPaginator.getPaginatorMap(cpLocalInfoPage, DSPageSize.NOTICE);

        var sendParam = new PageParam<LandScapeDiffDefault>();
        sendParam.setPage(cpLocalInfoPage.getContent());
        sendParam.setPagenationInfo(cpLocalPageNav);
        sendParam.setSize(cpLocalInfoPage.getSize());
        sendParam.setNowPageNum(nowPageNum -1);;
        return sendParam;
    }

    @GetMapping("/scene/{id}")
    private LandScapeDiffScene getLSDiffSceneById(@PathVariable(value = "id") Long id) {
        var landScapeDiff = this.landScapeDiffService.findTopById(id);
        return landScapeDiff;
    };

    @DeleteMapping("/scene/{id}")
    private String deleteLSDiffSceneById(@PathVariable(value = "id") Long id) {
        this.landScapeDiffService.delete(id);
        return "1";
    };

    @GetMapping("/info/{id}")
    private List<LandScapeDiffDefault> getLSDiffInfoById(@PathVariable(value = "id") Long id) {
        var landScapeDiffGroup = this.landScapeDiffGroupService.findById(id);
        return this.landScapeDiffService.findALlByLandScapeDiffGroup(landScapeDiffGroup);
    };

    @PostMapping()
    private LandScapeDiff addLSDiff(LandScapeDiffParam landScapeDiff) throws IOException {
        MultipartFile[] multipartFileList = new MultipartFile[]{
            landScapeDiff.getImage()
        };
        LandScapeDiff result = null;
        List<FileInfo> fileInfos = null;
        try {
            fileInfos = fileInfoService.procCPFiles(multipartFileList);
            result = landScapeBizService.registDiffAndFileInfo(fileInfos, landScapeDiff);

        } catch (Exception e) {
            fileInfos.forEach(p -> p.delete());
            throw new RuntimeException(e);
        }
        return result;
    }

}
