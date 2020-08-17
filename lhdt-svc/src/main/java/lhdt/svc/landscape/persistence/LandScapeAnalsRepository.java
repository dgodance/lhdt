package lhdt.svc.landscape.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.landscape.domain.LandScapeAnals;
import lhdt.svc.landscape.types.LandScapeAnalsType;

public interface LandScapeAnalsRepository extends JpaRepository<LandScapeAnals, Long> {
    boolean existsByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);
    LandScapeAnals findByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);
    List<LandScapeAnals> findAllByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);
}
