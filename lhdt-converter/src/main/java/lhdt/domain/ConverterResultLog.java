package lhdt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConverterResultLog implements Serializable {

    private static final long serialVersionUID = -5428363369463462634L;

    private ConverterJob converterJob;
    private List<ConversionJobResult> conversionJobResult;  // ConverterJob 변환결과
    private String startTime;   // 시작시각
    private String endTime;     // 종료시각
    private String failureLog;  // 실패로그
    private boolean isSuccess;  // 성공여부
    private int numberOfFilesConverted;     // 변환된 파일갯수
    private int numberOfFilesToBeConverted; // 변환되어야할 파일갯수

}
