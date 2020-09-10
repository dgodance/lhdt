package lhdt.ds.common.misc;

import lombok.Data;

@Data
public class DSPaginatorInfo {
    private Integer startPageNum;
    private Integer lastPageNum;
    private Integer previousPaging;
    private Integer nextPaging;
}
