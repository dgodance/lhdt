package lhdt.admin.svc.landscape.domain.LandScapeDiffGroupDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LSDiffGroupTableDefault {
    private Long Id;
    private String lsDiffGrupName;
    private Date registDt;
    private Date updtDt;
    private String modifiedAction;
    private String deleteAction;
}
