package lhdt.admin.svc.landscape.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LSPointActionType {
    EDIT (0),
    REGIST (1),
    CONTENT (2);
    private Integer code;
}
