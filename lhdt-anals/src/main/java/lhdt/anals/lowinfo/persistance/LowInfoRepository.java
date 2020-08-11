package lhdt.anals.lowinfo.persistance;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.lowinfo.domain.LowInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LowInfoRepository extends CrudRepository<LowInfo, Long> {
    boolean existsByLowInfoName(String lowInfoName);
    LowInfo findByLowInfoName(String lowInfoName);
    List<LowInfo> findAllByLowInfoName(String lowInfoName);
}
