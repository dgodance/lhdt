package lhdt.admin.svc.landscape.model;

import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Spring Boot Jackson - Ajax 객체 간 통신 방식에 따른 처리 오류로 인하여 펼쳐서 받아야함.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeRegistParam {
    private Long id;
    private String landScapeAnalsName;
    private Double startPosX;
    private Double startPosY;
    private Double startPosZ;
    private Double endPosX;
    private Double endPosY;
    private Double endPosZ;
    LandScapeAnalsType landScapeAnalsType;
}
