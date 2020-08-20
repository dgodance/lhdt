package lhdt.admin.svc.lowinfo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lhdt.admin.svc.common.Domain;
import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
import lombok.*;

/**
 * 법령상세
 * UK
 * lowInfoDetName, lowInfoDetContents, lowInfo
 */
@Entity
@Table(name="low_info_det")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LowInfoDet extends DsDomain {
    @Column(name = "low_info_det_name")
    private String lowInfoDetName;

    @Column(name = "low_info_det_contents")
    private String lowInfoDetContents;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="low_info_id")
    private LowInfo lowInfo;
}
