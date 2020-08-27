package lhdt.admin.svc.landscape.service;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.ds.common.misc.DsService;

import javax.transaction.Transactional;
import java.util.List;

public interface LandScapeDiffService extends DsService<LandScapeDiff, Long> {
    List<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup);
    LandScapeDiffScene findTopById(Long id);

}
