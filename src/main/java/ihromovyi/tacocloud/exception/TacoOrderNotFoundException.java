package ihromovyi.tacocloud.exception;

public class TacoOrderNotFoundException extends RuntimeException {
    public TacoOrderNotFoundException(String message) {
        super(message);
    }
}
