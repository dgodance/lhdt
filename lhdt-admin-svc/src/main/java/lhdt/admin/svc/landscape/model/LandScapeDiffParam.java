package lhdt.admin.svc.landscape.model;

import lhdt.admin.svc.landscape.domain.LandScapeDiff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 경관비교전달 인자를 정의합니다
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeDiffParam {
    /**
     * 아이디
     */
    private Long id;
    /**
     * 경관 비교 그룹 아이디
     */
    private Long landScapeDiffGroupId;
    /**
     * 카메라 정보
     */
    private String captureCameraState;
    /**
     * 경관명
     */
    private String landscapeName;
    /**
     * 이미지 정보
     */
    private MultipartFile image;
}
