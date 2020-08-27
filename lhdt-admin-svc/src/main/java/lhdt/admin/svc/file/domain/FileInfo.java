package lhdt.admin.svc.file.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lhdt.admin.svc.cityplanning.domain.CPDistricInfo;
import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import lhdt.admin.svc.file.domain.listener.FileInfoListener;
import lhdt.admin.svc.file.type.FileClsType;
import lhdt.admin.svc.file.type.FileType;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lhdt.ds.common.domain.DsDomain;
import lhdt.ds.common.misc.DsField;
import lhdt.ds.common.misc.DsFile;
import lhdt.ds.common.misc.DsFileMaster;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 지구계획보고상세
 * UK
 * id, cityPlanId
 */
@Entity
@EntityListeners(FileInfoListener.class)
@Table(name="file_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo extends DsDomain implements DsFile {
    /**
     * 파일명
     */
    @NotNull
    @Column(name = "file_name")
    @DsField(bizKey = true, order = 0)
    private String fileName;

    /**
     * 파일경로
     */
    @NotNull
    @Column(name = "file_path")
    @DsField(bizKey = true, order = 1)
    private String filePath;

    /**
     * 파일경로
     */
    @NotNull
    @Column(name = "origin_file_name")
    private String originFileName;

    /**
     * 파일종류
     */
    @Column(name = "file_type")
    private FileType fileType;
    /**
     * 파일종류
     */
    @Column(name = "file_cls_type")
    private FileClsType fileClsType;

    /**
     * 확장자
     */
    @NotNull
    @Column(name = "file_ext")
    @DsField(bizKey = true, order = 2)
    private String fileExtention;

    /**
     * 지구번호
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="district_id")
    private CPDistricInfo cpDistricInfo;

    @OneToMany(mappedBy = "cpfileInfo", fetch=FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CPReportDetail> CPReportDetails = new ArrayList<>();

    public void addCityInfo(CPReportDetail CPReportDetail) {
        if(CPReportDetail.getCpfileInfo() != this)
            CPReportDetail.setCpfileInfo(this);
        this.CPReportDetails.add(CPReportDetail);
    }

    public void addCityInfos(List<CPReportDetail> CPReportDetail) {
        CPReportDetail.forEach(p -> {
            if(p.getCpfileInfo() != this)
                p.setCpfileInfo(this);
            this.CPReportDetails.add(p);
        });
    }

    @OneToMany(mappedBy = "lsDiffImgInfo", fetch=FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LandScapeDiff> landScapeDiffList = new ArrayList<>();

    public void addLsScapeDiffInfo(LandScapeDiff landScapeDiff) {
        if(landScapeDiff.getLsDiffImgInfo() != this)
            landScapeDiff.setLsDiffImgInfo(this);
        this.landScapeDiffList.add(landScapeDiff);
    }
    public void addLsScapeDiffs(List<LandScapeDiff> landScapeDiffList) {
        landScapeDiffList.forEach(p -> {
            if(p.getLsDiffImgInfo() != this)
                p.setLsDiffImgInfo(this);
            this.landScapeDiffList.add(p);
        });
    }

    @Override
    public String toString() {
        return this.getFilePath() + "/" + this.getFileName() + "." + this.getFileExtention();
    }

    @Override
    public void delete() {
        var file = new File(this.toString());
        if(file.exists())
            file.delete();
    }
}
