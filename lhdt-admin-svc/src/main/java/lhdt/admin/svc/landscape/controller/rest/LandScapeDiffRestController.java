package lhdt.admin.svc.landscape.controller.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lhdt.admin.svc.common.model.PageParam;
import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.file.service.FileInfoService;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.admin.svc.landscape.model.LandScapeDiffParam;
import lhdt.admin.svc.landscape.service.LandScapeBizService;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lhdt.cmmn.misc.CmmnController;
import lhdt.cmmn.misc.CmmnPageSize;
import lhdt.cmmn.misc.CmmnPaginator;
import lhdt.cmmn.misc.CmmnPaginatorInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ls-diff-rest")
public class LandScapeDiffRestController extends CmmnController {
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
                        CmmnPageSize.NOTICE.getContent());
        CmmnPaginatorInfo cpLocalPageNav = CmmnPaginator.getPaginatorMap(cpLocalInfoPage, CmmnPageSize.NOTICE);

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
    
    
    /**
     * 저장된 경관 이미지 조회. 이미지를 base64 문자열로 리턴
     * @param id 경관 아이디
     * @return
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<Map<String,Object>> getImage(@PathVariable(value="id") Long id) {
    	LandScapeDiff domain = landScapeDiffService.findById(id);
//    	log.debug("{}", domain.getLsDiffImgInfo());
    	
    	
    	//
    	Map<String,Object> map = new HashMap<>();
    	map.put("base64", super.fileToBase64String(domain.getLsDiffImgInfo().toString()));
    	
    	//
    	return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }

}
