package lhdt.anals.cityplanning.domain;

import lhdt.anals.cityplanning.types.UpDownType;
import lhdt.anals.hello.domain.Domain;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.net.InterfaceAddress;

/**
 * 지구계획보고상세
 * UK
 * id, cityPlanId
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityPlanReportDetail extends Domain {
    /**
     * 도면번호(명)
     */
    @Column(name = "city_plan_id")
    private Long cityPlanId;

    /**
     * 도면번호(명)
     */
    @Column(name = "drawingId")
    private String drawingId;

    /**
     * 가구번호(명)
     */
    @Column(name = "householdId")
    private String householdId;

    /**
     * 면적
     */
    @Column(name = "area")
    private Long Area;

    /**
     * 허용용도
     */
    @Column(name = "allowalbe_use")
    private String allowableUse;

    /**
     * 불허용도
     */
    @Column(name = "not_allowalbe_use")
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
}
