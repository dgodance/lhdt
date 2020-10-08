package lhdt.svc.cityplanning.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lhdt.svc.cityplanning.types.UpDownType;
import lhdt.svc.common.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 지구계획보고상세
 * UK
 * id, cityPlanId
 */
@Entity
@Table(name="ctypln_report_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPReportDetail extends Domain {
    /**
     * 지역명
     * COMMENT '구역 명'"
     */
    @Column(name = "zone_nm")
    private String localName;

    /**
     * 지구명
     * COMMENT '지구 명'"
     */
    @Column(name = "dstrc_nm")
    private String districtName;

    /**
     * 사업방식
     * COMMENT '사업 방식 명'"
     */
    @Column(name = "bsns_mthd_nm")
    private String bussinessWay;

    /**
     * 용지명
     * COMMENT '종이 명'"
     */
    @Column(name = "paper_nm")
    private String paperName;

    /**
     * 획지명
     * COMMENT '필지 명'"
     */
    @Column(name = "lot_nm")
    private String nomination;

    /**
     * 토지이용구분정보
     * COMMENT '육지 이용 분류 명'"
     */
    @Column(name = "land_use_cl_nm")
    private String lndUseClsInfo;

    /**
     * 면적
     *  NUMERIC COMMENT '면적 값'"
     */
    @Column(name = "ar_value")
    private Long Area;

    /**
     * 허용용도
     * COMMENT '허용 용도 내용'"
     */
    @Column(name = "perm_prpos_cn")
    private String allowableUse;

    /**
     * 불허용도
     * COMMENT '불허 이용 내용'"
     */
    @Column(name = "nnpmsn_use_cn")
    private String notAllowableUse;

    /**
     * 건폐율
     *  NUMERIC COMMENT '건폐율 값'"
     */
    @Column(name = "btl_ratio_value")
    private Long buildingToLandRatio;

    /**
     * 용적률
     *  NUMERIC COMMENT '용적률 값'"
     */
    @Deprecated
    @Column(name = "fllor_area_ratio")
    private Long floorAreaRatio;

    /**
     * 최고높이
     *  NUMERIC COMMENT '지역 최대 높이 값'"
     */
    @Column(name = "area_mxmm_hg_value")
    private Long areaMaxHeight;

    /**
     * 최고층수
     * COMMENT '최대 층 수'"
     */
    @Column(name = "mxmm_floor_co")
    private Long floorMaxHeight;

    /**
     * 공동주택규모
     *  NUMERIC COMMENT '공동주택 규모 값'"
     */
    @Column(name = "aphus_scale_value")
    private Long aprartmentScale;

    /**
     * 공동주택세대수
     * COMMENT '공동주택 세대 수'"
     */
    @Column(name = "aphus_hshld_co")
    private Long numOfApartmentHouse;

    /**
     * 분양형태
     * COMMENT '분양 타입 명'"
     */
    @Column(name = "lttot_ty_nm")
    private String salesType;

    /**
     * 필지이상이하구분
     */
    @Enumerated(EnumType.ORDINAL)
    @col
    private UpDownType areaUpDownType;

    /**
     * 층수이상이하구분
     */
    @Enumerated(EnumType.ORDINAL)
    private UpDownType floorUpDownType;

    /**
     * COMMENT '파일 id'"
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="file_id")
    private CPFileInfo CPFileInfo;
}
