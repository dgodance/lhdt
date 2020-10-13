package lhdt.admin.svc.cityplanning.domain;

import lhdt.cmmn.domain.CmmnDomain;
import lhdt.cmmn.misc.CmmnField;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ctypln_area_color")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CPAreaColor extends CmmnDomain {
    /**
     * 토지이용분류(명)
     * COMMENT '지역 명'
     */
    @Column(name = "area_nm")
    @CmmnField(bizKey = true, order = 0)
    private String areaName;

    /**
     * 요약명
     * COMMENT '지역 명 내용'
     */
    @Column(name = "area_nm_cn")
    private String areaNmSumy;

    /**
     * 색상
     * COMMENT '지역 색 명'
     */
    @Column(name = "area_color_nm")
    private String color;
}
