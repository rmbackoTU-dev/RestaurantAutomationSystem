package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class Invoker {
  private Aggregator agg;

  public Invoker(Aggregator agg){
    this.agg = agg;
  }

  public Menu getMenu() {
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
  *Tries to place an order using the orderNumber passed
  * if it returns a -1 order number indicating no order was added
  * if it passes returns the order number of the order added in the tab 
  **/
  public int placeOrder(String item, String quantity, int tabNumber)
  throws IllegalArgumentException
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
  
  public Tab getTab(int tabNumber){
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
}