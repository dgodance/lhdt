package lhdt.admin.svc.file.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    지구계획보고서(0),
    경관이미지(1);
    private Integer code;
}
