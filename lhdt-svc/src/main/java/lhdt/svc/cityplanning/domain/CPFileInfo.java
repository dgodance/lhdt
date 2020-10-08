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
@Table(name="ctypln_file_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPFileInfo extends Domain {
    /**
     * 파일명
     * COMMENT '파일 명'"
     */
    @Column(name = "file_nm")
    private String fileName;

    /**
     * 파일경로
     * COMMENT '파일 경로 명'"
     */
    @Column(name = "file_cours_nm")
    private String filePath;

    /**
     * 파일경로
     * COMMENT '원본 파일 명'"
     */
    @Column(name = "origin_file_nm")
    private String originFileName;

    /**
     * 파일종류
     * COMMENT '파일 타입 값'"
     */
    @Column(name = "file_ty_value")
    private CPFileType cpFileType;

    /**
     * 지구번호
     * COMMENT '지구 id'"
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dstrc_id")
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
