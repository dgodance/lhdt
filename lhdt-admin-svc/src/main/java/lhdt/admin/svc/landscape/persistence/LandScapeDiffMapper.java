package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.ds.common.config.AnalsConnMapper;

@AnalsConnMapper
public interface LandScapeDiffMapper extends AdminSvcMapper<LandScapeAnals, Long> {
}
