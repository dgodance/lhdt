package lhdt.svc.cityplanning.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpDownType {
    UP(0),
    DOWN(1);

    private Integer code;
}
