package lhdt.admin.svc.landscape.persistence;

import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffDefault;
import lhdt.admin.svc.landscape.domain.LandScapeDiffDTO.LandScapeDiffScene;
import lhdt.admin.svc.landscape.domain.LandScapeDiffGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandScapeDiffRepository extends JpaRepository<LandScapeDiff, Long> {
    List<LandScapeDiffDefault> findAllByLandScapeDiffGroup(LandScapeDiffGroup landScapeDiffGroup);
    LandScapeDiffScene findTopById(Long id);
}
