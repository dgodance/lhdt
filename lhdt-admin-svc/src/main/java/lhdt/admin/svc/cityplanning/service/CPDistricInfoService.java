package lhdt.admin.svc.cityplanning.service;

import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.common.AdminSvcService;
import lhdt.ds.common.misc.DsService;

import java.util.List;

public interface CPDistricInfoService extends AdminSvcService<CPDistricInfo, Long> {
    public List<CPDistricInfo> findAllByCpLocalInfo(Long cpLocalInfoId);
}
