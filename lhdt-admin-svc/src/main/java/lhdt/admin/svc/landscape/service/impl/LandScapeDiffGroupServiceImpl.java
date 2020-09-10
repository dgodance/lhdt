package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffGroupMapper;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffGroupRepository;
import lhdt.admin.svc.landscape.service.LandScapeDiffGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class LandScapeDiffGroupServiceImpl
        extends AdminSvcServiceImpl<LandScapeDiffGroupRepository, LandScapeDiffGroupMapper, LandScapeDiffGroup, Long>
        implements LandScapeDiffGroupService {
    private final LandScapeDiffGroupRepository jpaRepo;
    private final LandScapeDiffGroupMapper mapper;

    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new LandScapeDiffGroup());
    }
}
