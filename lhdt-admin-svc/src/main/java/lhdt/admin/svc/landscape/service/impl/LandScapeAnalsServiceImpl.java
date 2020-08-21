package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import lhdt.admin.svc.landscape.persistence.LandScapeAnalsMapper;
import lhdt.admin.svc.landscape.persistence.LandScapeAnalsRepository;
import lhdt.admin.svc.landscape.service.LandScapeAnalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class LandScapeAnalsServiceImpl
        extends AdminSvcServiceImpl<LandScapeAnalsRepository, LandScapeAnalsMapper, LandScapeAnals, Long>
        implements LandScapeAnalsService {
    private final LandScapeAnalsRepository jpaRepo;
    private final LandScapeAnalsMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new LandScapeAnals());
    }
}
