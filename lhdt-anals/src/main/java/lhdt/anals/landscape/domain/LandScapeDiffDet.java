package lhdt.anals.landscape.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lhdt.anals.hello.domain.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 경관비교상세
 * UK
 * fileName filePath landScapeDiff
 */
@Entity
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
