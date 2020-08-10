package lhdt.anals.cityplanning.domain;

import lhdt.anals.cityplanning.types.UpDownType;
import lhdt.anals.hello.domain.Domain;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityPlanReportDetail extends Domain {
    @Column(name = "city_plan_id")
    private Long cityPlanId;

    @Column(name = "area")
    private Long Area;

    @Column(name = "allowalbe_use")
    private String allowableUse;

    @Column(name = "not_allowalbe_use")
    private String notAllowableUse;

    @Column(name = "building_to_land_ratio")
    private Long buildingToLandRatio;

    @Column(name = "fllor_area_ratio")
    private Long floorAreaRatio;

    @Column(name = "area_max_height")
    private Integer areaMaxHeight;

    @Column(name = "floor_max_height")
    private Integer floorMaxHeight;

    @Column(name = "area_up_down_type")
    private UpDownType areaUpDownType;

    @Column(name = "floor_up_down_type")
    private UpDownType floorUpDownType;
}
