package lhdt.admin.svc.landscape.model;

import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeDiffParam {
    private Long id;
    private Long landScapeDiffGroupId;
    private String captureCameraState;
    private String landscapeName;
    private MultipartFile image;
}
