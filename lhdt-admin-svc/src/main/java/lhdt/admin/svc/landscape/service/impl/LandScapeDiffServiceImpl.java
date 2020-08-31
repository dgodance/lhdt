package lhdt.admin.svc.landscape.service.impl;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffMapper;
import lhdt.admin.svc.landscape.persistence.LandScapeDiffRepository;
import lhdt.admin.svc.landscape.service.LandScapeDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    public Page<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup,
                                                                  Integer startPage, Integer contentsSize) {
        PageRequest pageRequest =
                PageRequest.of(startPage,
                        contentsSize, Sort.Direction.DESC, "id");
        return this.jpaRepo.findAllByLandScapeDiffGroup(landScapeDiffGroup, pageRequest);
    }

    @Override
    public List<LandScapeDiffDefault> findALlByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup) {
        return this.jpaRepo.findAllByLandScapeDiffGroup(landScapeDiffGroup);
    }

    @Override
    public LandScapeDiffScene findTopById(Long id) {
        return this.jpaRepo.findTopById(id);
    }
}
