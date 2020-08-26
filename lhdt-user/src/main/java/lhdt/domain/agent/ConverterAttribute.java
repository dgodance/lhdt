package lhdt.domain.agent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lhdt.domain.converter.ConverterJob;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConverterAttribute implements Serializable {

    private static final long serialVersionUID = 4038799118656987546L;

    private ConverterJob converterJob;

    // 3D 데이터 속성
    private String attributes;

}
