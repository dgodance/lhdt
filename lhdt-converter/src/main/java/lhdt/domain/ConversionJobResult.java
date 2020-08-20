package lhdt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversionJobResult implements Serializable {

    private static final long serialVersionUID = 3378990302698130808L;

    private boolean bGeoReferenced;     // 지리참조 여부
    private float[] bbox;               // bGeoReferenced가 false일때, bbox
    private String startTime;           // 시작시각
    private String endTime;             // 종료시각
    private String fileName;            // 파일명
    private String fullPath;            // 파일전체 경로
    private String message;             // 실패 시 메세지
    private String resultStatus;        // 성공여부 (success, failure)

}
