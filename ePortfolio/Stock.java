package ePortfolio;
/**
 * This class stores attributes for a stock, and provides accessors and
 * mutators for all of the attributes, as well as toString to print the class
 * @author Gavin Rotsaert-Smith
 */
public class Stock extends Investment{

    /** The constructor creates a stock object
     * the fee is 9.99
     * @param symbol string is set to symbol
     * @param name string is set to name
     * @param quantity is set as the int quantity
     * @param price is a double set to price
     * The book value and gain are initialized to 0 for a new stock
     * the book value is set in main
     */
    public Stock(String symbol, String name, int quantity, double price) {
       super(symbol, name, quantity, price);
       this.fee = 9.99;
    }

    /** Empty constructor */
    public Stock(){
    }

    @Override
    public String toString(){
        return symbol +"\n"+ name +"\nQuantity: "+ quantity +" shares\nPrice: $"+ price +"\nBook Value: $"+ bookValue;
    }

    @Override
    public String toFile(){
        return "stock" + "\n" + symbol + "\n" + name + "\n" + quantity + "\n" + price + "\n" + bookValue; 
    }

}