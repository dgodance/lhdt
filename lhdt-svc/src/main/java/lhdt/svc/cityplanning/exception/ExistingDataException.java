package lhdt.svc.cityplanning.exception;

public class ExistingDataException extends RuntimeException {
    public ExistingDataException(){
        super("already list in regist data");
    }
}
