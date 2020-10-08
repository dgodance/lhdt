package lhdt.admin.svc.landscape.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 경관점 사용자 액션에 대하여 정의 합니다
 */
@Getter
@AllArgsConstructor
public enum LSPointActionType {
    EDIT (0),
    REGIST (1),
    CONTENT (2);
    private Integer code;
}
