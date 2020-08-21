package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapeAnals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandScapeAnalsRepository extends JpaRepository<LandScapeAnals, Long> {
}
