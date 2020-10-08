package lhdt.svc.landscape.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.data.geo.Point;

import lhdt.svc.common.Domain;
import lhdt.svc.landscape.types.LandScapeAnalsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 경관점에 대한 데이터 구조 정의 클래스
 * UK
 * landScapeAnalsName, startLandScape, endLandScape
 */
@Entity
@Table(name="scene_anals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeAnals extends Domain
{
    /**
     * 경관점 이름
     * COMMENT '경관 분석 명'"
     */
    @Column(name = "scene_anals_nm")
    private String landScapeAnalsName;

    /**
     * 경관 시작점
     *  COMMENT '경관 시작 위치 명'"
     */
    @Column(name = "scene_begin_lc_nm")
    private Point startLandScapePos;

    /**
     * 경관 종료점
     *  COMMENT '경관 종료 위치 명'"
     */
    @Column(name = "scene_end_lc_nm")
    private Point endLandScapePos;

    /**
     * 시작점 고도
     *  COMMENT '시작 위치 높이 값'"
     */
    @Column(name = "begin_lc_hg_value")
    private Double startAltitude;

    /**
     * 종료점 고도
     * COMMENT '종료 위치 높이 값'"
     */
    @Column(name = "end_lc_hg_value")
    private Double endAltitude;

    /**
     * 경관점 종류
     * COMMENT '경관 분석 타입 값'"
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "scene_anals_ty_value")
    LandScapeAnalsType landScapeAnalsType;
}
