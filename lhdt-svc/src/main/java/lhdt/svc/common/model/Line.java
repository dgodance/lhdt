package lhdt.svc.common.model;

import lombok.Data;

/**
 * 라인에 대한 데이터 구조 정의
 */
@Data
public class Line {
    /**
     * 시작점
     */
    private Point startPoint;
    /**
     * 종료점
     */
    private Point endPoint;
}
