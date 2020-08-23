package lhdt.admin.svc.cityplanning.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NotSupportCsvFileException extends RuntimeException {
    private String fullPath;
    private String errorArrayString;
    private Integer arrayLength;
    public NotSupportCsvFileException(String fullPath, String errorArrayString, Integer arrayLength) {
        this.fullPath = fullPath;
        this.errorArrayString = errorArrayString;
        this.arrayLength = arrayLength;
    }
    public NotSupportCsvFileException(String e) {
        super(e);
    }
}
