package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * jpa와 경관비교그룹 테이블을 매핑합니다
 */
public interface LandScapeDiffGroupRepository extends JpaRepository<LandScapeDiffGroup, Long> {
}
