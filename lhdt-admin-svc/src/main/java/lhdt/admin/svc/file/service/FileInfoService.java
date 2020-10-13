package lhdt.admin.svc.file.service;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.common.AdminSvcService;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 파일 처리 서비스
 */
public interface FileInfoService extends AdminSvcService<FileInfo, Long> {
    /**
     * 파일 처리 프로세스
     * @param multipartFiles
     * @return
     */
    List<FileInfo> procCPFiles(MultipartFile[] multipartFiles);
}
