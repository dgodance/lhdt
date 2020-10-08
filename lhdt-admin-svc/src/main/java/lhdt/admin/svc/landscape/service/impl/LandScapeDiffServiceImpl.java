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

/**
 * 경관비교 서비스에 대한 세부 기능을 구현합니다
 */
@Service
@RequiredArgsConstructor
public class LandScapeDiffServiceImpl
        extends AdminSvcServiceImpl<LandScapeDiffRepository, LandScapeDiffMapper, LandScapeDiff, Long>
        implements LandScapeDiffService {
    private final LandScapeDiffRepository jpaRepo;
    private final LandScapeDiffMapper mapper;

    /**
     * JPA와 MYBATIS정보를 객체에 주입합니다
     */
    @PostConstruct
    private void init() {
        super.set(jpaRepo, mapper, new LandScapeDiff());
    }

    /**
     * 경관비교그룹과 페이지 정보를 통해 모든데이터를 찾습니다
     * @param landScapeDiffGroup
     * @param startPage
     * @param contentsSize
     * @return
     */
    @Override
    public Page<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup,
                                                                  Integer startPage, Integer contentsSize) {
        PageRequest pageRequest =
                PageRequest.of(startPage,
                        contentsSize, Sort.Direction.DESC, "id");
        return this.jpaRepo.findAllByLandScapeDiffGroup(landScapeDiffGroup, pageRequest);
    }

    /**
     * 경관 비교그룹정보를 통해 모든 데이터를 찾습니다
     * @param landScapeDiffGroup
     * @return
     */
    @Override
    public List<LandScapeDiffDefault> findALlByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup) {
        return this.jpaRepo.findAllByLandScapeDiffGroup(landScapeDiffGroup);
    }

    /**
     * 아이디를 통하여 하나의 데이터를 찾습니다
     * @param id
     * @return
     */
    @Override
    public LandScapeDiffScene findTopById(Long id) {
        return this.jpaRepo.findTopById(id);
    }
}
