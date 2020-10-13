package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffGroupMapper;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffGroupRepository;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 경관비교 서비스에 대한 세부 기능을 구현합니다
 */
@Service
@RequiredArgsConstructor
public class LandScapeDiffGroupServiceImpl
        extends AdminSvcServiceImpl<LandScapeDiffGroupRepository, LandScapeDiffGroupMapper, LandScapeDiffGroup, Long>
        implements LandScapeDiffGroupService {
    private final LandScapeDiffGroupRepository jpaRepo;
    private final LandScapeDiffGroupMapper mapper;

    /**
     * JPA와 MYBATIS정보를 객체에 주입합니다
     */
    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new LandScapeDiffGroup());
    }
}
