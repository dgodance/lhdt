package lhdt.admin.svc.landscape.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
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
@Table(name="ls_diff")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name="jsonb", typeClass=JsonBinaryType.class)
public class LandScapeDiff extends DsDomain {
    @NotNull
    @DsField(bizKey = true, order = 0)
    @Column(name = "ls_diff_name")
    private String lsDiffName;


    @NotNull
    @Type(type = "jsonb")
    @Column(name = "ls_camera_state", columnDefinition = "jsonb")
    private String captureCameraState;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ls_diff_group_id")
    @DsField(bizKey = true, order = 1)
    private LandScapeDiffGroup landScapeDiffGroup;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="ls_diff_img_id")
    @DsField(bizKey = true, order = 2)
    private FileInfo lsDiffImgInfo;
}
