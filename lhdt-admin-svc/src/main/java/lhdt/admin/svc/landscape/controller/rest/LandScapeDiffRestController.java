package lhdt.admin.svc.landscape.controller.rest;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ls-diff")
public class LandScapeDiffRestController {
    @Autowired
    private LandScapeDiffGroupService landScapeDiffGroupService;
    @Autowired
    private LandScapeDiffService landScapeDiffService;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private LandScapeBizService landScapeBizService;

    @GetMapping("/fileInfo/{id}")
    private List<FileInfo> fileInfo(@PathVariable(value = "id") Long id) {
        var aa = new FileInfo();
        aa.setFileName("asdgasdgadg" + id);
        aa.setFilePath("365236");
        aa.setOriginFileName("365236");
        aa.setFileExtention("dfhdfh");
        this.fileInfoService.regist(aa);
        return this.fileInfoService.findAll();
    };


    @GetMapping("/group")
    private List<LandScapeDiffGroup> getLSDiffGroup() {
        return this.landScapeDiffGroupService.findAll();
    };

    @GetMapping("/{id}")
    private List<LandScapeDiffDefault> getLSDiff(@PathVariable(value = "id") Long id) {
        var landScapeDiffGroup = this.landScapeDiffGroupService.findById(id);
        return this.landScapeDiffService.findAllByLandScapeDiffGroup(landScapeDiffGroup);
    };

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
        return this.landScapeDiffService.findAllByLandScapeDiffGroup(landScapeDiffGroup);
    };

    @PostMapping()
    private LandScapeDiff addLSDiff(LandScapeDiffParam landScapeDiff) throws IOException {
        var id = landScapeDiff.getLandScapeDiffGroupId();
        var name = landScapeDiff.getLandscapeName();
        var cameraStat = landScapeDiff.getCaptureCameraState();

        MultipartFile[] multipartFileList = new MultipartFile[]{
            landScapeDiff.getImage()
        };
        LandScapeDiff result = null;
        List<FileInfo> fileInfos = null;
        try {
            fileInfos = fileInfoService.procCPFiles(multipartFileList);
            var obj = new LandScapeDiff();
            var group = this.landScapeDiffGroupService.findById(id);
            obj.setLandScapeDiffGroup(group);
            obj.setLsDiffName(name);
            obj.setCaptureCameraState(cameraStat);
            result = landScapeBizService.registDiffAndFileInfo(fileInfos, obj);

        } catch (Exception e) {
            fileInfos.forEach(p -> p.delete());
            throw new RuntimeException(e);
        }
        return result;
    }

}
