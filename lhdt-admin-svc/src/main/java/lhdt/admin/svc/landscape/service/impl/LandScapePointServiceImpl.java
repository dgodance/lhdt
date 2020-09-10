package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lhdt.admin.svc.landscape.persistence.LandScapePointMapper;
import lhdt.admin.svc.landscape.persistence.LandScapePointRepository;
import lhdt.admin.svc.landscape.service.LandScapePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class LandScapePointServiceImpl
        extends AdminSvcServiceImpl<LandScapePointRepository, LandScapePointMapper, LandScapePoint, Long>
        implements LandScapePointService {
    private final LandScapePointRepository jpaRepo;
    private final LandScapePointMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new LandScapePoint());
    }
}
