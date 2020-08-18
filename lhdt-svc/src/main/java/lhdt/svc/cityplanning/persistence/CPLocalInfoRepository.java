package lhdt.svc.cityplanning.persistence;

import lhdt.svc.cityplanning.domain.CPAreaColor;
import lhdt.svc.cityplanning.domain.CPLocalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPLocalInfoRepository extends JpaRepository<CPLocalInfo, Long> {
    boolean existsByLocalName(String localName);
    CPLocalInfo findByLocalName(String localName);
    List<CPLocalInfo> findAllByLocalName(String localName);
}
