package lhdt.svc.cityplanning.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.svc.cityplanning.types.CPFileType;
import lhdt.svc.common.Domain;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 지구계획보고상세
 * UK
 * id, cityPlanId
 */
@Entity
@Table(name="cp_file_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPFileInfo extends Domain {
    /**
     * 파일명
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 파일경로
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 파일경로
     */
    @Column(name = "origin_file_name")
    private String originFileName;

    /**
     * 파일종류
     */
    @Column(name = "file_type")
    private CPFileType cpFileType;

    /**
     * 지구번호
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="district_id")
    private CPDistricInfo cpDistricInfo;

    @JsonManagedReference
    @OneToMany(mappedBy = "CPFileInfo", fetch=FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CPReportDetail> CPReportDetails = new ArrayList<>();

    public void addCityInfo(CPReportDetail CPReportDetail) {
        if(CPReportDetail.getCPFileInfo() != this)
            CPReportDetail.setCPFileInfo(this);
        this.CPReportDetails.add(CPReportDetail);
    }
    public void addCityInfos(List<CPReportDetail> CPReportDetail) {
        CPReportDetail.forEach(p -> {
            if(p.getCPFileInfo() != this)
                p.setCPFileInfo(this);
            this.CPReportDetails.add(p);
        });
    }
}
