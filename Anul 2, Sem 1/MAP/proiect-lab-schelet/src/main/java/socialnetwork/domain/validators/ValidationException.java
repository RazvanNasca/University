package socialnetwork.domain.validators;

public class ValidationException extends RuntimeException {
    public ValidationException() {
    }

    private String message;

    public ValidationException(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
