package lhdt.svc.cityplanning.persistence;

import java.util.List;

import lhdt.svc.cityplanning.domain.CPFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import lhdt.svc.cityplanning.domain.CPReportDetail;

public interface CPReportDetailRepository extends JpaRepository<CPReportDetail, Long> {
    boolean existsByLocalNameAndDistrictNameAndBussinessWayAndPaperNameAndNomination
            (String localName, String districtName, String bussinessWay, String paperName, String nomination);
    CPReportDetail findByLocalNameAndDistrictNameAndBussinessWayAndPaperNameAndNomination
            (String localName, String districtName, String bussinessWay, String paperName, String nomination);
    List<CPReportDetail> findAllByLocalNameAndDistrictNameAndBussinessWayAndPaperNameAndNomination
            (String localName, String districtName, String bussinessWay, String paperName, String nomination);
}
