package lhdt.admin.svc.landscape.domain;

import com.sun.istack.NotNull;
import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lhdt.cmmn.domain.CmmnDomain;
import lhdt.admin.svc.landscape.type.LSPointActionType;
import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.*;

@Entity
@Table(name="scene_anals")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapePoint extends CmmnDomain {
    /**
     * COMMENT '경관 점 명'"
     */
    @NotNull
    @Column(name = "scene_point_nm")
    private String landScapePointName;

    /**
     *  NUMERIC COMMENT '경관 시작 위치 명'"
     */
    @NotNull
    @Column(name = "scene_begin_lc_nm")
    private Point startLandScapePos;

    /**
     *  NUMERIC COMMENT '경관 종료 위치 명'"
     */
    @Column(name = "scene_end_lc_nm")
    private Point endLandScapePos;

    /**
     *  NUMERIC COMMENT '시작 위치 높이 값'"
     */
    @Column(name = "begin_lc_hg_value")
    private Double startAltitude;

    /**
     *  NUMERIC COMMENT '종료 위치 높이 값'"
     */
    @Column(name = "end_lc_hg_value")
    private Double endAltitude;

    /**
     * COMMENT '경관 점 타입 값'"
     */
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "scene_point_ty_value")
    LandScapeAnalsType landScapePointType;

    /**
     * COMMENT '경관 점 작용 타입 값'"
     */
    @Column(name = "scene_point_actn_ty_value")
    LSPointActionType LSPointActionType;
}
