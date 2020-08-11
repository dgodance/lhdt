package lhdt.anals.landscape.persistance;

import lhdt.anals.landscape.domain.LandScapeDiff;
import lhdt.anals.landscape.domain.LandScapeDiffDet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LandScapeDiffRepository extends CrudRepository<LandScapeDiff, Long> {
    boolean existsByLandScapeDiffName(String lsdn);
    LandScapeDiff findByLandScapeDiffName(String lsdn);
    List<LandScapeDiff> findAllByLandScapeDiffName(String lsdn);
}
