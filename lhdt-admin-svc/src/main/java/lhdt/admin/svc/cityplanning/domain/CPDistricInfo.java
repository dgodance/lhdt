package lhdt.admin.svc.cityplanning.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.admin.svc.file.domain.FileInfo;
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
public class CPDistricInfo extends DsDomain {
    public CPDistricInfo(String districtName) {
        this.districtName = districtName;
    }
    /**
     * 도면번호(명)
     */
    @Column(name = "district_name")
    @DsField(bizKey = true, order = 0)
    private String districtName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="local_id")
    @DsField(bizKey = true, order = 1)
    private CPLocalInfo cpLocalInfo;

    @OneToMany(mappedBy = "cpDistricInfo", fetch= FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<FileInfo> fileInfos = new ArrayList<>();

    public void addCityInfo(FileInfo fileInfo) {
        if(fileInfo.getCpDistricInfo() != this)
            fileInfo.setCpDistricInfo(this);
        this.fileInfos.add(fileInfo);
    }
    public void addCityInfos(List<FileInfo> fileInfos) {
        fileInfos.forEach(p -> {
            if(p.getCpDistricInfo() != this)
                p.setCpDistricInfo(this);
            this.fileInfos.add(p);
        });
    }
}
