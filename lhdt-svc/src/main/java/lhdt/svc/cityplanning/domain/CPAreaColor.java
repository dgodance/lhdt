package lhdt.svc.cityplanning.domain;

import lhdt.svc.common.Domain;
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
public class CPAreaColor extends Domain {
    /**
     * 토지이용분류(명)
     * COMMENT '지역 명'"
     */
    @Column(name = "area_nm")
    private String areaName;

    /**
     * 요약명
     *  VARCHAR(4000) COMMENT '지역 명 내용'"
     */
    @Column(name = "area_nm_cn")
    private String areaNmSumy;

    /**
     * 색상
     * COMMENT '지역 색 명'"
     */
    @Column(name = "area_color_nm")
    private String color;
}
