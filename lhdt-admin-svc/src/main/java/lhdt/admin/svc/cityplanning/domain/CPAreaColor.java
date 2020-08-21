package lhdt.admin.svc.cityplanning.domain;

import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
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
public class CPAreaColor extends DsDomain {
    /**
     * 토지이용분류(명)
     */
    @Column(name = "area_name")
    @DsField(bizKey = true, order = 0)
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
