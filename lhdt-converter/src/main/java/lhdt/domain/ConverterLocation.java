package lhdt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConverterLocation implements Serializable {

    private static final long serialVersionUID = -3475237723073093024L;

    private ConverterJob converterJob;
    private String data_key;
    private double latitude;
    private double longitude;

}
