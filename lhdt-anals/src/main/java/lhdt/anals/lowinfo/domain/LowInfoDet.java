package lhdt.anals.lowinfo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lhdt.anals.hello.domain.Domain;
import lhdt.anals.hello.domain.SubType0;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LowInfoDet extends Domain {
    @Column(name = "low_info_det_name")
    private String lowInfoDetName;

    @Column(name = "low_info_det_contents")
    private String lowInfoDetContents;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="low_info_id")
    private LowInfo lowInfo;
}
