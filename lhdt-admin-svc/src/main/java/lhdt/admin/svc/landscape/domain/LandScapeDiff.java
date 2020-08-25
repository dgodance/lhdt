package lhdt.admin.svc.landscape.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

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
public class LandScapeDiff extends DsDomain {
    @NotNull
    @DsField(bizKey = true, order = 0)
    @Column(name = "ls_diff_name")
    private String lsDiffName;

    @Column(name = "ls_camera_state", columnDefinition = "json")
    private String captureCameraState;

    @Column(name = "ls_file_name")
    private String fileName;

    @Column(name = "ls_file_path")
    private String filePath;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ls_diff_group_id")
    private LandScapeDiffGroup landScapeDiffGroup;
}
