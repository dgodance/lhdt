package lhdt.admin.svc.file.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 파일 확장 타입 대한 종류를 열거
 */
@Getter
@AllArgsConstructor
public enum FileClsType {
    SHP(0);
    private Integer code;
}

