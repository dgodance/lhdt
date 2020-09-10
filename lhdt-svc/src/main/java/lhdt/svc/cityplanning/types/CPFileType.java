package lhdt.svc.cityplanning.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CPFileType {
    지구계획보고서(0);
    private Integer code;
}
