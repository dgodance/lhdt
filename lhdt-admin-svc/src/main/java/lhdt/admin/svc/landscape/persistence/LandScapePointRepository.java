package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapePoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandScapePointRepository extends JpaRepository<LandScapePoint, Long> {
}
