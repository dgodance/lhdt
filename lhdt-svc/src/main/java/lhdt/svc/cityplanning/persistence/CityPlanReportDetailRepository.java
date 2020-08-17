package lhdt.svc.cityplanning.persistence;

import java.util.List;

import lhdt.svc.cityplanning.domain.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.cityplanning.domain.CityPlanReportDetail;

public interface CityPlanReportDetailRepository extends JpaRepository<CityPlanReportDetail, Long> {
    boolean existsByCityInfoAndDrawingIdAndHouseholdId(CityInfo cityInfo, String drawingId, String houseHolder);
    CityPlanReportDetail findByCityInfoAndDrawingIdAndHouseholdId(CityInfo cityInfo, String drawingId, String houseHolder);
    List<CityPlanReportDetail> findAllByCityInfoAndDrawingIdAndHouseholdId(CityInfo cityInfo, String drawingId, String houseHolder);
}
