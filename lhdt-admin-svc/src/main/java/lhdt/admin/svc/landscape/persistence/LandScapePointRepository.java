package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapePoint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * jpa와 경관점 테이블을 매핑합니다
 */
public interface LandScapePointRepository extends JpaRepository<LandScapePoint, Long> {
}
