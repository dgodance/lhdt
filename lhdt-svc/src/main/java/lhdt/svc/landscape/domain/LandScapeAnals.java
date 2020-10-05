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
@Table(name="landscape_anals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeAnals extends Domain
{
    /**
     * 경관점 이름
     */
    @Column(name = "land_scape_anals_name")
    private String landScapeAnalsName;

    /**
     * 경관 시작점
     */
    @Column(name = "start_land_scape")
    private Point startLandScapePos;

    /**
     * 경관 종료점
     */
    @Column(name = "end_land_scape")
    private Point endLandScapePos;

    /**
     * 시작점 고도
     */
    @Column(name = "start_alt")
    private Double startAltitude;

    /**
     * 종료점 고도
     */
    @Column(name = "end_alt")
    private Double endAltitude;

    /**
     * 경관점 종류
     */
    @Enumerated(EnumType.ORDINAL)
    LandScapeAnalsType landScapeAnalsType;
}
