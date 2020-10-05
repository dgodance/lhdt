package lhdt.svc.landscape.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 경관점 등록 종류 구분
 */
@Getter
@AllArgsConstructor
public enum LandScapeAnalsType {
    점 (0),
    선 (1);
    private Integer code;
}
