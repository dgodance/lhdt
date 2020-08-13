package lhdt.svc.lowinfo.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lhdt.svc.lowinfo.domain.LowInfo;

public interface LowInfoRepository extends CrudRepository<LowInfo, Long> {
    boolean existsByLowInfoName(String lowInfoName);
    LowInfo findByLowInfoName(String lowInfoName);
    List<LowInfo> findAllByLowInfoName(String lowInfoName);
}
