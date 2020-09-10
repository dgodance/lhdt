package lhdt.svc.cityplanning.persistence;


import lhdt.svc.cityplanning.domain.CPAreaColor;
import lhdt.svc.cityplanning.domain.CPDistricInfo;
import lhdt.svc.cityplanning.domain.CPLocalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPDistricInfoRepository extends JpaRepository<CPDistricInfo, Long> {
    boolean existsByDistrictNameAndCpLocalInfo(String areaName, CPLocalInfo cpLocalInfo);
    CPDistricInfo findByDistrictNameAndCpLocalInfo(String areaName, CPLocalInfo cpLocalInfo);
    List<CPDistricInfo> findAllByDistrictNameAndCpLocalInfo(String areaName, CPLocalInfo cpLocalInfo);
}
