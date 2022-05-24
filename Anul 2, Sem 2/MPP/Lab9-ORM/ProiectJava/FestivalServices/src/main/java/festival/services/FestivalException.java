package festival.services;

public class FestivalException extends Exception{

    public FestivalException(){}

    public FestivalException(String message) { super(message);}

    public FestivalException(String message, Throwable cause) {
        super(message, cause);
    }

}
