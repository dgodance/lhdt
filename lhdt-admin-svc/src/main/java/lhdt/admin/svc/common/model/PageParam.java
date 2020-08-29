package lhdt.admin.svc.common.model;

import lhdt.ds.common.misc.DSPaginatorInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageParam<T> {
    private List<T> page;
    private DSPaginatorInfo pagenationInfo;
    private Integer size;
    private Integer nowPageNum;
}
