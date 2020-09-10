package lhdt.svc.landscape.model;

import lhdt.svc.common.model.Point;
import lhdt.svc.landscape.types.LandScapeAnalsType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Spring Boot Jackson - Ajax 객체 간 통신 방식에 따른 처리 오류로 인하여 펼쳐서 받아야함.
 */
@Data
public class LandScapeAnalsParam {
    private String landScapeAnalsName;
    private Double startPosX;
    private Double startPosY;
    private Double startPosZ;
    private Double endPosX;
    private Double endPosY;
    private Double endPosZ;
    LandScapeAnalsType landScapeAnalsType;
}
