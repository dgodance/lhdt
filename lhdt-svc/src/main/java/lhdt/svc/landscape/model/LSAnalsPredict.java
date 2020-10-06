package lhdt.svc.landscape.model;

import lhdt.svc.landscape.types.LSAnalsPredictType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 경관분석 예측 데이터 구조 정의 클래스
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LSAnalsPredict {
    /**
     * 출력 이미지
     */
    private String output_img;
    /**
     * 선 두깨
     */
    private Long shielding_rate;
    /**
     * 예측 종류
     */
    private LSAnalsPredictType lsAnalsPredictType;
}
