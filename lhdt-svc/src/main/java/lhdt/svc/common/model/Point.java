package lhdt.svc.common.model;

import lombok.Data;

/**
 * 포인트에 대한 데이터 구조 정의
 */
@Data
public class Point {
    /**
     * x점
     */
    private Double x;
    /**
     * y점
     */
    private Double y;
    /**
     * z점
     */
    private Double z;
}
