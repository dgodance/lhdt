package lhdt.svc.landscape.model;

import lhdt.svc.common.model.Point;
import lhdt.svc.landscape.types.LandScapeAnalsType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 경관점 등록을 위한 인터페이스 데이터 구조 정의 클래스
 * Spring Boot Jackson - Ajax 객체 간 통신 방식에 따른 처리 오류로 인하여 펼쳐서 받아야함.
 */
@Data
public class LandScapeAnalsParam {
    /**
     * 경관명
     */
    private String landScapeAnalsName;
    /**
     * 시작점 위도
     */
    private Double startPosX;
    /**
     * 시작점 경도
     */
    private Double startPosY;
    /**
     * 시작점 고도
     */
    private Double startPosZ;
    /**
     * 종료점 위도
     */
    private Double endPosX;
    /**
     * 종료점 경도
     */
    private Double endPosY;
    /**
     * 종료점 고도
     */
    private Double endPosZ;
    /**
     * 경관점 종류
     */
    LandScapeAnalsType landScapeAnalsType;
}
