package lhdt.admin.svc.cityplanning.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CPFileRegistParam {
    private MultipartFile[] files;
}
