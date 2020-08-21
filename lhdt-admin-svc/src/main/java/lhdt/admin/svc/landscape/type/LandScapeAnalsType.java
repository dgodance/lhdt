package lhdt.admin.svc.landscape.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LandScapeAnalsType {
    점 (0),
    선 (1);
    private Integer code;
}
