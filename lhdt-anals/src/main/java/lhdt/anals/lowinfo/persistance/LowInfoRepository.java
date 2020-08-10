package lhdt.anals.lowinfo.persistance;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.lowinfo.domain.LowInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LowInfoRepository extends CrudRepository<LowInfo, Long> {
    boolean existsByIdAndLowInfoName(Long id, String lowInfoName);
    LowInfo findByIdAndLowInfoName(Long id, String lowInfoName);
    List<LowInfo> findAllByIdAndLowInfoName(Long id, String lowInfoName);
}
