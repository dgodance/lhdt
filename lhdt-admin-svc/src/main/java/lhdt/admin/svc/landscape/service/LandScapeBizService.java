package lhdt.admin.svc.landscape.service;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.model.LandScapeDiffParam;

import java.util.List;

public interface LandScapeBizService {
    LandScapeDiff registDiffAndFileInfo(List<FileInfo> fileInfos, LandScapeDiffParam landScapeDiff);
}
