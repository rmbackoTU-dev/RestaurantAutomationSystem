package restaurantAutomationSystem.commandInvokers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.RestaurantClock;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuEvent;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class Invoker {
  private Aggregator agg;

  public Invoker(Aggregator agg){
    this.agg = agg;
  }
  /**
   * Gets the current menu in use at the time
   * @return
   */
  public Menu getMenu() 
  throws IllegalStateException
  {
	RestaurantCommandInterface getMenuCmd=new GetMenuCommand(this.agg);
	try
	{
		Menu newMenu=(Menu) getMenuCmd.execute();
		return newMenu;
	}
	catch(CommandErrorException cee)
	{
		//Throw an IllegalStateException, because this means Menu
		//is not in a state is should be in following execute command
		//This likely means that there is a problem with the aggregator
		throw new IllegalStateException(
				"An unexpected Error occured while getting the menu");	
	}
  }
  
  /**
   * Gets all the menus available to the restaurant system 
   * and the times which they are available
   */
  public Map<RestaurantClock, Menu> getAllMenusWithTime()
  throws IllegalStateException
  {
	  HashMap menuList=new HashMap<RestaurantClock, Menu>();
	  RestaurantCommandInterface getMenusWithTimeCmd=new GetAllMenusWithTimeCommand(this.agg);
	  try
	  {
		  MenuEvent[] events=(MenuEvent[]) getMenusWithTimeCmd.execute();
		  MenuEvent currentEvent;
		  RestaurantClock time;
		  Menu eventMenu;
		  for (int i=0; i< events.length; i++)
		  {
			  currentEvent=events[i];
			  time=currentEvent.getClock();
			  eventMenu=(Menu) currentEvent.getAvailableMenu();
			  menuList.put(time, eventMenu);
		  }
		  return menuList;
	  }
	  catch(CommandErrorException cee)
	  {
		 //Throw an IllegalStateException, because this means Menu
		//is not in a state is should be in following execute command
		//This likely means that there is a problem with the aggregator
		throw new IllegalStateException(
				"An unexpected Error occured while getting the menu");	 
	  }
  }
  
  
  /**
   * Command Invoker adds a new tab if space is available
   * then returns a tab number to allow users to add to their tab later
   */
  public int openNewTab(Tab newTab)
  throws IllegalStateException
  {
	  Integer tabIndex;
	  RestaurantCommandInterface openTabCommand=new OpenTabCommand(this.agg, newTab);
	  try
	  {
		tabIndex=(Integer) openTabCommand.execute();
		return tabIndex.intValue();
	  }
	  catch(CommandErrorException cee)
	  {
		  throw new IllegalStateException(cee.getUserMessage());
	  }
  }
  
  /**
   * Command Invoker removes an order from a tab at a given tab index
   * and returns a boolean if successful
   */
  public boolean removeOrder(int tabNumber, int orderNum)
  throws IllegalStateException
  {
	  RestaurantCommandInterface removeOrderCmd=new RemoveOrderCommand(this.agg, tabNumber, 
			  orderNum);
	  try
	  {
		  Boolean result=(Boolean) removeOrderCmd.execute();
		  return result.booleanValue();
	  }
	  catch(CommandErrorException cee)
	  {
		  throw new IllegalStateException("An unexpected error has occured");
	  }
  }

  /**
  *Tries to place an order using the orderNumber passed
  * if it returns a -1 order number indicating no order was added
  * if it passes returns the order number of the order added in the tab 
  **/
  public int placeOrder(String item, String quantity, int tabNumber)
  throws IllegalArgumentException, IllegalStateException
  {
    int orderNumber=-1;
    Integer itemInt=Integer.parseInt(item);
    Integer quantityInt=Integer.parseInt(quantity);
    if(!(itemInt instanceof Integer))
    {
      throw new IllegalArgumentException("ERROR: An order number was not given.");
    }
    else if(quantityInt instanceof Integer)
    {
    	throw new IllegalArgumentException("ERROR: An order number was not given.");
    }
    else if(tabNumber < 0)
    {
    	throw new IllegalStateException("ERROR: The Tab has not been opened");
    }
    else{
      try{
    	Integer orderItemNum=Integer.getInteger(item);
    	Integer orderQuantity=Integer.getInteger(quantity);
        RestaurantCommandInterface placeOrderCmd=new PlaceOrderCommand(this.agg,
        		orderItemNum.intValue(), orderQuantity.intValue(), tabNumber);
        orderNumber= (Integer) placeOrderCmd.execute();
      }
      catch(CommandErrorException cee)
      {
          /**throw an IllegalArguementException with our
          *user error pull the User message at the 
          *systemInterface and pass it to the user interface**/
          throw new IllegalArgumentException(cee.getUserMessage());
      }
    }
    return orderNumber;
  }

  /**
  *Tries to place an order using the orderNumber passed
  * if it returns a -1 order number indicating no order was added
  * if it passes returns the order number of the order added in the tab
  **/
  public int placeOrderWithKnownOrder(String item, String quantity, int tabNumber, int orderNumber)
  throws IllegalArgumentException
  {
    int orderNum=-1;
    Integer itemInt=Integer.parseInt(item);
    Integer quantityInt=Integer.parseInt(quantity);
    if(!(itemInt instanceof Integer))
    {
      throw new IllegalArgumentException("ERROR: An order number was not given.");
    }
    else if(quantityInt instanceof Integer)
    {
    	throw new IllegalArgumentException("ERROR: An order number was not given.");
    }
    else{
      try{
    	Integer orderItemNum=Integer.getInteger(item);
    	Integer orderQuantity=Integer.getInteger(quantity);
        RestaurantCommandInterface placeOrderCmd=new PlaceOrderCommand(this.agg,
        		orderItemNum.intValue(), orderQuantity.intValue(), tabNumber, orderNumber);
        orderNum= (Integer) placeOrderCmd.execute();
      }
      catch(CommandErrorException cee)
      {
          /**throw an IllegalArguementException with our
          *user error pull the User message at the 
          *systemInterface and pass it to the user interface**/
          throw new IllegalArgumentException(cee.getUserMessage());
      }
    }
    return orderNum;
  }
  
  public Tab getTab(int tabNumber)
  throws IllegalStateException
  {
	  RestaurantCommandInterface getTabCmd=new GetTabCommand(this.agg, tabNumber);
		try
		{
			Tab newTab=(Tab) getTabCmd.execute();
			return newTab;
		}
		catch(CommandErrorException cee)
		{
			//Throw an IllegalStateException, because this means Tab
			//is not in a state is should be in following execute command
			//This likely means that there is a problem with the aggregator
			throw new IllegalStateException(
					"An unexpected Error occured while getting the menu");
			
		}
  }
  
  public boolean addCashPayment(int tabIndex, BigDecimal amount)
  throws IllegalStateException
  {
	  RestaurantCommandInterface addCashCmd=new AddCashPaymentCommand(this.agg, tabIndex, amount);
	  boolean success=false;
	  try
	  {
		  success=(Boolean) addCashCmd.execute();
	  }
	  catch(CommandErrorException cee)
	  {
		  throw new IllegalStateException(
				  "An unexpected Error occured while adding a cash payment");
	  }
	  return success;
  }
  
  public boolean addCreditCardPayment(int tabIndex, String accountNum, String provider, String token,
		  int expirationMonth, int expirationYear, String cashAmount, String chargeLimit )
  throws IllegalStateException
  {
	  String expirationDate=String.valueOf(expirationMonth)+"-"+String.valueOf(expirationYear);
	  RestaurantCommandInterface addCardCmd= new AddCreditCardPaymentCommand(this.agg,
			  tabIndex, accountNum, provider, token, expirationDate, cashAmount, chargeLimit);
	  boolean success=false;
	  try
	  {
		  success=(Boolean) addCardCmd.execute();
	  }
	  catch(CommandErrorException cee)
	  {
		  throw new IllegalStateException(
				  "An unexpected Error occured while adding a card payment");
	  }
	  return success;
  }
  
  public int[] splitTab(int tabIndex)
  throws IllegalStateException
  {
	 RestaurantCommandInterface splitTabCmd=new SplitTabCommand(this.agg, tabIndex);
	 int[] newTabIndexes=null;
	 try
	 {
		 newTabIndexes=(int[]) splitTabCmd.execute();
		 if(newTabIndexes == null)
		 {
			 throw new IllegalStateException(
					 "An unknown error occured when attempting to split the tab "+tabIndex);
		 }
	 }
	 catch(CommandErrorException cee)
	 {
		 throw new IllegalStateException("An error occured while spliting the tab " + cee.getMessage());
	 }
	 return newTabIndexes;
  }
  
  public boolean payBill(int tabIndex, String token)
  throws IllegalStateException
  {
	  RestaurantCommandInterface payBillCmd=new PayBillCommand(this.agg, tabIndex, token);
	  Boolean result=false;
	  try
	  {
		  result=(Boolean) payBillCmd.execute();
	  }
	  catch(CommandErrorException cee)
	  {
		throw new IllegalStateException("An error occured while spliting the tab "+cee.getMessage());  
	  }
	  return result;
  }
	  
}