package lhdt.admin.svc.cityplanning.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 지구계획지역정보
 * UK
 * id, cityPlanId
 */
@Entity
@Table(name="cp_local_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPLocalInfo extends DsDomain {
    public CPLocalInfo(String localName) {
        this.localName = localName;
    }
    /**
     * 지역명
     */
    @Column(name = "local_name")
    @DsField(bizKey = true, order = 0)
    private String localName;

    @JsonManagedReference
    @OneToMany(mappedBy = "cpLocalInfo", fetch= FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CPDistricInfo> cpDistricInfos = new ArrayList<>();

    public void addCityInfo(CPDistricInfo cpDistricInfo) {
        if(cpDistricInfo.getCpLocalInfo() != this)
            cpDistricInfo.setCpLocalInfo(this);
        this.cpDistricInfos.add(cpDistricInfo);
    }
    public void addCityInfos(List<CPDistricInfo> cpDistricInfos) {
        cpDistricInfos.forEach(p -> {
            if(p.getCpLocalInfo() != this)
                p.setCpLocalInfo(this);
            this.cpDistricInfos.add(p);
        });
    }
}
