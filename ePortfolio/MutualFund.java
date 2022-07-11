package ePortfolio;
/**
 * This class stores attributes for a mutual fund, and provides accessors and
 * mutators for all of the attributes, as well as toString to print the class
 * @author Gavin Rotsaert-Smith
 */
public class MutualFund extends Investment{

    /** The constructor creates a mutual fund object
     * the fee is 9.99
     * @param symbol string is set to symbol
     * @param name string is set to name
     * @param quantity is set as the int quantity
     * @param price is a double set to price
     * The book value and gain are initialized to 0 for a new stock
     * the book value is set in main
     * the fee is 45
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        super(symbol,name,quantity,price);
        this.fee = 45;
    }

    /** Empty constructor */
    public MutualFund(){
    }

    //toString
    public String toString(){
        return symbol +"\n"+ name +"\nQuantity: "+ quantity +" funds\nPrice: $"+ price +"\nBook Value: $"+ bookValue;
    }

    @Override
    public String toFile(){
        return "mutualfund" + "\n" + symbol + "\n" + name + "\n" + quantity + "\n" + price + "\n" + bookValue; 
    }

}
