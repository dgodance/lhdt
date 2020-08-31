package lhdt.admin.svc.landscape.domain.landScapeAnalsDTO;

import lhdt.admin.svc.landscape.domain.LandScapePoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandScapeAnalsTable extends LandScapePoint {
    private boolean viewAction;
    private boolean analsAction;
}
