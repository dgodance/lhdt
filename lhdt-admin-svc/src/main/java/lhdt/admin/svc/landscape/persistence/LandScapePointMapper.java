package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lhdt.ds.common.config.AnalsConnMapper;

@AnalsConnMapper
public interface LandScapePointMapper extends AdminSvcMapper<LandScapePoint, Long> {
}
