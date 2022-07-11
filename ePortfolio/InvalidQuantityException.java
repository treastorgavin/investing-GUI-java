package ePortfolio;

/**
 * This class throws an invalid quantity exception which extends the exception class
 */
public class InvalidQuantityException extends Exception{
    
    public InvalidQuantityException(){
        super("Invalid quantity.");
    }

    public InvalidQuantityException(String e, Throwable err){
        super(e, err);
    }

}
