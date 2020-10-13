package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lhdt.admin.svc.landscape.persistence.LandScapePointMapper;
import lhdt.admin.svc.landscape.persistence.LandScapePointRepository;
import lhdt.admin.svc.landscape.service.LandScapePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 경관점에 대한 세부 기능을 구현합니다
 */
@Service
@RequiredArgsConstructor
public class LandScapePointServiceImpl
        extends AdminSvcServiceImpl<LandScapePointRepository, LandScapePointMapper, LandScapePoint, Long>
        implements LandScapePointService {
    private final LandScapePointRepository jpaRepo;
    private final LandScapePointMapper mapper;

    /**
     * JPA와 MYBATIS정보를 객체에 주입합니다
     */
    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new LandScapePoint());
    }
}
