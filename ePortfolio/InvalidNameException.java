package ePortfolio;

/**
 * This class throws an invalid name exception which extends the exception class
 * @author Gavin Rotsaert-Smith
 */
public class InvalidNameException extends Exception{
    public InvalidNameException(){
        super("Invalid Name.");
    }

    public InvalidNameException(String e){
        super(e);
    }
}
