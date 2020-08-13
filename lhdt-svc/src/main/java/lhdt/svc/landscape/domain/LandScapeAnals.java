package lhdt.svc.landscape.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeAnals extends Domain {
    @Column(name = "land_scape_anals_name")
    private String landScapeAnalsName;

    @Column(name = "start_land_scape")
    private Point startLandScape;

    @Column(name = "end_land_scape")
    private Point endLandScape;

    @Enumerated(EnumType.ORDINAL)
    LandScapeAnalsType landScapeAnalsType;
}
