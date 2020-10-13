package lhdt.admin.svc.landscape.service;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.model.LandScapeDiffParam;

import java.util.List;

/**
 * 경관 서비스에 대한 비즈니스로직을 관리하는 클래스
 */
public interface LandScapeBizService {
    /**
     * 경관비교 정보 등록 인터페이스
     * @param fileInfos
     * @param landScapeDiff
     * @return
     */
    LandScapeDiff registDiffAndFileInfo(List<FileInfo> fileInfos, LandScapeDiffParam landScapeDiff);
}
