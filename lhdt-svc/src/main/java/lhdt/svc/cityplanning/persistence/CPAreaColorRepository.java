package lhdt.svc.cityplanning.persistence;

import lhdt.svc.cityplanning.domain.CPAreaColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPAreaColorRepository extends JpaRepository<CPAreaColor, Long> {
    boolean existsByAreaName(String areaName);
    CPAreaColor findByAreaName(String areaName);
    List<CPAreaColor> findAllByAreaName(String areaName
    );
}
