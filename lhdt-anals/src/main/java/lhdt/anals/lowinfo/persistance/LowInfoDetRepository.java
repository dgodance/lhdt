package lhdt.anals.lowinfo.persistance;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.lowinfo.domain.LowInfo;
import lhdt.anals.lowinfo.domain.LowInfoDet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LowInfoDetRepository extends CrudRepository<LowInfoDet, Long> {
    boolean existsByLowInfoDetNameAndLowInfo(String lowInfoDetName, LowInfo lowInfo);
    LowInfoDet findByLowInfoDetNameAndLowInfo(String lowInfoDetName, LowInfo lowInfo);
    List<LowInfoDet> findAllByLowInfoDetNameAndLowInfo(String lowInfoDetName, LowInfo lowInfo);
}
