package lhdt.admin.svc.cityplanning.persistence;


import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.domain.CPLocalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CPDistricInfoRepository extends JpaRepository<CPDistricInfo, Long> {
    CPDistricInfo findByDistrictNameAndCpLocalInfo(String areaName, CPLocalInfo cpLocalInfo);
}
