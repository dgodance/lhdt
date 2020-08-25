package lhdt.admin.svc.landscape.service;

import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.ds.common.misc.DsService;

import java.util.List;

public interface LandScapeDiffService extends DsService<LandScapeDiff, Long> {
    public List<LandScapeDiff> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup);
}
