package lhdt.svc.cityplanning.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.svc.common.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 지구계획지구번호
 * UK
 * id, cityPlanId
 */
@Entity
@Table(name="cp_district_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPDistricInfo extends Domain {
    public CPDistricInfo(String districtName) {
        this.districtName = districtName;
    }
    /**
     * 도면번호(명)
     */
    @Column(name = "district_name")
    private String districtName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="local_id")
    private CPLocalInfo cpLocalInfo;

    @JsonManagedReference
    @OneToMany(mappedBy = "cpDistricInfo", fetch= FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CPFileInfo> cpFileInfos = new ArrayList<>();

    public void addCityInfo(CPFileInfo cpFileInfo) {
        if(cpFileInfo.getCpDistricInfo() != this)
            cpFileInfo.setCpDistricInfo(this);
        this.cpFileInfos.add(cpFileInfo);
    }
    public void addCityInfos(List<CPFileInfo> cpFileInfos) {
        cpFileInfos.forEach(p -> {
            if(p.getCpDistricInfo() != this)
                p.setCpDistricInfo(this);
            this.cpFileInfos.add(p);
        });
    }
}
