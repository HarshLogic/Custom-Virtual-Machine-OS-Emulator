package memory;

public class PageFaultException extends RuntimeException {
    public PageFaultException(String message) {
        super(message);
    }
}