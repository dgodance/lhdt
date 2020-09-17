package lhdt.admin.svc.landscape.domain;

import com.sun.istack.NotNull;
import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lhdt.cmmn.domain.CmmnDomain;
import lhdt.admin.svc.landscape.type.LSPointActionType;
import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.*;

@Entity
@Table(name="landscape_anals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapePoint extends CmmnDomain {
    @NotNull
    @Column(name = "land_scape_point_name")
    private String landScapePointName;

    @NotNull
    @Column(name = "start_land_scape")
    private Point startLandScapePos;

    @Column(name = "end_land_scape")
    private Point endLandScapePos;

    @Column(name = "start_alt")
    private Double startAltitude;

    @Column(name = "end_alt")
    private Double endAltitude;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    LandScapeAnalsType landScapePointType;

    LSPointActionType LSPointActionType;
}
