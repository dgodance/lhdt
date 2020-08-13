package lhdt.svc.landscape.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lhdt.svc.landscape.domain.LandScapeDiff;

public interface LandScapeDiffRepository extends CrudRepository<LandScapeDiff, Long> {
    boolean existsByLandScapeDiffName(String lsdn);
    LandScapeDiff findByLandScapeDiffName(String lsdn);
    List<LandScapeDiff> findAllByLandScapeDiffName(String lsdn);
}
