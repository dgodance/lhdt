package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPAreaColor;
import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.persistence.CPAreaColorMapper;
import lhdt.admin.svc.cityplanning.persistence.CPAreaColorRepository;
import lhdt.admin.svc.cityplanning.persistence.CPDistricInfoMapper;
import lhdt.admin.svc.cityplanning.persistence.CPDistricInfoRepository;
import lhdt.admin.svc.cityplanning.service.CPAreaColorService;
import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.common.AdminSvcService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CPDistricInfoServiceImpl
        extends AdminSvcServiceImpl<CPDistricInfoRepository, CPDistricInfoMapper, CPDistricInfo, Long>
        implements CPDistricInfoService {
    private final CPDistricInfoRepository jpaRepo;
    private final CPDistricInfoMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new CPDistricInfo());
    }
}
