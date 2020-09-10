package lhdt.admin.svc.cityplanning.model;


import lhdt.admin.svc.cityplanning.type.UpDownType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpDownString {
    private Integer ratio;
    private UpDownType udt;
}
