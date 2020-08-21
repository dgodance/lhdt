package lhdt.admin.svc.cityplanning.service.impl;

import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import lhdt.admin.svc.cityplanning.persistence.CPDistricInfoMapper;
import lhdt.admin.svc.cityplanning.persistence.CPDistricInfoRepository;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoRepository;
import lhdt.admin.svc.cityplanning.persistence.CPFileInfoMapper;
import lhdt.admin.svc.cityplanning.service.CPDistricInfoService;
import lhdt.admin.svc.cityplanning.service.CPFileInfoService;
import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CPFileInfoServiceImpl
        extends AdminSvcServiceImpl<CPFileInfoRepository, CPFileInfoMapper, CPFileInfo, Long>
        implements CPFileInfoService {
    private final CPFileInfoRepository jpaRepo;
    private final CPFileInfoMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new CPFileInfo());
    }
}
