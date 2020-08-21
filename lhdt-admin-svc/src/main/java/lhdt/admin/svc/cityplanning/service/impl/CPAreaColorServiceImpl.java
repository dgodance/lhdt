package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPAreaColor;
import lhdt.admin.svc.cityplanning.persistence.CPAreaColorMapper;
import lhdt.admin.svc.cityplanning.persistence.CPAreaColorRepository;
import lhdt.admin.svc.cityplanning.service.CPAreaColorService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.lowinfo.domain.LowInfoDet;
import lhdt.admin.svc.lowinfo.persistence.LowInfoDetMapper;
import lhdt.admin.svc.lowinfo.persistence.LowInfoDetRepository;
import lhdt.admin.svc.lowinfo.service.LowInfoDetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CPAreaColorServiceImpl
        extends AdminSvcServiceImpl<CPAreaColorRepository, CPAreaColorMapper, CPAreaColor, Long>
        implements CPAreaColorService {
    private final CPAreaColorRepository jpaRepo;
    private final CPAreaColorMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new CPAreaColor());
    }
}
