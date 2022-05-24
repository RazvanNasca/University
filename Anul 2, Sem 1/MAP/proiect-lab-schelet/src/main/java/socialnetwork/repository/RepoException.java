package socialnetwork.repository;

public class RepoException extends RuntimeException {

    public RepoException(){}

    private String message;

    public String getMessage(){return message;}

    public RepoException(String message){this.message = message;}

    public RepoException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepoException(Throwable cause) {
        super(cause);
    }

    public RepoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
