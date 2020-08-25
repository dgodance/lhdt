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

    @NotNull
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "landScapeDiff")
    private byte[] image;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ls_diff_group_id")
    @DsField(bizKey = true, order = 1)
    private LandScapeDiffGroup landScapeDiffGroup;
}
