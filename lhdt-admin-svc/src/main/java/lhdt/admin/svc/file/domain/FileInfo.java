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
import lhdt.cmmn.domain.CmmnDomain;
import lhdt.cmmn.misc.CmmnField;
import lhdt.cmmn.misc.CmmnFile;
import lhdt.cmmn.misc.CmmnFileMaster;
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
public class FileInfo extends CmmnDomain implements CmmnFile {
    /**
     * 파일명
     * COMMENT '파일 명'"
     */
    @NotNull
    @Column(name = "file_nm")
    @CmmnField(bizKey = true, order = 0)
    private String fileName;

    /**
     * 파일경로
     * COMMENT '파일 경로 명'"
     */
    @NotNull
    @Column(name = "file_cours_nm")
    @CmmnField(bizKey = true, order = 1)
    private String filePath;

    /**
     * 파일경로
     * COMMENT '원본 파일 명'"
     */
    @NotNull
    @Column(name = "origin_file_nm")
    private String originFileName;

    /**
     * 파일종류
     * COMMENT '파일 타입 값'"
     */
    @Column(name = "file_ty_value")
    private FileType fileType;
    /**
     * 파일종류
     * COMMENT '파일 분류 값'"
     */
    @Column(name = "file_cl_value")
    private FileClsType fileClsType;

    /**
     * 확장자
     * COMMENT '파일 확장자 명'"
     */
    @NotNull
    @Column(name = "file_extsn_nm")
    @CmmnField(bizKey = true, order = 2)
    private String fileExtention;

    /**
     * 지구번호
     * COMMENT '파일 번호'"
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="file_no")
    private CPDistricInfo cpDistricInfo;

    /**
     * CSV 레포트
     */
    @OneToMany(mappedBy = "cpfileInfo", fetch=FetchType.LAZY,
            cascade = CascadeType.ALL,orphanRemoval = true)
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

    /**
     * 이미지
     */
    @OneToMany(mappedBy = "lsDiffImgInfo", fetch=FetchType.LAZY,
            cascade = CascadeType.ALL,orphanRemoval = true)
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
