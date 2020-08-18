package lhdt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConverterLocation {
    private String data_key;
    private double latitude;
    private double longitude;
}
