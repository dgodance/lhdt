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
@Table(name="cp_report_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPReportDetail extends Domain {
    /**
     * 지역명
     */
    @Column(name = "local_name")
    private String localName;

    /**
     * 지구명
     */
    @Column(name = "district_name")
    private String districtName;

    /**
     * 사업방식
     */
    @Column(name = "business_way")
    private String bussinessWay;

    /**
     * 용지명
     */
    @Column(name = "paper_name")
    private String paperName;

    /**
     * 획지명
     */
    @Column(name = "nomination")
    private String nomination;

    /**
     * 토지이용구분정보
     */
    @Column(name = "lnd_use_cls_info")
    private String lndUseClsInfo;

    /**
     * 면적
     */
    @Column(name = "area")
    private Long Area;

    /**
     * 허용용도
     */
    @Column(name = "allowalbe_use", columnDefinition="TEXT")
    private String allowableUse;

    /**
     * 불허용도
     */
    @Column(name = "not_allowalbe_use", columnDefinition="TEXT")
    private String notAllowableUse;

    /**
     * 건폐율
     */
    @Column(name = "building_to_land_ratio")
    private Long buildingToLandRatio;

    /**
     * 용적률
     */
    @Column(name = "fllor_area_ratio")
    private Long floorAreaRatio;

    /**
     * 최고높이
     */
    @Column(name = "area_max_height")
    private Long areaMaxHeight;

    /**
     * 최고층수
     */
    @Column(name = "floor_max_height")
    private Long floorMaxHeight;

    /**
     * 공동주택규모
     */
    @Column(name = "aprt_scale")
    private Long aprartmentScale;

    /**
     * 공동주택세대수
     */
    @Column(name = "num_scale_aprt_house")
    private Long numOfApartmentHouse;

    /**
     * 분양형태
     */
    @Column(name = "sales_type")
    private String salesType;

    /**
     * 필지이상이하구분
     */
    @Enumerated(EnumType.ORDINAL)
    private UpDownType areaUpDownType;

    /**
     * 층수이상이하구분
     */
    @Enumerated(EnumType.ORDINAL)
    private UpDownType floorUpDownType;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="file_id")
    private CPFileInfo CPFileInfo;
}
