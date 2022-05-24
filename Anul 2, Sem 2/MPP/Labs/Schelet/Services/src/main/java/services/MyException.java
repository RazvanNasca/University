package services;

public class MyException extends Exception{

    public MyException(){}

    public MyException(String message) { super(message);}

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

}