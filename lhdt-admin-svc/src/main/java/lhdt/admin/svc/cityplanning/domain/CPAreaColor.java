package lhdt.admin.svc.cityplanning.domain;

import lhdt.cmmn.domain.CmmnDomain;
import lhdt.cmmn.misc.CmmnField;
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
public class CPAreaColor extends CmmnDomain {
    /**
     * 토지이용분류(명)
     */
    @Column(name = "area_name")
    @CmmnField(bizKey = true, order = 0)
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
