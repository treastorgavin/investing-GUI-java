package ePortfolio;

/**
 * This class stores general attributes for an investment, and provides accessors and
 * mutators for all of the attributes
 * @author Gavin Rotsaert-Smith
 */
public abstract class Investment {
    protected String symbol;
    protected String name;
    protected int quantity;
    protected Double price;
    protected double bookValue;
    /** Updated everytime getGain is called in main */
    protected double gain;
    // fee depends on the type of investment
    protected double fee;
    // METHODS

    /** Empty constructor */
    public Investment(){
    }

    /** Constructor with 4 parameters for symbol, name, quantity and price
     * @param symbol string is set to symbol
     * @param name string is set to name
     * @param quantity is set as the int quantity
     * @param price is a double set to price
     * The book value and gain are initialized to 0 for a new stock
     * the book value is set in main
     */
    public Investment(String symbol, String name, int quantity, double price){
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = 0.0;
        this.gain = 0.0;
    }

    /** Returns the symbol stored in the investment object
     * @return symbol attribute of the investment obj
     */
    public String getSymbol(){
        return this.symbol;
    }
    /** Returns the name stored in the investment object
     * @return name attribute of the investment obj
     */
    public String getName(){
        return this.name;
    }
    /** Returns the quantity stored in the investment object
     * @return quantity attribute of the investment obj
     */
    public int getQuantity(){
        return this.quantity;
    }
    /** Returns the price stored in the investment object
     * @return price attribute of the investment obj
     */
    public Double getPrice(){
        return this.price;
    }
    /** Returns the book value stored in the investment object
     * @return book value attribute of the mutuinvestmental obj
     */
    public double getBookValue(){
        return this.bookValue;
    }
    /** Returns the gain stored in the investment object
     * @return gain attribute of the investment obj
     */
    public double getGain(){
        return this.gain;
    }

    // SETTERS
    /**
     * Sets the symbol based on the input symbol
     * @param symbol is the symbol to set
     * @return false if symbol is null, true otherwise
     * @throws InvalidSymbolException is symbol is null or empty
     */
    public boolean setSymbol(String symbol) throws InvalidSymbolException{
        if(symbol == null){
            return false;
        }
        if(symbol == null || symbol.isEmpty()){
            throw new InvalidSymbolException();
        }
        this.symbol = symbol;
        return true;
    }
    
    /**
     * Sets the name based on the input symbol
     * @param name is the name to set
     * @return false if name == null true otherwise
     * @throws InvalidNameException if name == null or isEmpty
     */
    public boolean setName(String name) throws InvalidNameException{
        if(name == null){
            return false;
        }
        if(name == null || name.isEmpty()){
            throw new InvalidNameException();
        }
        this.name = name;
        return true;
    }

    /**
     * Sets the price based on the input price
     * @param price is the price to set
     * @return true if price greater than 0
     * @throws InvalidPriceException if price is less than 0
     */
    public boolean setPrice(double price) throws InvalidPriceException{
        if(price < 0){
            throw new InvalidPriceException();
        }
        this.price = price;
        return true;
    }

    /**
     * Sets the quantity of the object with the newquantity
     * @param quantity is the quantity to be set
     * @return true if quantity is greater than 0
     * @throws InvalidQuantityException if quantity is less than 0
     */
    public boolean setQuantity(int quantity) throws InvalidQuantityException{
        if(quantity < 0){
            throw new InvalidQuantityException();
        }
        this.quantity = quantity;
        return true;
    }

    /** sets the bookvalue depending on the type of investment
     * @param price is the price of investment
     * @param quantity is the quantity of investment
     */
    public void setBookValue(double price, int quantity){

        this.bookValue = (price * quantity) + fee;
        
    }

    public void setBookValueFile(double bookValue){
        this.bookValue = bookValue;
        
    }

     /** Sets the gain based on the the new price
     * @param newPrice is the new price
     * the formula is gain = (quantity*price - fee) - BookVal
     */
    public void setGain(double newPrice){
        this.gain = ((this.quantity * newPrice) - fee) - this.bookValue;
    }

    /** This calculates the payment received after selling, the fee depends on the type of investment
     * @param quantity is the quantity that is being sold
     * @return the payment recieved based on the amount sold and the current price
     */
    public double paymentReceived(int quantity){
        return (quantity * this.price - fee);
    }

     /** This updates the book value if a investment is sold
     * @param oldQuantity is the old quantity of the investment
     * it then calculates a ratio of the old and new quantity and then multiplies the book value by that ratio
     */
    public void updateBookValue(int oldQuantity){
        double Q = this.quantity;
        double oQ = oldQuantity;
        double ratio = Q/oQ;
        this.bookValue = this.bookValue * ratio;
    }

    /** Matching price range matches an exact price, lower bound and/or upper bound price
     * @param lowerPrice is the lower bound search, if the price in this stock is higher than it then it returns true
     * @param upperPrice is the upper range and the price in the stock must be equal to or lower than it
     * @return true if the price of the investment falls within the search values
     */
    public boolean matchingPriceRange(double lowerPrice, double upperPrice){
       return this.price >= lowerPrice && this.price <= upperPrice;
    }


    /** This function will write to a file in a given format
     * @return the format of the file
     */
    public String toFile(){
        return symbol + "\n" + name + "\n" + quantity + "\n" + price + "\n" + bookValue + "\n"; 
    }
}
