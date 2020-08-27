package lhdt.admin.svc.cityplanning.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lhdt.admin.svc.file.domain.FileInfo;
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
     * 식별키
     */
    @Column(name = "cp_id")
    @DsField(bizKey = true, order = 1)
    private Long lotId;

    /**
     * 사업유형
     */
    @Column(name = "project-type")
    private String projectTypeString;

    /**
     * 사업지구
     */
    @Column(name = "project-title")
    @DsField(bizKey = true, order = 2)
    private String projectTitle;

    /**
     * 가구번호
     */
    @Column(name = "block-code")
    @DsField(bizKey = true, order = 3)
    private String blockCode;

    /**
     * 획지번호
     */
    @Column(name = "lot-code")
    @DsField(bizKey = true, order = 4)
    private String lotCode;

    /**
     * 획지면적
     */
    @Column(name = "lot-area")
    private Long lotArea;

    /**
     * 용도지역
     */
    @Column(name = "land-use-zon")
    private String landUseZoning;

    /**
     * 토지이용
     */
    @Column(name = "land-use-plan")
    private String landUsePlan;

    /**
     * 대지분할합필
     */
    @Column(name = "lot-div-merge")
    private String lotDivideMarge;

    /**
     * 용도
     */
    @Column(name = "building-use")
    private String buildingUse;

    /**
     * 용도-지정
     */
    @Column(name = "building-use-defin")
    private String buildingUseDefined;

    /**
     * 용도-권장
     */
    @Column(name = "building-use-recomd")
    private String buildingUseRecommended;

    /**
     * 용도-허용
     */
    @Column(name = "building-use-allowd")
    private String buildingUseAllowed;

    /**
     * 용도-제한
     */
    @Column(name = "building-use-condi")
    private String buildingUseConditional;

    /**
     * 용도-불허
     */
    @Column(name = "building-use-fobidn")
    private String buildingUseForbidden;

    /**
     * 건폐율
     */
    @Column(name = "building-cov-ratio")
    private Long buildingCoverageRatio;

    /**
     * 용적률
     */
    @Column(name = "floor-area-ratio")
    private Long floorAreaRatio;

    /**
     * 용적률-기준
     */
    @Column(name = "floor-area-ratio-std")
    private Long floorAreaRatioStandard;

    /**
     * 용적률-허용
     */
    @Column(name = "floor-area-ratio-allowd")
    private Long floorAreaRatioAllowed;

    /**
     * 용적률-상한
     */
    @Column(name = "floor-area-ratio-maximum")
    private Long floorAreaRatioMaximum;

    /**
     * 최고높이
     */
    @Column(name = "maximun-build-height")
    private Long maximunBuildingHeight;

    /**
     * 최고층수
     */
    @Column(name = "maximun-build-floors")
    private Long maximunBuildingFloors;

    /**
     * 주택유형
     */
    @Column(name = "housing-type")
    private String housingTypeString;

    /**
     * 세대수
     */
    @Column(name = "num-of-household")
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
