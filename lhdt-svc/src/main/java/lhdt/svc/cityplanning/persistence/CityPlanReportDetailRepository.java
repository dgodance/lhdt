package lhdt.svc.cityplanning.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.cityplanning.domain.CityPlanReportDetail;

public interface CityPlanReportDetailRepository extends JpaRepository<CityPlanReportDetail, Long> {
    boolean existsByIdAndCityPlanId(Long id, Long cityPlanId);
    CityPlanReportDetail findByIdAndCityPlanId(Long id, Long cityPlanId);
    List<CityPlanReportDetail> findAllByIdAndCityPlanId(Long id, Long cityPlanId);
}
