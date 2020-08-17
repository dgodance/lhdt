package lhdt.anals.cityplanning.persistance;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.hello.domain.SubType0;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityPlanReportDetailRepository extends CrudRepository<CityPlanReportDetail, Long> {
    boolean existsByIdAndCityPlanId(Long id, Long cityPlanId);
    CityPlanReportDetail findByIdAndCityPlanId(Long id, Long cityPlanId);
    List<CityPlanReportDetail> findAllByIdAndCityPlanId(Long id, Long cityPlanId);
}
