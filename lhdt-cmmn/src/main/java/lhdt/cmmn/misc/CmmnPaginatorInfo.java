package lhdt.cmmn.misc;

import lombok.Data;

@Data
public class CmmnPaginatorInfo {
    private Integer startPageNum;
    private Integer lastPageNum;
    private Integer previousPaging;
    private Integer nextPaging;
}
