package ePortfolio;

/**
 * This class throws a no file name exception which extends the exception class
 */
public class NoFileNameException extends Exception{
    
    public NoFileNameException(){
        super("Expected filename.\n");
    }

    public NoFileNameException(String e){
        super(e);
    }

}
