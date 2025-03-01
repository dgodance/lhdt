package lhdt.admin.svc.lowinfo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lhdt.admin.svc.common.Domain;
import lhdt.cmmn.domain.CmmnDomain;
import lhdt.cmmn.misc.CmmnField;
import lombok.*;

/**
 * 법령마스터
 * UK
 * lowInfoName
 */
@Entity
@Table(name="low_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LowInfo extends CmmnDomain {

    @Column(name = "low_info_name")
    @CmmnField(bizKey = true, order = 0)
    private String lowInfoName;

    @JsonManagedReference
    @OneToMany(mappedBy = "lowInfo", fetch= FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LowInfoDet> lowInfoDets = new ArrayList<>();
}
