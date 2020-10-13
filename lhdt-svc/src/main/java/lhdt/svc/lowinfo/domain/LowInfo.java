package lhdt.svc.lowinfo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.sun.istack.NotNull;
import lhdt.svc.common.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 법령마스터
 * UK
 * lowInfoName
 */
@Entity
@Table(name="law_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LowInfo extends Domain {
    /**
     * COMMENT '법률 정보 명'"
     */
    @NotNull
    @Column(name = "law_info_nm")
    private String lowInfoName;

    @JsonManagedReference
    @OneToMany(mappedBy = "lowInfo", fetch= FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LowInfoDet> lowInfoDets = new ArrayList<>();
}
