package lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LSDiffGroupTable {
    private LSDiffGroupMeta meta;
    private List<LSDiffGroupTableDefault> data = new ArrayList<>();
}