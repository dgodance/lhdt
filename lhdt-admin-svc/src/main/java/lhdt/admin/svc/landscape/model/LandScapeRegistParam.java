package lhdt.admin.svc.landscape.model;

import lhdt.admin.svc.landscape.type.LandScapeAnalsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Spring Boot Jackson - Ajax 객체 간 통신 방식에 따른 처리 오류로 인하여 펼쳐서 받아야함.
 * 경관점 등록 파라미터에 대하여 정의합니다
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeRegistParam {
    /**
     * 아이디
     */
    private Long id;
    /**
     * 경관명
     */
    private String landScapeAnalsName;
    /**
     * 시점 위도
     */
    private Double startPosX;
    /**
     * 시점 경도
     */
    private Double startPosY;
    /**
     * 시점 고도
     */
    private Double startPosZ;
    /**
     * 종점 위도
     */
    private Double endPosX;
    /**
     * 종점 경도
     */
    private Double endPosY;
    /**
     * 종점 고도
     */
    private Double endPosZ;
    /**
     * 경관 분석 종류
     */
    LandScapeAnalsType landScapeAnalsType;
}
