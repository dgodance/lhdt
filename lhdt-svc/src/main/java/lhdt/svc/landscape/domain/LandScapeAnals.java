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
 * 경관분석마스터
 * UK
 * landScapeAnalsName, startLandScape, endLandScape
 */
@Entity
@Table(name="landscape_anals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeAnals extends Domain {
    @Column(name = "land_scape_anals_name")
    private String landScapeAnalsName;

    @Column(name = "start_land_scape")
    private Point startLandScapePos;

    @Column(name = "end_land_scape")
    private Point endLandScapePos;

    @Column(name = "start_alt")
    private Double startAltitude;

    @Column(name = "end_alt")
    private Double endAltitude;

    @Enumerated(EnumType.ORDINAL)
    LandScapeAnalsType landScapeAnalsType;
}
