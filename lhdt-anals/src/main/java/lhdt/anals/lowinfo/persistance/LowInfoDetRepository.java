package lhdt.anals.lowinfo.persistance;

import lhdt.anals.cityplanning.domain.CityPlanReportDetail;
import lhdt.anals.lowinfo.domain.LowInfo;
import lhdt.anals.lowinfo.domain.LowInfoDet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LowInfoDetRepository extends CrudRepository<LowInfoDet, Long> {
    boolean existsByIdAndLowInfoDetNameAndLowInfo(Long id, String lowInfoDetName, LowInfo lowInfo);
    LowInfoDet findByIdAndLowInfoDetNameAndLowInfo(Long id, String lowInfoDetName, LowInfo lowInfo);
    List<LowInfoDet> findAllByIdAndLowInfoDetNameAndLowInfo(Long id, String lowInfoDetName, LowInfo lowInfo);
}
