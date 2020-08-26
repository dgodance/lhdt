package lhdt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConverterAttribute implements Serializable {

    private static final long serialVersionUID = 4038799118656987546L;

    private ConverterJob converterJob;
    private List<Map<String, Object>> attributes;   // 3D 데이터 속성

}
