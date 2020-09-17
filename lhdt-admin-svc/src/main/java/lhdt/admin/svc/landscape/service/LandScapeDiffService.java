package lhdt.admin.svc.landscape.service;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.cmmn.misc.CmmnService;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;

import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

public interface LandScapeDiffService extends CmmnService<LandScapeDiff, Long> {
    Page<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup,
                                                           Integer startPage, Integer contentsSize);
    List<LandScapeDiffDefault> findALlByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup);
    LandScapeDiffScene findTopById(Long id);

}
