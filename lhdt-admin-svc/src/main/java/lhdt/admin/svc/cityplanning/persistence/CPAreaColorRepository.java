package lhdt.admin.svc.cityplanning.persistence;

import lhdt.admin.svc.cityplanning.domain.CPAreaColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPAreaColorRepository extends JpaRepository<CPAreaColor, Long> {
    CPAreaColor findByAreaName(String areaName);
}
