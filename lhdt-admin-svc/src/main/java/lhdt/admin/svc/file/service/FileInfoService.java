package lhdt.admin.svc.file.service;

import lhdt.admin.svc.file.domain.FileInfo;
import lhdt.admin.svc.common.AdminSvcService;
import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileInfoService extends AdminSvcService<FileInfo, Long> {
    List<FileInfo> procCPFiles(MultipartFile[] multipartFiles);
}
