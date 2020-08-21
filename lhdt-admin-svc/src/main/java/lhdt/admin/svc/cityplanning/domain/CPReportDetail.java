package lhdt.admin.svc.cityplanning.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lhdt.admin.svc.cityplanning.type.UpDownType;
import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
public class CPReportDetail extends DsDomain {
    /**
     * 지역명
     */
    @Column(name = "local_name")
    @DsField(bizKey = true, order = 0)
    private String localName;

    /**
     * 지구명
     */
    @Column(name = "district_name")
    @DsField(bizKey = true, order = 1)
    private String districtName;

    /**
     * 사업방식
     */
    @Column(name = "business_way")
    @DsField(bizKey = true, order = 2)
    private String bussinessWay;

    /**
     * 용지명
     */
    @Column(name = "paper_name")
    @DsField(bizKey = true, order = 3)
    private String paperName;

    /**
     * 획지명
     */
    @Column(name = "nomination")
    @DsField(bizKey = true, order = 4)
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
