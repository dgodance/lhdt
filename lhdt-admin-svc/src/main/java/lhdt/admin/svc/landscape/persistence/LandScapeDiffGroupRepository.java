package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandScapeDiffGroupRepository extends JpaRepository<LandScapeDiffGroup, Long> {
}
