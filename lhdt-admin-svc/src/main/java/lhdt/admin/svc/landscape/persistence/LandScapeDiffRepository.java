package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandScapeDiffRepository extends JpaRepository<LandScapeDiff, Long> {
    List<LandScapeDiff> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup);
}
