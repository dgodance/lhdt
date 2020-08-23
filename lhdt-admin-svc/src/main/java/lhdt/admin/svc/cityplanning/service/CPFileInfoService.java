package lhdt.admin.svc.cityplanning.service;

import lhdt.admin.svc.cityplanning.domain.CPFileInfo;
import lhdt.admin.svc.common.AdminSvcService;
import lhdt.ds.common.misc.DsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CPFileInfoService extends AdminSvcService<CPFileInfo, Long> {
    List<CPFileInfo> procCPFiles(MultipartFile[] multipartFiles);
}
