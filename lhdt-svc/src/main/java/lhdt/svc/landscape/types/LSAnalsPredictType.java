package lhdt.svc.landscape.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 경관점 분석 예측 종류에 대하여 구분
 */
@Getter
@AllArgsConstructor
public enum LSAnalsPredictType {
    스카이라인 (0),
    조망차폐 (1);
    private Integer code;
}
