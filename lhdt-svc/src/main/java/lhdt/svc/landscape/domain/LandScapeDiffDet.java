package lhdt.svc.landscape.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lhdt.svc.common.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 경관비교상세
 * UK
 * fileName filePath landScapeDiff
 */
@Entity
@Table(name="landscape_diff_det")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeDiffDet extends Domain {
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="land_scape_id")
    private LandScapeDiff landScapeDiff;
}
