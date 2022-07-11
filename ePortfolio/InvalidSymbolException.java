package ePortfolio;

/**
 * This class throws an invalid symbol exception which extends the exception class
 */
public class InvalidSymbolException extends Exception{
    
    public InvalidSymbolException(){
        super("Invalid Symbol.");
    }

    public InvalidSymbolException(String e, Throwable err){
        super(e, err);
    }
    
}
