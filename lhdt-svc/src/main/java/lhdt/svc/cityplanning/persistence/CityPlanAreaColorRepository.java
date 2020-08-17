package lhdt.svc.cityplanning.persistence;

import lhdt.svc.cityplanning.domain.CityInfo;
import lhdt.svc.cityplanning.domain.CityPlanAreaColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityPlanAreaColorRepository extends JpaRepository<CityPlanAreaColor, Long> {
    boolean existsByAreaName(String areaName);
    CityPlanAreaColor findByAreaName(String areaName);
    List<CityPlanAreaColor> findAllByAreaName(String areaName
    );
}
