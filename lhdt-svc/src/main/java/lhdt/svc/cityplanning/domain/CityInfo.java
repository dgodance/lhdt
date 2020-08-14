package lhdt.svc.cityplanning.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.svc.common.Domain;
import lhdt.svc.hello.domain.SubType0;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 지구계획보고상세
 * UK
 * id, cityPlanId
 */
@Entity
@Table(name="city_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityInfo extends Domain {
    /**
     * 도면번호(명)
     */
    @Column(name = "city_name")
    private String cityName;

    @JsonManagedReference
    @OneToMany(mappedBy = "cityInfo", fetch=FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CityPlanReportDetail> cityPlanReportDetails = new ArrayList<>();

    public void addCityInfo(CityPlanReportDetail cityPlanReportDetail) {
        this.cityPlanReportDetails.add(cityPlanReportDetail);
        if(cityPlanReportDetail.getCityInfo() != this)
            cityPlanReportDetail.setCityInfo(this);
    }
    public void addCityInfos(List<CityPlanReportDetail> cityPlanReportDetail) {
        cityPlanReportDetail.forEach(p -> {
            if(p.getCityInfo() != this)
                p.setCityInfo(this);
            this.cityPlanReportDetails.add(p);
        });
    }
}
