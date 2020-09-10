package lhdt.svc.landscape.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LSAnalsPredictType {
    스카이라인 (0),
    조망차폐 (1);
    private Integer code;
}
