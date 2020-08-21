package lhdt.admin.svc.cityplanning.persistence;

import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.ds.common.config.AnalsConnMapper;

@AnalsConnMapper
public interface CPDistricInfoMapper extends AdminSvcMapper<CPDistricInfo, Long> {
}
