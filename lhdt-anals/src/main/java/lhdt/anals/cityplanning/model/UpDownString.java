package lhdt.anals.cityplanning.model;

import lhdt.anals.cityplanning.types.UpDownType;
import lombok.*;

@Builder
@Data
public class UpDownString {
    private Integer ratio;
    private UpDownType udt;
}
