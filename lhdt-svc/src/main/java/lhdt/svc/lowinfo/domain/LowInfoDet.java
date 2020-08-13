package lhdt.svc.lowinfo.domain;

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
 * 법령상세
 * UK
 * lowInfoDetName, lowInfoDetContents, lowInfo
 */
@Entity
@Table(name="low_info_det")
@Getter
@Setter
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
