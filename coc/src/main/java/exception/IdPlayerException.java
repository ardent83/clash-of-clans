package exception;

public class IdPlayerException extends Exception{
    public IdPlayerException() {
        super("The username is already available!");
    }
}
