package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lhdt.cmmn.config.AnalsConnMapper;

@AnalsConnMapper
public interface LandScapeDiffMapper extends AdminSvcMapper<LandScapePoint, Long> {
}
