package lhdt.admin.svc.cityplanning.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.cmmn.domain.CmmnDomain;
import lhdt.cmmn.misc.CmmnField;
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
@Table(name="ctypln_dstrc_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPDistricInfo extends CmmnDomain {
    public CPDistricInfo(String districtName) {
        this.districtName = districtName;
    }
    /**
     * 도면번호(명)
     * COMMENT '지구 명'"
     */
    @Column(name = "dstrc_nm")
    @CmmnField(bizKey = true, order = 0)
    private String districtName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ctypln_dstrc_nm")
    @CmmnField(bizKey = true, order = 1)
    private CPLocalInfo cpLocalInfo;

    @JsonManagedReference
    @OneToMany(mappedBy = "cpDistricInfo", fetch= FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
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
