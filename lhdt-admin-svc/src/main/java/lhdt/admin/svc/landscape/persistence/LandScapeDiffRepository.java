package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * jpa와 경관비교 테이블을 매핑합니다
 */
public interface LandScapeDiffRepository extends JpaRepository<LandScapeDiff, Long> {
    /**
     * 경관비교그룹을 통해 모든 자료를 찾습니다
     * @param landScapeDiffGroup
     * @param pageable
     * @return
     */
    Page<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup,
                                                           Pageable pageable);

    /**
     * 경관비교그룹을 통해 모든 자료를 찾습니다
     * @param landScapeDiffGroup
     * @return
     */
    List<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup);

    /**
     * 아이디를 통하여 1건의 데이터를 가져옵니다
     * @param id
     * @return
     */
    LandScapeDiffScene findTopById(Long id);
}
