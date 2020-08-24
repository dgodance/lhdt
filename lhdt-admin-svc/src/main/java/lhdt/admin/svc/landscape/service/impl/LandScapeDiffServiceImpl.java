package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.persistence.LandScapeAnalsMapper;
import lhdt.admin.svc.landscape.persistence.LandScapeAnalsRepository;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffMapper;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffRepository;
import lhdt.admin.svc.landscape.service.LandScapeAnalsService;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lhdt.ds.common.misc.DsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class LandScapeDiffServiceImpl
        extends AdminSvcServiceImpl<LandScapeDiffRepository, LandScapeDiffMapper, LandScapeDiff, Long>
        implements LandScapeDiffService {
    private final LandScapeDiffRepository jpaRepo;
    private final LandScapeDiffMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new LandScapeDiff());
    }
}
