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
@Table(name="ctypln_report_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPReportDetail extends CmmnDomain {
    /**
     * 식별키
     * COMMENT '도시계획 id'"
     */
    @Column(name = "ctypln_id")
    @CmmnField(bizKey = true, order = 1)
    private Long lotId;

    /**
     * 사업유형
     * COMMENT '프로젝트 타입 명'"
     */
    @Column(name = "project_ty_nm")
    private String projectTypeString;

    /**
     * 사업지구
     * COMMENT '프로젝트 제목'"
     */
    @Column(name = "project_sj")
    private String projectTitle;

    /**
     * 가구번호
     *  VARCHAR(10) COMMENT '블로 코드'"
     */
    @Column(name = "blck_code", columnDefinition = " varchar(10)")
    private String blockCode;

    /**
     * 획지번호
     *  VARCHAR(10) COMMENT '필지 코드'"
     */
    @Column(name = "lot_code", columnDefinition = " varchar(10)")
    private String lotCode;

    /**
     * 획지면적
     *  NUMERIC COMMENT '필지 면적 값'"
     */
    @Column(name = "lot_ar_value")
    private Long lotArea;

    /**
     * 용도지역
     * COMMENT '육지 이용 구역 명'"
     */
    @Column(name = "land_use_zone_nm")
    private String landUseZoning;

    /**
     * 토지이용
     * COMMENT '육지 이용 계획 명'"
     */
    @Column(name = "land_use_plan_nm")
    private String landUsePlan;

    /**
     * 대지분할합필
     * COMMENT '필지 분할 병합 명'"
     */
    @Column(name = "lot_partitn_mrg_nm")
    private String lotDivideMarge;

    /**
     * 용도
     * COMMENT '건물 이용 명'"
     */
    @Column(name = "buld_use_nm")
    private String buildingUse;

    /**
     * 용도_지정
     * COMMENT '건물 이용 정의 명'"
     */
    @Column(name = "buld_use_dfn_nm")
    private String buildingUseDefined;

    /**
     * 용도_권장
     * COMMENT '건물 이용 추천 명'"
     */
    @Column(name = "buld_use_recomend_nm")
    private String buildingUseRecommended;

    /**
     * 용도_허용
     * COMMENT '건물 이용 허가 명'"
     */
    @Column(name = "buld_use_perm_nm")
    private String buildingUseAllowed;

    /**
     * 용도_제한
     * COMMENT '건물 이용 조건 명'"
     */
    @Column(name = "buld_use_cnd_nm")
    private String buildingUseConditional;

    /**
     * 용도_불허
     * COMMENT '건물 이용 제한 명'"
     */
    @Column(name = "buld_use_prhibt_nm")
    private String buildingUseForbidden;

    /**
     * 건폐율
     *  NUMERIC COMMENT '용적률 값'"
     */
    @Column(name = "measrmt_ratio_value")
    private Long buildingCoverageRatio;

    /**
     * 용적률
     *  NUMERIC COMMENT '용적2률 값'"
     */
    @Column(name = "measrmt2_ratio_value")
    private Long floorAreaRatio;

    /**
     * 용적률_기준
     *  NUMERIC COMMENT '용적률 기준 값'"
     */
    @Column(name = "measrmt_ratio_stdr_value")
    private Long floorAreaRatioStandard;

    /**
     * 용적률_허용
     *  NUMERIC COMMENT '용적률 허용 값'"
     */
    @Column(name = "measrmt_ratio_perm_value")
    private Long floorAreaRatioAllowed;

    /**
     * 용적률_상한
     *  NUMERIC COMMENT '용적률 제한 값'"
     */
    @Column(name = "measrmt_ratio_prhibt_value")
    private Long floorAreaRatioMaximum;

    /**
     * 최고높이
     *  NUMERIC COMMENT '건물 최대 높이 값'"
     */
    @Column(name = "buld_mxmm_hg_value")
    private Long maximunBuildingHeight;

    /**
     * 최고층수
     *  NUMERIC COMMENT '건물 최대 층수 값'"
     */
    @Column(name = "buld_mxmm_floor_value")
    private Long maximunBuildingFloors;

    /**
     * 주택유형
     * COMMENT '주택 타입 명'"
     */
    @Column(name = "house_ty_nm")
    private String housingTypeString;

    /**
     * 세대수
     * COMMENT '세대 수'"
     */
    @Column(name = "hshld_co")
    private Long numberOfHouseholds;

    /**
     * 자료기준
     */
    @Column(name = "reference")
    private String reference;

    /**
     * COMMENT '도시계획 파일 id'"
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ctypln_file_id")
    private FileInfo cpfileInfo;
}
