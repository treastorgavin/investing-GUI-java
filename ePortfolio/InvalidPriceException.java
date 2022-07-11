package ePortfolio;

/**
 * This class throws an invalid price exception which extends the exception class
 */
public class InvalidPriceException extends Exception {
    
    public InvalidPriceException(){
        super("Invalid Price.");
    }

    public InvalidPriceException(String e, Throwable err){
        super(e, err);
    }
}
