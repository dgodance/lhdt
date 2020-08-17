package lhdt.svc.cityplanning.persistence;

import lhdt.svc.cityplanning.domain.CityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityInfoRepository extends JpaRepository<CityInfo, Long> {
    boolean existsByCityName(String cityName);
    CityInfo findByCityName(String cityName);
    List<CityInfo> findAllByCityName(String cityName);
}
