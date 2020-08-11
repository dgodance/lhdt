package lhdt.anals.landscape.domain;

import lhdt.anals.hello.domain.Domain;
import lhdt.anals.landscape.types.LandScapeAnalsType;
import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
