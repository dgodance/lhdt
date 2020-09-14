package lhdt.admin.svc.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import lhdt.cmmn.misc.CmmnPaginatorInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageParam<T> {
    private List<T> page;
    private CmmnPaginatorInfo pagenationInfo;
    private Integer size;
    private Integer nowPageNum;
}
