package lhdt.svc.lowinfo.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lhdt.svc.lowinfo.domain.LowInfo;
import lhdt.svc.lowinfo.domain.LowInfoDet;

public interface LowInfoDetRepository extends CrudRepository<LowInfoDet, Long> {
    boolean existsByLowInfoDetNameAndLowInfo(String lowInfoDetName, LowInfo lowInfo);
    LowInfoDet findByLowInfoDetNameAndLowInfo(String lowInfoDetName, LowInfo lowInfo);
    List<LowInfoDet> findAllByLowInfoDetNameAndLowInfo(String lowInfoDetName, LowInfo lowInfo);
}
