package lhdt.admin.svc.cityplanning.persistence;

import lhdt.admin.svc.cityplanning.domain.CPLocalInfo;
import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import lhdt.admin.svc.common.AdminSvcMapper;
import lhdt.ds.common.config.AnalsConnMapper;

@AnalsConnMapper
public interface CPReportDetailMapper extends AdminSvcMapper<CPReportDetail, Long> {
}
