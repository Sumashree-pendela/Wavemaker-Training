package todo.exception;

public class LoggedInUserNotFoundException extends Exception{
    public LoggedInUserNotFoundException(String message) {
        super(message);
    }
}
