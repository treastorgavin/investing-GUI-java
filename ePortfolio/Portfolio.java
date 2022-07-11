package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** This class implements portfolio that holds stocks and mutual funds in array lists.
 * you to buy or sell investments, search for investments, update prices, and 
 * compute the total gain of the portfolio. 
 * @author Gavin Rotsaert-Smith */
public class Portfolio extends JFrame{

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    /** maintaining the stocks */
    private ArrayList<Investment> investmentList;// new ArrayList<Investment>();
    private HashMap<String,ArrayList<Integer>> keywordMap;

    private String FILE_NAME;

    private static String[] types = {"Stock","Mutual Fund"};
    //Set up the GUI panels, text fields, areas for each interface

    //welcome
    JPanel options;
    JPanel welcomePanel;

    //buy
    JPanel buyPanel;
    JTextField buySymbolField;
    JTextField buyNameField;
    JTextField buyQuantityField;
    JTextField buyPriceField;
    JTextArea buyArea;
    JComboBox<String> buyInvTypeComboBox;

    //sell
    JPanel sellPanel;
    JTextField sellSymbolField;
    JTextField sellQuantityField;
    JTextField sellPriceField;
    JTextArea sellArea;

    //update
    JPanel updatePanel;
    JTextField updateSymbolField;
    JTextField updateNameField;
    JTextField updatePriceField;
    JTextArea updateArea;

    //getGain
    JPanel gainPanel;
    JTextField gainField;
    JTextArea gainArea;

    //search
    JPanel searchPanel;
    JTextField searchSymbolField;
    JTextField searchNameField;
    JTextField searchLPriceField;
    JTextField searchHPriceField;
    JTextArea searchArea;

    /** Constructor that creates two array lists for stocks and mutual funds, and GUI */
    public Portfolio(){
        this.investmentList = new ArrayList<>();
        this.keywordMap = new HashMap<>();

        prepareMenuGUI();
        prepareWelcomGUI();
        prepareBuyGUI();
        prepareSellGUI();
        prepareUpdateGUI();
        prepareGainGUI();
        prepareSearchGUI();
        prepareOptionGUI();

    }

    //menu button listeners
    /**
     * This implements an ActionListener for the drop down menu.
     * Whatever command is selected will be set visible and initialize the fields, while setting the other panels to not visible.
     * Upon clicking the Quit option the program will save investments to a file FILE_NAME
     */
    private class MenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();

            if(cmd.equals("Buy")){

                welcomePanel.setVisible(false);
                buyPanel.setVisible(true);
                sellPanel.setVisible(false);
                updatePanel.setVisible(false);
                gainPanel.setVisible(false);
                searchPanel.setVisible(false);
                
                buyInvTypeComboBox.setSelectedItem("stock");
                buySymbolField.setText("");
                buyNameField.setText("");
                buyQuantityField.setText("");
                buyPriceField.setText("");
                buyArea.setText("");

            }else if(cmd.equals("Sell")){

                welcomePanel.setVisible(false);
                buyPanel.setVisible(false);
                sellPanel.setVisible(true);
                updatePanel.setVisible(false);
                gainPanel.setVisible(false);
                searchPanel.setVisible(false);
                
                sellSymbolField.setText("");
                sellQuantityField.setText("");
                sellPriceField.setText("");
                sellArea.setText("");

            }else if(cmd.equals("Update")){

                welcomePanel.setVisible(false);
                buyPanel.setVisible(false);
                sellPanel.setVisible(false);
                updatePanel.setVisible(true);
                gainPanel.setVisible(false);
                searchPanel.setVisible(false);
                
                updateSymbolField.setText("");
                updateNameField.setText("");
                updatePriceField.setText("");
                updateArea.setText("");
                
                setUpdate();

            }else if(cmd.equals("Get Gain")){
                welcomePanel.setVisible(false);
                buyPanel.setVisible(false);
                sellPanel.setVisible(false);
                updatePanel.setVisible(false);
                gainPanel.setVisible(true);
                searchPanel.setVisible(false);
                
                gainField.setText("");
                gainArea.setText("");
                
                computeTotalGain();

            }else if(cmd.equals("Search")){

                welcomePanel.setVisible(false);
                buyPanel.setVisible(false);
                sellPanel.setVisible(false);
                updatePanel.setVisible(false);
                gainPanel.setVisible(false);
                searchPanel.setVisible(true);
                
                searchSymbolField.setText("");
                searchNameField.setText("");
                searchLPriceField.setText("");
                searchHPriceField.setText("");
                searchArea.setText("");

            }else if(cmd.equals("Quit")){

                try {
                    saveInvestments(FILE_NAME);
                }
                catch(IOException a) {
                    System.out.println("Error writing to file.");
                }
                System.exit(0);

            }
            
        }

    }

    /**
     * This ButtonListener class implements ActionListener
     * This class is for internal buttons: Buy, Sell, Reset, Save, Search...
     */
    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            String cmd = ae.getActionCommand();
            if(cmd.equals("Buy")){
              buyInvestment();
            }else if(cmd.equals("Reset")){
                buySymbolField.setText("");
                buyNameField.setText("");
                buyQuantityField.setText("");
                buyPriceField.setText("");
                buyArea.setText("");
            }

            if(cmd.equals("Sell")){
                sellInvestment();
            }else if(cmd.equals("Reset")){
                sellSymbolField.setText("");
                sellQuantityField.setText("");
                sellPriceField.setText("");
                sellArea.setText("");
            }

            //update
            if(cmd.equals("Save")){

                update(symbolIndex(updateSymbolField.getText()));
                
            }else if(cmd.equals("Next")){

               int idx = symbolIndex(updateSymbolField.getText());

                if (idx == investmentList.size() -1){
                    return;
                }else{
                    idx++;

                    updateSymbolField.setText(investmentList.get(idx).getSymbol());
                    updateNameField.setText(investmentList.get(idx).getName());
                    updatePriceField.setText("");
                    updateArea.setText("");
                }
                
            }else if(cmd.equals("Prev")){

                int idx = symbolIndex(updateSymbolField.getText());

                if (idx == 0){
                    return;
                }else{
                    idx--;

                    updateSymbolField.setText(investmentList.get(idx).getSymbol());
                    updateNameField.setText(investmentList.get(idx).getName());
                    updatePriceField.setText("");
                    updateArea.setText("");
                }
            }

            if(cmd.equals("Get Gain")){
                computeTotalGain();
            }

            if(cmd.equals("Search")){
                search();
            }else if(cmd.equals("Reset")){
                searchSymbolField.setText("");
                searchNameField.setText("");
                searchLPriceField.setText("");
                searchHPriceField.setText("");
                searchArea.setText("");
            }
        }
    }

    /**
     * prepareMenuGUI sets up the menuBar (drop down menu) and assigns action listeners to each option (MenuListeners)
    */
    private void prepareMenuGUI(){
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //option menu
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Commands");
        JMenuItem buyOption = new JMenuItem("Buy");
        JMenuItem sellOption = new JMenuItem("Sell");
        JMenuItem updateOption = new JMenuItem("Update");
        JMenuItem gainOption = new JMenuItem("Get Gain");
        JMenuItem searchOption = new JMenuItem("Search");
        JMenuItem quitOption = new JMenuItem("Quit");

        //menu listeners to display the correct panel based on which option was pressed

        MenuListener e = new MenuListener();

        buyOption.addActionListener(e);
        sellOption.addActionListener(e);
        updateOption.addActionListener(e);
        gainOption.addActionListener(e);
        searchOption.addActionListener(e);
        quitOption.addActionListener(e);

        //adding to menu
        optionsMenu.add(buyOption);
        optionsMenu.add(sellOption);
        optionsMenu.add(updateOption);
        optionsMenu.add(gainOption);
        optionsMenu.add(searchOption);
        optionsMenu.add(quitOption);

        //MAIN
        //add to menuBar
        menuBar.add(optionsMenu);

        setJMenuBar(menuBar);
    }
    /**
     * prepareWelcomGUI sets up the first panel with a welcome message
    */
    private void prepareWelcomGUI(){
        //welcome panel
        welcomePanel = new JPanel();

        welcomePanel.setLayout(new GridLayout(5,1));
        JLabel welcomeLabel = new JLabel("<html>Welcome to ePortfolio. Choose a command from the \"Commands\" menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.<html>");

        //for spacing
        welcomePanel.add(new JLabel(""));
        welcomePanel.add(welcomeLabel);
    }
    /**
     * prepareBuyGUI sets up the buy panel, adds buttons, text fields and message area appropriatly
    */
    private void prepareBuyGUI(){
        //BUYING
        //buy panel
        buyPanel = new JPanel();

        buyPanel.setLayout(new BorderLayout());
        JPanel buymainPanel = new JPanel();
        buymainPanel.setLayout(new GridLayout(6,1));

        buymainPanel.add(new JLabel("Buying an investment"));

        //picking investment type
        JPanel buyInvTypePanel = new JPanel();
        buyInvTypePanel.setLayout(new GridLayout(1,2));
        buyInvTypePanel.add(new JLabel("Type"));

        buyInvTypeComboBox = new JComboBox<>(types);
        buyInvTypePanel.add(buyInvTypeComboBox);
        buymainPanel.add(buyInvTypePanel);

        //symbol
        JPanel buySymbolPanel = new JPanel();
        buySymbolPanel.setLayout(new GridLayout(1,2));
        buySymbolPanel.add(new JLabel("Symbol"));
        buySymbolField = new JTextField(5);
        buySymbolPanel.add(buySymbolField);
        buymainPanel.add(buySymbolPanel);

        //name
        JPanel buyNamePanel = new JPanel();
        buyNamePanel.setLayout(new GridLayout(1,2));
        buyNamePanel.add(new JLabel("Name"));
        buyNameField = new JTextField(5);
        buyNamePanel.add(buyNameField);
        buymainPanel.add(buyNamePanel);

        //quantity
        JPanel buyQuantityPanel = new JPanel();
        buyQuantityPanel.setLayout(new GridLayout(1,2));
        buyQuantityPanel.add(new JLabel("Quantity"));
        buyQuantityField = new JTextField(5);
        buyQuantityPanel.add(buyQuantityField);
        buymainPanel.add(buyQuantityPanel);

        //price
        JPanel buyPricePanel = new JPanel();
        buyPricePanel.setLayout(new GridLayout(1,2));
        buyPricePanel.add(new JLabel("Price"));
        buyPriceField = new JTextField(5);
        buyPricePanel.add(buyPriceField);
        buymainPanel.add(buyPricePanel);

        buyPanel.add(buymainPanel, BorderLayout.CENTER);

        //action listener for actions

        ButtonListener ae = new ButtonListener();

        //buy button
        //buttons on right side (East)
        JPanel buyRightPanel = new JPanel();
        buyRightPanel.setLayout(new GridLayout (3,1));
        JPanel subbuyrightPanel = new JPanel();
        subbuyrightPanel.setLayout(new BoxLayout(subbuyrightPanel, BoxLayout.Y_AXIS));

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(ae);

        subbuyrightPanel.add(buyButton);

        //reset button

        JButton buyResetButton = new JButton("Reset");
        buyResetButton.addActionListener(ae);
        subbuyrightPanel.add(buyResetButton);

        //for button spacing
        buyRightPanel.add(new JLabel(""));
        buyRightPanel.add(subbuyrightPanel);

        buyPanel.add(buyRightPanel,BorderLayout.EAST);

        //scrolling area
        JPanel buyBottomPanel = new JPanel();

        buyArea = new JTextArea(7,40);
        buyArea.setEditable(false);

        JScrollPane buyAreaScroll = new JScrollPane(buyArea);

        buyAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        buyAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        buyBottomPanel.setLayout(new BoxLayout(buyBottomPanel, BoxLayout.Y_AXIS));
        buyBottomPanel.add(new JLabel("Messages"));
        buyBottomPanel.add(buyAreaScroll);

        buyPanel.add(buyBottomPanel, BorderLayout.SOUTH);
    }
    /**
     * prepareSellGUI sets up the sell panel, adds buttons, text fields and message area appropriatly
    */
    private void prepareSellGUI(){
        //SELLING
        sellPanel = new JPanel();
        ButtonListener ae = new ButtonListener();

        sellPanel.setLayout(new BorderLayout());
        JPanel sellmainPanel = new JPanel();
        sellmainPanel.setLayout(new GridLayout(4,1));

        sellmainPanel.add(new JLabel("Selling an investment"));

        //symbol
        JPanel sellSymbolPanel = new JPanel();
        sellSymbolPanel.setLayout(new GridLayout(1,2));
        sellSymbolPanel.add(new JLabel("Symbol"));
        sellSymbolField = new JTextField(5);
        sellSymbolPanel.add(sellSymbolField);
        sellmainPanel.add(sellSymbolPanel);

        //quantity
        JPanel sellQuantityPanel = new JPanel();
        sellQuantityPanel.setLayout(new GridLayout(1,2));
        sellQuantityPanel.add(new JLabel("Quantity"));
        sellQuantityField = new JTextField(5);
        sellQuantityPanel.add(sellQuantityField);
        sellmainPanel.add(sellQuantityPanel);

        //price
        JPanel sellPricePanel = new JPanel();
        sellPricePanel.setLayout(new GridLayout(1,2));
        sellPricePanel.add(new JLabel("Price"));
        sellPriceField = new JTextField(5);
        sellPricePanel.add(sellPriceField);
        sellmainPanel.add(sellPricePanel);

        sellPanel.add(sellmainPanel, BorderLayout.CENTER);

        //sell button
        //buttons on right side (East)
        JPanel sellRightPanel = new JPanel();
        sellRightPanel.setLayout(new GridLayout (3,1));
        JPanel subsellRightPanel = new JPanel();
        subsellRightPanel.setLayout(new BoxLayout(subsellRightPanel, BoxLayout.Y_AXIS));

        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(ae);

        subsellRightPanel.add(sellButton);

        //reset button

        JButton sellResetButton = new JButton("Reset");
        sellResetButton.addActionListener(ae);

        subsellRightPanel.add(sellResetButton);

        //for button spacing
        sellRightPanel.add(new JLabel(""));
        sellRightPanel.add(subsellRightPanel);

        sellPanel.add(sellRightPanel,BorderLayout.EAST);

        //scrolling area
        JPanel sellBottomPanel = new JPanel();

        sellArea = new JTextArea(7,40);
        sellArea.setEditable(false);

        JScrollPane sellAreaScroll = new JScrollPane(sellArea);

        sellAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sellAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        sellBottomPanel.setLayout(new BoxLayout(sellBottomPanel, BoxLayout.Y_AXIS));
        sellBottomPanel.add(new JLabel("Messages"));
        sellBottomPanel.add(sellAreaScroll);

        sellPanel.add(sellBottomPanel, BorderLayout.SOUTH);
    }
    /**
     * prepareUpdateGUI sets up the update panel, adds buttons, text fields and message area appropriatly
    */
    private void prepareUpdateGUI(){
        ButtonListener ae = new ButtonListener();
        updatePanel = new JPanel();
        updatePanel.setLayout(new BorderLayout());

        JPanel updatemainPanel = new JPanel();
        updatemainPanel.setLayout(new GridLayout(4,1));

        updatemainPanel.add(new JLabel("Updating investments"));

        //symbol
        JPanel updateSymbolPanel = new JPanel();
        updateSymbolPanel.setLayout(new GridLayout(1,2));
        updateSymbolPanel.add(new JLabel("Symbol"));
        updateSymbolField = new JTextField(10);
        updateSymbolField.setEditable(false);
        updateSymbolField.setBackground(Color.LIGHT_GRAY); //to show uneditable area
        updateSymbolPanel.add(updateSymbolField);
        updatemainPanel.add(updateSymbolPanel);

        //name
        JPanel updateNamePanel = new JPanel();
        updateNamePanel.setLayout(new GridLayout(1,2));
        updateNamePanel.add(new JLabel("Name"));
        updateNameField = new JTextField(15);
        updateNameField.setEditable(false);
        updateNameField.setBackground(Color.LIGHT_GRAY); //to show uneditable area
        updateNamePanel.add(updateNameField);
        updatemainPanel.add(updateNamePanel);

        //price
        JPanel updatePricePanel = new JPanel();
        updatePricePanel.setLayout(new GridLayout(1,2));
        updatePricePanel.add(new JLabel("Price"));
        updatePriceField = new JTextField(5);
        updatePricePanel.add(updatePriceField);
        updatemainPanel.add(updatePricePanel);

        updatePanel.add(updatemainPanel, BorderLayout.CENTER);

        JPanel updateRightPanel = new JPanel();
        updateRightPanel.setLayout(new GridLayout(2,1));
        JPanel updatesubRightPanel = new JPanel();
        updatesubRightPanel.setLayout(new BoxLayout(updatesubRightPanel, BoxLayout.Y_AXIS));
        
        JButton updatePrevButton = new JButton("Prev");
        updatePrevButton.addActionListener(ae);
        updatesubRightPanel.add(updatePrevButton);

        JButton updateNextButton = new JButton("Next");
        updateNextButton.addActionListener(ae);
        updatesubRightPanel.add(updateNextButton);

        JButton updateSaveButton = new JButton("Save");
        updateSaveButton.addActionListener(ae);
        updatesubRightPanel.add(updateSaveButton);

        //for button spacing
        updateRightPanel.add(new JLabel(""));
        updateRightPanel.add(updatesubRightPanel);

        updateRightPanel.add(updatesubRightPanel);

        updatePanel.add(updateRightPanel, BorderLayout.EAST);

        //scroll
        JPanel updateBottomPanel = new JPanel();
        
        updateArea = new JTextArea(7,40);
        updateArea.setEditable(false);
        
        JScrollPane updateAreaScroll = new JScrollPane(updateArea);
        
        updateAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        updateAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        updateBottomPanel.setLayout(new BoxLayout(updateBottomPanel, BoxLayout.Y_AXIS));
        updateBottomPanel.add(new JLabel("Messages"));
        updateBottomPanel.add(updateAreaScroll);
        
        updatePanel.add(updateBottomPanel, BorderLayout.SOUTH);
    }
    /** prepareGainGUI sets up the gain panel, adds text fields and message area appropriatly
     * 
    */
    private void prepareGainGUI(){
        // GAIN
        gainPanel = new JPanel();

        gainPanel.setLayout(new BorderLayout());

        JPanel gainMainPanel = new JPanel();
        gainMainPanel.add(new JLabel());
        gainMainPanel.setLayout(new GridLayout(2,1));
        gainMainPanel.add(new JLabel("Getting total gain"));

        JPanel totalGainPanel = new JPanel();
        totalGainPanel.setLayout(new GridLayout(1,2));
        totalGainPanel.add(new JLabel("Total gain"));
        gainField = new JTextField(10);
        gainField.setEditable(false);
        totalGainPanel.add(gainField);
        gainMainPanel.add(totalGainPanel);

        gainPanel.add(totalGainPanel, BorderLayout.NORTH);

        JPanel gainBottomPanel = new JPanel();

        gainBottomPanel.setLayout(new BoxLayout(gainBottomPanel, BoxLayout.Y_AXIS));
        gainBottomPanel.add(new JLabel("Individual gains"));

        gainArea = new JTextArea(7,40);
        gainArea.setEditable(false);

        JScrollPane gainAreaScroll = new JScrollPane(gainArea);

        gainAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        gainAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        gainBottomPanel.add(gainAreaScroll);

        gainPanel.add(gainBottomPanel, BorderLayout.CENTER);
    }
    /** prepareSellGUI sets up the gain panel, adds text fields and message area appropriatly
     * 
    */
    private void prepareSearchGUI(){
        //SEARCH
        ButtonListener ae = new ButtonListener();
        searchPanel = new JPanel();

        searchPanel.setLayout(new BorderLayout());

        JPanel mainSearchPanel = new JPanel();
        mainSearchPanel.setLayout(new GridLayout(5,1));
        mainSearchPanel.add(new JLabel("Searching investments"));

        //symbol
        JPanel searchSymbolPanel = new JPanel();
        searchSymbolPanel.setLayout(new GridLayout(1,2));
        searchSymbolPanel.add(new JLabel("Symbol"));
        searchSymbolField = new JTextField(10);
        searchSymbolPanel.add(searchSymbolField);
        mainSearchPanel.add(searchSymbolPanel);
        
        //name
        JPanel searchNamePanel = new JPanel();
        searchNamePanel.setLayout(new GridLayout(1,2));
        searchNamePanel.add(new JLabel("Name keywords"));
        searchNameField = new JTextField(20);
        searchNamePanel.add(searchNameField);
        mainSearchPanel.add(searchNamePanel);
        
        //low price
        JPanel searchLPricePanel = new JPanel();
        searchLPricePanel.setLayout(new GridLayout(1,2));
        searchLPricePanel.add(new JLabel("Low price"));
        searchLPriceField = new JTextField(5);
        searchLPricePanel.add(searchLPriceField);
        mainSearchPanel.add(searchLPricePanel);
        
        //high price
        JPanel searchHPricePanel = new JPanel();
        searchHPricePanel.setLayout(new GridLayout(1,2));
        searchHPricePanel.add(new JLabel("High price"));
        searchHPriceField = new JTextField(5);
        searchHPricePanel.add(searchHPriceField);
        mainSearchPanel.add(searchHPricePanel);

        searchPanel.add(mainSearchPanel, BorderLayout.CENTER);

        //buttons
        JPanel searchRightPanel = new JPanel();
        searchRightPanel.setLayout(new GridLayout(3,1));

        JPanel searchsubRightPanel = new JPanel();
        searchsubRightPanel.setLayout(new BoxLayout(searchsubRightPanel, BoxLayout.Y_AXIS));

        JButton searchResetButton = new JButton("Reset");
        searchResetButton.addActionListener(ae);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(ae);

        //for spacing
        searchRightPanel.add(new JLabel(""));
        searchRightPanel.add(searchsubRightPanel);

        searchsubRightPanel.add(searchResetButton);
        searchsubRightPanel.add(searchButton);

        searchRightPanel.add(searchsubRightPanel);

        searchPanel.add(searchRightPanel, BorderLayout.EAST);

        //scroll
        JPanel searchBottomPanel = new JPanel();

        searchBottomPanel.setLayout(new BoxLayout(searchBottomPanel, BoxLayout.Y_AXIS));
        searchBottomPanel.add(new JLabel("Search results"));

        searchArea = new JTextArea(7,40);
        searchArea.setEditable(false);

        JScrollPane searchAreaScroll = new JScrollPane(searchArea);

        searchAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        searchAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        searchBottomPanel.add(searchAreaScroll);

        searchPanel.add(searchBottomPanel, BorderLayout.SOUTH);
    }
    /** prepareOptionGUI adds all panels together so that when an option is pressed we can decide to make it visible and others not visible
     * 
    */
    private void prepareOptionGUI(){
        //add all panels to the main panel
        options = new JPanel(new CardLayout());

        options.add(welcomePanel,"Main");
        options.add(buyPanel,"Buy");
        options.add(sellPanel,"Sell");
        options.add(updatePanel,"Update");
        options.add(gainPanel,"Get gain");
        options.add(searchPanel,"Search");

        add(options);

        welcomePanel.setVisible(true);

    }

    /**
     *  Main will set up a new Portfolio class, check if there is a file name
     * if there is a file name (args[0]) then it will check to see if the file exists
     * if the file does not exist it will create a new file with the file name provided
     * if that fails it will throw IOException
     * it will then check if the file is a file and not a directory and throw a NoFileNameException otherwise
     * if it is a readable file it will load the investments in that file (assuming they are formatted properly)
     * finally it will show the GUI
     * @param args command line argument (file name)
     * @throws NoFileNameException exception that checks if a file name is present upon execution
     * @throws IOException thrown if unable to create a new file
     */
    public static void main(String[] args) throws NoFileNameException, IOException{
        
        Portfolio portfolio = new Portfolio();

        //if there is no filename provided
        if(args.length < 1){
            throw new NoFileNameException("No file name");
        }else{
            portfolio.FILE_NAME = args[0];
            File file = new File(portfolio.FILE_NAME);
            //check if the file exists
            if(file.exists()){
                //if the file exists and is the correct type, load the investments from file
                if(file.isFile()){
                    portfolio.loadInvestments(portfolio.FILE_NAME);
                }else{
                    throw new NoFileNameException();
                }
            }else{
                File newFile = new File(portfolio.FILE_NAME);
                try{
                    newFile.createNewFile();
                }catch(IOException e){
                    throw new IOException();
                }
            }
        }
        
        portfolio.setVisible(true);
    
    }
    
    /** *********** BUY ****************
     * Own a new investment or add more quantity to an existing investment
     * User enters the kind of investment (stock or mutualfund) followed by the symbol for investment e.g. stock APPL
     * The symbol allows us to check the system to see if there is a pre-existing investment
     * if there is already an investment with that symbol we get the new price and quantity
     * if there isnt that symbol then we get the input for the name of the stock
     * 
     * buyStock and buyMutualFund execute based on the type inputted in main (stock or mutual fund)
     * 
    */
    public void buyInvestment(){
        boolean found = false;
        int index = 0;

        String symbol = buySymbolField.getText();
        // does the stock already exist check
        for(int i = 0; i < this.investmentList.size();i++){
            Investment temp = investmentList.get(i);
            if(symbol.equals(temp.getSymbol())){
                found = true;
                index = i;
                break;
            }
        }

        if (found){
            //check if it is a mutualfund or stock

            Investment temp  = investmentList.get(index);
            if(buyInvTypeComboBox.getSelectedItem().toString().equals("Stock")){
                Stock s = (Stock) investmentList.get(index);
                // add new price and quantity to the existing stock
                int newQuantity = Integer.valueOf(buyQuantityField.getText());
                
                try{
                    s.setQuantity(newQuantity);
                }catch(InvalidQuantityException e){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                double newPrice = Double.valueOf(buyPriceField.getText());

                try{
                    s.setPrice(newPrice);
                }catch(InvalidPriceException err){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }
                
                int Q = temp.getQuantity();
                Q = Q + newQuantity;

                //set new values
                try{
                    s.setQuantity(Q);
                }catch(InvalidQuantityException e){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                s.setBookValue(newPrice,Q);
                s.setGain(newPrice);

                buyArea.setText("Bought more of:\n"+ s.toString());

            }else if(buyInvTypeComboBox.getSelectedItem().toString().equals("Mutual Fund")){
                
                MutualFund m = (MutualFund) investmentList.get(index);
                // add new price and quantity to the existing stock
                int newQuantity = Integer.valueOf(buyQuantityField.getText());
            
                try{
                    m.setQuantity(newQuantity);
                }catch(InvalidQuantityException err){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                double newPrice = Double.valueOf(buyPriceField.getText());

                try{
                    m.setPrice(newPrice);
                }catch(InvalidPriceException err){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }
                
                int Q = temp.getQuantity();
                Q = Q + newQuantity;

                //set new values
                try{
                    m.setQuantity(Q);
                }catch(InvalidQuantityException err){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                m.setBookValue(newPrice,Q);
                m.setGain(newPrice); 
                buyArea.setText("Bought more of:\n"+ m.toString());
            }
        }else{
            //getting user input from combo box
            String invType = buyInvTypeComboBox.getSelectedItem().toString();

            // check to see whether user wants stock or mutualfund
            if(invType.equals("Stock")){
                Stock stock = new Stock();

                String name = buyNameField.getText();
                try {
                    stock.setSymbol(symbol);
                } catch (InvalidSymbolException err) {
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                try {
                    stock.setName(name);
                } catch (InvalidNameException e) {
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                int quantity = Integer.valueOf(buyQuantityField.getText());
                try {
                    stock.setQuantity(quantity);
                } catch (InvalidQuantityException err) {
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                double price = Double.valueOf(buyPriceField.getText());
                try{
                    stock.setPrice(price);
                }catch(InvalidPriceException err){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                //adding to Array List stockList
                stock.setBookValue(price, quantity);
                stock.setGain(price);
                investmentList.add(stock);
                
                addtoHashMap(name,investmentList.indexOf(stock));
                buyArea.setText("Bought new stock:\n"+stock.toString());
                //for every word in name
                //check if name is in kewords list
                //if it does
                // add index to the hashmap for that word

            }else if(invType.equals("Mutual Fund")){
                MutualFund fund = new MutualFund();

                String name = buyNameField.getText();
                try {
                    fund.setSymbol(symbol);
                } catch (InvalidSymbolException err) {
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                try {
                    fund.setName(name);
                } catch (InvalidNameException err) {
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                int quantity = Integer.valueOf(buyQuantityField.getText());
                try {
                    fund.setQuantity(quantity);
                } catch (InvalidQuantityException err) {
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }

                double price = Double.valueOf(buyPriceField.getText());
                try{
                    fund.setPrice(price);
                }catch(InvalidPriceException err){
                    buyArea.setText("Error, please make sure all fields are filled out with the correct input.\n");
                    return;
                }
                
                //adding to Array List 
                fund.setBookValue(price, quantity);
                fund.setGain(price);
                investmentList.add(fund);
                
                addtoHashMap(name,investmentList.indexOf(fund));
                buyArea.setText("Bought new Mutual fund:\n"+fund.toString());

            }
        }
        buySymbolField.setText("");
        buyNameField.setText("");
        buyQuantityField.setText("");
        buyPriceField.setText("");
    }
   
    /** ********* SELL ***************
     * User needs to provide a symbol, a price and quantity to sell
     * selling is only possible if the investment exists and the available quantity is greater or equal to the requested quantity
     * if the array list is empty for either investment type the program will return back to main
     * if it isnt empty it will prompt for a price and quantity to sell
     * if the remaining quantity is greater than 0 we will also adjust the bookValue otherwise if they sell the entire stock/share we will delete the item from the portfolio
    */
    public void sellInvestment(){
        int count = 0;
        boolean found = false;

        // checking to make sure there are investments in the list to sell
        if(investmentList.isEmpty()){
            sellArea.setText("ERROR: There are no investments to sell.\n");
            return;
        }else{

            String symbol = sellSymbolField.getText();

            boolean check = false;
            for(Investment s:investmentList){
                if(s.getSymbol().equalsIgnoreCase(symbol)){
                    check = true;
                    break;
                }
            }
            if(!check){
                sellArea.setText("ERROR: The "+symbol+" investment does not exist.");
                return;
            }
            
            double price = Double.parseDouble(sellPriceField.getText());

            int quantity = Integer.parseInt(sellQuantityField.getText());

            // finding the index of the investment to sell
            count = 0;
            while (investmentList.size() > count){
                Investment temp = investmentList.get(count);
                if(symbol.equalsIgnoreCase(temp.getSymbol())){
                    found = true;
                    break;
                }else{
                    count++;
                }
            }
            if (found){
                Investment temp  = investmentList.get(count);
                int oldQuantity = temp.getQuantity();

                try {
                    temp.setPrice(price);
                } catch (InvalidPriceException e) {
                    sellArea.setText("Invalid Price.\n");
                    return;
                }

                // checking to see if the quantity is greater than the quantity we have
                if(temp.getQuantity() >= quantity){
                    try{
                        temp.setQuantity(temp.getQuantity()-quantity);
                    }catch(InvalidQuantityException e){
                        sellArea.setText("Invalid quantity.\n");
                        return;
                    }
                }else{
                    sellArea.setText("ERROR: Not enough investment to sell.");
                    return;
                }
                // if we didnt sell all our investment update the book value else remove investment
                if(temp.getQuantity() > 0){
                    temp.updateBookValue(oldQuantity);
                    sellArea.setText("Payment recieved selling "+ temp.getSymbol()+": "+ temp.paymentReceived(quantity)+"\n");
                }else{
                    sellArea.setText("Payment recieved, selling all of "+ temp.getSymbol()+": "+ temp.paymentReceived(quantity)+"\n");
                    removefromHashMap(temp.name, investmentList.indexOf(temp));
                    investmentList.remove(count);
                }
            }
        }
        sellSymbolField.setText("");
        sellQuantityField.setText("");
        sellPriceField.setText("");
    }
    
    /** ********* UPDATE **********
     * Go through all the existing investments and ask the user to enter the new prices
     * adjust the bookValue based on the new prices
     * if there are no investments to update it will return with an error
     * if there are investments it will prompt the user to input a new price for each investment
     * @param idx is the index of the investment to update
    */
    public void update(int idx){
        double newPrice;

        // check if there are any stocks and update the stocks first
        Investment temp = investmentList.get(idx);

        newPrice = Double.parseDouble(updatePriceField.getText());

        try {
            temp.setPrice(newPrice);
        } catch (InvalidPriceException e) {
            updateArea.setText("Invalid Price.\n");
        }

        temp.setGain(newPrice);
        temp.setBookValue(newPrice, temp.getQuantity());
        updateArea.setText("Updated: " + temp.toString());
    }
    
    /** ******* GAIN **********
     * Calculate the total gain for all the individual investments based on their current prices
     * COMPUTING TOTAL GAIN hypothetically sold every stock and fund you have for the current
     * price, how much more would it be than the sum of their bookValues (subtract the 9.99
     * commission fee for the stock and 45 for the mutual fund of each sale)
    */
    public void computeTotalGain(){

        double totalGain = 0;
        if(investmentList.size() == 0){
            gainArea.setText("There are no investments.\n");
        }

        for(int i = 0; i < investmentList.size(); i++){
            Investment temp = investmentList.get(i);
            totalGain += temp.getGain();
            gainArea.append(temp.getSymbol() + " : " + (String.valueOf(temp.getGain())) + "\n");
        }

        String sGain = String.valueOf(totalGain);
        gainField.setText(sGain);
    }
    
    /** ******** SEARCH *************
     * User will provide values for up to three fields: symbol, keywords for the name, and price range
     * The user should be able to leave anyone of the fields blank or all the fields
     * 
     * If search is just a symbol: APPL
     * - only the investment with this symbol will be returned
     * If search contains: "Growth Fund"
     * - all investments whose names contain these keywords will be returned
     * 
     * If a search contains more than one field e.g., APPL 10.00-100.00
     * - then APPL is not enough as the price of the investment needs to fall within the price range
     * 
     * Price range cases:
     * - 15.00 means exactly $15.00
     * - "10.00-" means $10.00 and higher
     * - "-100.00" means $100.00 and lower
     * 
     * If any input value is empty it will match any corresponding attribute of an investment
     * If all fields are empty then all the investments will be returned
    */
    public void search(){

        
        if(investmentList.isEmpty()){
            searchArea.setText("ERROR: You have no current investments.\n");
            return;
        }
        
        String symbol;
        String keywords;
        String[] splitKeywords;

        String lP;
        double lowerPrice;

        String uP;
        double upperPrice;

        //check to see if there was an input

        if(searchSymbolField.getText().isEmpty()){
            symbol = null;
        }else{
            symbol = searchSymbolField.getText();
        }
        
        if(!searchLPriceField.getText().isEmpty()){
            lP = searchLPriceField.getText();
            lowerPrice = Double.parseDouble(lP);
        }else{
            lowerPrice = -1;
        }

        if(!searchHPriceField.getText().isEmpty()){
            uP = searchHPriceField.getText();
            upperPrice = Double.parseDouble(uP);
        }else{
            upperPrice = -1;
        }
        

        boolean found = false;

        ArrayList<Integer> investmentIndices = new ArrayList<>();
        for(int i = 0; i < investmentList.size(); i++){
            investmentIndices.add(i);
        }

        ArrayList<Investment> searchInvestments = investmentList;

        if(!searchNameField.getText().isEmpty()){

            keywords = searchNameField.getText();

            splitKeywords = keywords.split("[ ]+");

            searchInvestments = new ArrayList<>();

            for (String word: splitKeywords){
                String lowword = word.toLowerCase();
                if(keywordMap.containsKey(lowword)){

                    // go through every integer in investmentIndices (i), for each i it checks if its in keyword map, if it doesnt contain the integer remove that index from investmentIndecies
                    // after this is run we will have an array list with the indices for all the matching keywords
                    investmentIndices.removeIf(i -> !keywordMap.get(lowword).contains(i));
                }else{
                    searchArea.setText("No matches found.\n");
                    return;
                }
            }

            for(Investment i: investmentList){
                if(investmentIndices.contains(investmentList.indexOf(i))){
                    searchInvestments.add(i);
                }
            }

        }

        // searching for stocks, if the input for any of the fields are empty or if it was found in the stock it will continue to search and print the matching stock. The mutual search works exactly the same except it uses the funds array list
        for(Investment i: searchInvestments){
            if(searchSymbolField.getText().isEmpty() || (symbol.equalsIgnoreCase(i.getSymbol()))){
                if((searchHPriceField.getText().isEmpty() && searchLPriceField.getText().isEmpty()) || i.matchingPriceRange(lowerPrice,upperPrice)){
                    searchArea.append(i.toString()+"\n");
                    found = true;
                }
            }
        }
        // if nothing was found, search returns no matches found
        if(!found){
            searchArea.setText("No matches found.\n");
        }
        

    }

    /** This function loads the investment from a file
     * @param input is the file name
     */
    public void loadInvestments(String input){
        BufferedReader reader;
        //load student information from an input file
        try{
            reader = new BufferedReader(new FileReader(this.FILE_NAME));
            String Line = reader.readLine();

            while(Line != null){

                if (Line.equals("stock")){

                    Stock i = new Stock();

                    String buffer = reader.readLine();

                    try {
                        i.setSymbol(buffer);
                    } catch (InvalidSymbolException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();
                    
                    try {
                        i.setName(buffer);
                    } catch (InvalidNameException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();
                    try {
                        i.setQuantity(Integer.parseInt(buffer));
                    } catch (InvalidQuantityException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();
                    try {
                        i.setPrice(Double.parseDouble(buffer));
                    } catch (InvalidPriceException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();

                    i.setBookValueFile(Double.parseDouble(buffer));
                    
                    investmentList.add(i);
                    addtoHashMap(i.name,investmentList.indexOf(i));
                }else{
                    MutualFund m = new MutualFund();
                    String buffer = reader.readLine();
                    try {
                        m.setSymbol(buffer);
                    } catch (InvalidSymbolException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();

                    try {
                        m.setName(buffer);
                    } catch (InvalidNameException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();
                    try {
                        m.setQuantity(Integer.parseInt(buffer));
                    } catch (InvalidQuantityException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();

                    try {
                        m.setPrice(Double.parseDouble(buffer));
                    } catch (InvalidPriceException e) {
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    
                    buffer = reader.readLine();
                    m.setBookValueFile(Double.parseDouble(buffer));
                    investmentList.add(m);
                    addtoHashMap(m.name,investmentList.indexOf(m));
                }
                Line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /** This function takes a file name as an input and appends to the file
     * @param FileName is the name of the file
     * @throws IOException if there is an error writing to file
     */
    public void saveInvestments(String FileName) throws IOException {
        PrintWriter fileWriter = null;
        try{
            fileWriter = new PrintWriter(FileName,"UTF-8");
        }catch (IOException e){
            throw new IOException();
        }
        for(Investment i:investmentList){
            fileWriter.println(i.toFile());
        }
        fileWriter.close();
    }

    /** This function adds an element to the hashmap, if the key already exists it adds the index to that key
     * @param name is the name of the investment
     * @param index is the index of that investment
     */
    private void addtoHashMap(String name, int index){
        String[] splitkeywords = name.split("[ ]+");
        for(String word: splitkeywords){
            String lowword = word.toLowerCase();
            if(keywordMap.containsKey(lowword)){
                keywordMap.get(lowword).add(index);
            }else{
                ArrayList<Integer> intList = new ArrayList<>();
                intList.add(index);
                keywordMap.put(lowword.toLowerCase(), intList);
            }
        }
    }

    /** This function will remove the value in the hashmap corresponding to the index and name provided if an investment is fully sold and decrement all indexes higher by 1.
     * @param name is the name of the investment
     * @param index is the index of that investment
     */
    private void removefromHashMap(String name, int index){
        String[] splitkeywords = name.split("[ ]+");
        for(String word: splitkeywords){
            String lowword = word.toLowerCase();
            ArrayList<Integer> intList = keywordMap.get(lowword);

            if(intList.size() == 1){
                keywordMap.remove(lowword);
            }else{
                keywordMap.get(lowword).remove((Integer)index);
            }
        }

        for(ArrayList<Integer> valuesList : keywordMap.values()){
            for(int i : valuesList){
                if(i > index){
                    valuesList.remove((Integer)i);
                    valuesList.add(i-1);
                }
            }
        }

    }

    /**This function will get the index of a given symbol in the ArrayList (investmentList)
     * 
     * @param symbol is the symbol to search in the arraylist
     * @return integer value of the index of symbol in the arraylist
     */
    private int symbolIndex(String symbol){
        
        int idx = 0;

        for(int i = 0; i < investmentList.size(); i++){
            symbol = investmentList.get(i).getSymbol();
            if(symbol.equalsIgnoreCase(updateSymbolField.getText())){
                idx =  i;
            }
        }
        return idx;
    }

    /** This function sets up the update panel so that it is ready upon the MenuListener Action for update.
     * 
     */
    private void setUpdate(){
        if(investmentList.isEmpty()){
            updateArea.setText("ERROR: There are no investments to update.");
            return;
        }

        Investment temp = investmentList.get(0);
        updateSymbolField.setText(temp.getSymbol());
        updateNameField.setText(temp.getName());
        updatePriceField.setText("");
    }

}