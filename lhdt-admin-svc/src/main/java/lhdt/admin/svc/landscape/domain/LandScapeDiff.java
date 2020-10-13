package lhdt.admin.svc.landscape.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.cmmn.domain.CmmnDomain;
import lhdt.cmmn.misc.CmmnField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@Entity
@Table(name="scene_cmpr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name="jsonb", typeClass=JsonBinaryType.class)
public class LandScapeDiff extends CmmnDomain {
    /**
     * COMMENT '경관 비교 명'"
     */
    @NotNull
    @CmmnField(bizKey = true, order = 0)
    @Column(name = "scene_cmpr_nm")
    private String lsDiffName;


    /**
     *  JSONB COMMENT '카메라 내용'"
     */
    @NotNull
    @Type(type = "jsonb")
    @Column(name = "camera_cn", columnDefinition = "jsonb")
    private String captureCameraState;

    /**
     * COMMENT '경관 비교 그룹 id'"
     */
    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="scene_cmpr_group_id")
    @CmmnField(bizKey = true, order = 1)
    private LandScapeDiffGroup landScapeDiffGroup;

    /**
     * COMMENT '경관 비교 이미지 id'"
     */
    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="scene_cmpr_img_id")
    @CmmnField(bizKey = true, order = 2)
    private FileInfo lsDiffImgInfo;
}
