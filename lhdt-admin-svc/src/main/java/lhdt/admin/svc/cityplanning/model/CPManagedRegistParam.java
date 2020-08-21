package lhdt.admin.svc.cityplanning.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CPManagedRegistParam {
    private Long localInfoId;
    private String localInfoName;
    private Long districId;
    private String districName;
}
