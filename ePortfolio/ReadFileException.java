package ePortfolio;

/**
 * This class throws a read file name exception which extends the exception class
 * @author Gavin Rotsaert-Smith
 */
public class ReadFileException extends Exception{

    public ReadFileException(){
        super("Cannot read file.\n");
    }

    public ReadFileException(String e, Throwable err){
        super(e, err);
    }

}
