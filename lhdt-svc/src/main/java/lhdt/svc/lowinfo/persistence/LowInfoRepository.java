package lhdt.svc.lowinfo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.lowinfo.domain.LowInfo;

public interface LowInfoRepository extends JpaRepository<LowInfo, Long> {
    boolean existsByLowInfoName(String lowInfoName);
    LowInfo findByLowInfoName(String lowInfoName);
    List<LowInfo> findAllByLowInfoName(String lowInfoName);
}
