package exception;

public class VMNotRunningException extends RuntimeException {

    public VMNotRunningException(String message) {
        super(message);
    }
}