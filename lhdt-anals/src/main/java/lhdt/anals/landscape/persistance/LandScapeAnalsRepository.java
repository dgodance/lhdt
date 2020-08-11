package lhdt.anals.landscape.persistance;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.landscape.domain.LandScapeAnals;
import lhdt.anals.landscape.types.LandScapeAnalsType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LandScapeAnalsRepository extends CrudRepository<LandScapeAnals, Long> {
    boolean existsByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);
    LandScapeAnals findByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);
    List<LandScapeAnals> findAllByLandScapeAnalsNameAndLandScapeAnalsType(String lsn, LandScapeAnalsType lsat);
}
