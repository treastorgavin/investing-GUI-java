# investing-GUI-java

## Program Description
----------------------
This program implements 4 classes to manage a protfolio. The classes are Investment, Stock, MutualFund and Portfolio. Stock and Mutual Fund have the same attributes of: symbol, name, quantity, price and bookValue and are inherited from the investment class. The Portfolio class executes commands in the program and manages the Stocks and Mutual Funds in array lists.
The program allows the user to do several functions: buy, sell, update, getGain, search, and quit.
The public class portfolio extends GUI and all the prepare GUI functions create GUIs for each function.

### Compile and run
To compile the program:
compile everything in the ePortfolio
javac ./ePortfolio/Portfolio.java

to run the program:

java ePortfolio/Portfolio FILE_NAME.txt

### Limitations, Issues and Errors
NA

### Future improvements to be made
Future improvements could be to streamline the GUI code to be more efficient.
Another improvement could be to adjust the Action listeners for specific buttons that execute the same process.
e.g. there could be one listener for the Reset button that would reset all the fields in all the functions.
Right now the fields are being reset upon opening that funciton (option).

Another improvement could be to specify the incorrect input. Right now it is just a general error warning that one of the feilds are incorrect. An improvement would be to specify what field is incorrect so the user can identify it faster. 


### Test Plan
Testing if the input is valid for the commands:
-empty string
-numerical characters
-any command that dont match: buy, sell, update, getgain, search and quit
-different cases for each command

BUY
Testing buy command:
- input
	- test for a valid investment (stock/mutual fund)
	- empty string
	- numerical characters
	- anything but stock/mutual fund
	- acceptable mutual or fund
- testing symbol for investment
	- empty string
	- string that already exists
	- string that doesnt exist
- testing name
	- empty string
	- non empty string
- testing quantity
	- empty string
	- non numerical characters
	- negatives
	- must be an integer
- testing price
	- empty string
	- non numerical characters
	- negatives
	- must be a double/int

SELL
Testing sell:
- test with empty arrays
- test with one empty one not
- test with non empty arrays
	- empty symbol
	- symbols that dont exist
	- symbols that do exist
	- quantity must be positive integer and nothing else
	- price must be positive double and nothing else
	- non numerical characters
	- negatives
	- quantity that is greater than the quantity available
	- quantity equal to the quantity available (valid)
	- quantity less than the quantity available (valid)

UPDATE
Testing update:
- two empty arrays
- one array empty
- neither array empty
	- empty strings
	- negative price
	- non numerical characters
	- valid input

GETGAIN
testing getGain:
	- test with empty arrays
	- one empty array
	- no empty arrays

SEARCH
testing search
- testing with 2 empty arrays
- test with different combinations of input, either empty or not (every combination)

price range
- negative numbers
- non numerical characters
- with only upper limit
- with only lower limit
- with exact price
- with upper and lower limit
	- with no matches
	- with some matches
	- edge cases of limit
	- all matching

symbol
- no matches
- match at the start
- match at the end
- match anywhere not at the start or end
-numerical values

keywords
- with one word matches one doesnt
- with multiple words that dont match
- with mutiple words that do match
- multiple matches for multiple investments
- one word that does match

