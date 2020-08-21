package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import lhdt.admin.svc.cityplanning.domain.CPLocalInfo;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoMapper;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoRepository;
import lhdt.admin.svc.cityplanning.persistence.CPLocalInfoMapper;
import lhdt.admin.svc.cityplanning.persistence.CPLocalInfoRepository;
import lhdt.admin.svc.cityplanning.service.CPFileInfoService;
import lhdt.admin.svc.cityplanning.service.CPLocalInfoService;
import lhdt.admin.svc.common.AdminSvcService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CPLocalInfoServiceImpl
        extends AdminSvcServiceImpl<CPLocalInfoRepository, CPLocalInfoMapper, CPLocalInfo, Long>
        implements CPLocalInfoService {
    private final CPLocalInfoRepository jpaRepo;
    private final CPLocalInfoMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new CPLocalInfo());
    }
}
