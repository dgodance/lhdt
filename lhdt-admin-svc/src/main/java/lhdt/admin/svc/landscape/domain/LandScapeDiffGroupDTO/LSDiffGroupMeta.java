package lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LSDiffGroupMeta {
    private Integer page;
    private Integer pages;
    private Integer perpage;
    private Integer total;
    private String sort;
    private String field;
}
