package lhdt.svc.common;

import lombok.Data;

/**
 * 페이지네이션에 대한 데이터 구조를 정의합니다
 */
@Data
public class PaginatorInfo {
    /**
     * 시작 페이지 번호
     */
    private Integer startPageNum;
    /**
     * 종료 페이지 번호
     */
    private Integer lastPageNum;
    /**
     * 이전 페이지 번호
     */
    private Integer previousPaging;
    /**
     * 다음 페이지 번호
     */
    private Integer nextPaging;
}
