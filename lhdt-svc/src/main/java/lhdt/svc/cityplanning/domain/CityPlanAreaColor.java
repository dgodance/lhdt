package lhdt.svc.cityplanning.domain;

import lhdt.svc.common.Domain;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="city_plan_area_color")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityPlanAreaColor extends Domain {
    /**
     * 토지이용분류(명)
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 요약명
     */
    @Column(name = "area_name_summary")
    private String areaNmSumy;

    /**
     * 색상
     */
    @Column(name = "color")
    private String color;
}
