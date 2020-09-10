package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import lhdt.admin.svc.cityplanning.persistence.CPReportDetailMapper;
import lhdt.admin.svc.cityplanning.persistence.CPReportDetailRepository;
import lhdt.admin.svc.cityplanning.service.CPReportDetailService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CPReportDetailServiceImpl
        extends AdminSvcServiceImpl<CPReportDetailRepository, CPReportDetailMapper, CPReportDetail, Long>
        implements CPReportDetailService {
    private final CPReportDetailRepository jpaRepo;
    private final CPReportDetailMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new CPReportDetail());
    }
}
