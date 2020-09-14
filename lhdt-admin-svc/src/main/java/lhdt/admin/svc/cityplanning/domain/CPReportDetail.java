package lhdt.admin.svc.cityplanning.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.cmmn.domain.CmmnDomain;
import lhdt.cmmn.misc.CmmnField;
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
public class CPReportDetail extends CmmnDomain {
    /**
     * 식별키
     */
    @Column(name = "cp_id")
    @CmmnField(bizKey = true, order = 1)
    private Long lotId;

    /**
     * 사업유형
     */
    @Column(name = "project_type")
    private String projectTypeString;

    /**
     * 사업지구
     */
    @Column(name = "project_title")
    private String projectTitle;

    /**
     * 가구번호
     */
    @Column(name = "block_code")
    private String blockCode;

    /**
     * 획지번호
     */
    @Column(name = "lot_code")
    private String lotCode;

    /**
     * 획지면적
     */
    @Column(name = "lot_area")
    private Long lotArea;

    /**
     * 용도지역
     */
    @Column(name = "land_use_zon")
    private String landUseZoning;

    /**
     * 토지이용
     */
    @Column(name = "land_use_plan")
    private String landUsePlan;

    /**
     * 대지분할합필
     */
    @Column(name = "lot_div_merge")
    private String lotDivideMarge;

    /**
     * 용도
     */
    @Column(name = "building_use")
    private String buildingUse;

    /**
     * 용도_지정
     */
    @Column(name = "building_use_defin")
    private String buildingUseDefined;

    /**
     * 용도_권장
     */
    @Column(name = "building_use_recomd")
    private String buildingUseRecommended;

    /**
     * 용도_허용
     */
    @Column(name = "building_use_allowd")
    private String buildingUseAllowed;

    /**
     * 용도_제한
     */
    @Column(name = "building_use_condi")
    private String buildingUseConditional;

    /**
     * 용도_불허
     */
    @Column(name = "building_use_fobidn")
    private String buildingUseForbidden;

    /**
     * 건폐율
     */
    @Column(name = "building_cov_ratio")
    private Long buildingCoverageRatio;

    /**
     * 용적률
     */
    @Column(name = "floor_area_ratio")
    private Long floorAreaRatio;

    /**
     * 용적률_기준
     */
    @Column(name = "floor_area_ratio_std")
    private Long floorAreaRatioStandard;

    /**
     * 용적률_허용
     */
    @Column(name = "floor_area_ratio_allowd")
    private Long floorAreaRatioAllowed;

    /**
     * 용적률_상한
     */
    @Column(name = "floor_area_ratio_maximum")
    private Long floorAreaRatioMaximum;

    /**
     * 최고높이
     */
    @Column(name = "maximun_build_height")
    private Long maximunBuildingHeight;

    /**
     * 최고층수
     */
    @Column(name = "maximun_build_floors")
    private Long maximunBuildingFloors;

    /**
     * 주택유형
     */
    @Column(name = "housing_type")
    private String housingTypeString;

    /**
     * 세대수
     */
    @Column(name = "num_of_household")
    private Long numberOfHouseholds;

    /**
     * 자료기준
     */
    @Column(name = "reference")
    private String reference;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cp_file_id")
    private FileInfo cpfileInfo;
}
