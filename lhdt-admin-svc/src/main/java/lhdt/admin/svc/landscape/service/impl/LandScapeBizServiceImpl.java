package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.file.persistence.FileInfoRepository;
import lhdt.admin.svc.file.service.FileInfoService;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffRepository;
import lhdt.admin.svc.landscape.service.LandScapeBizService;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LandScapeBizServiceImpl implements LandScapeBizService {
    private final LandScapeDiffService landScapeDiffService;
    private final FileInfoService fileInfoService;

    /**
     * error point
     * @param fileInfos
     * @param landScapeDiff
     * @return
     */
    @Override
    @Transactional
    public LandScapeDiff registDiffAndFileInfo(List<FileInfo> fileInfos, LandScapeDiff landScapeDiff) {
        var fileResult = this.fileInfoService.regist(fileInfos.get(0));
        landScapeDiff.setLsDiffImgInfo(fileResult);
        var result = this.landScapeDiffService.regist(landScapeDiff);
        return null;
    }
}
