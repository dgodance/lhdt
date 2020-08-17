package lhdt.svc.cityplanning.model;

import lhdt.svc.cityplanning.types.UpDownType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpDownString {
    private Integer ratio;
    private UpDownType udt;
}
