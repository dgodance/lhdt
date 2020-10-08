package lhdt.admin.svc.file.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 파일 사용 종류에 대한 타입 기술
 */
@Getter
@AllArgsConstructor
public enum FileType {
    지구계획보고서(0),
    경관이미지(1);
    private Integer code;
}
