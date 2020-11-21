package restaurantAutomationSystem.controllers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.commandInvokers.Invoker;
import restaurantAutomationSystem.restaurantData.Aggregator;
import restaurantAutomationSystem.restaurantData.Menu;
import restaurantAutomationSystem.restaurantData.Order;
import restaurantAutomationSystem.restaurantData.Tab;
import restaurantAutomationSystem.restaurantData.MenuItem;
import restaurantAutomationSystem.restaurantData.OrderItem;

public class SystemInterfaceController {
	private static Aggregator systemData;
	  private static Invoker invoker;
	  private static SystemInterfaceController currentInstance=null;
	  
	  //speak the language of the backend
	  public static void initialize(){
	    Menu menu = new Menu();
	    MenuItem tempMenuItem = new MenuItem("Shrimp Salad", 1, 9.99, 1, true);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Fruit Parfait", 2, 9.99, 2, true);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Cheese Platter", 3, 1.99, 0, true);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Cola", 4, 2.99, 3, false);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Bottle Pink Moscato", 7, 17.99, 3, true);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Bean Burrito", 5, 1.99, 1, false);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Porterhouse", 6, 32.99, 1, false);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Brownie Sundae", 8, 15.99, 2, false);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem(" Egg Sandwitch", 9, 4.99, 1,true);
	    menu.addMenuItem(tempMenuItem);
	    tempMenuItem = new MenuItem("Caesar Salad", 10, 19.99, 0, true);
	    menu.addMenuItem(tempMenuItem);
	    Order order=new Order();
	    systemData = new Aggregator(menu, order);
	    invoker=new Invoker(systemData);
	  }


	  public static String getMenu(){
	      Menu menu;
	      menu = invoker.getMenu();
	      return menu.toString();
	  }

	  public static String placeOrder(String item)
	  {
	    String resultString="";
	    try
	    {
	      boolean result=invoker.placeOrder(item, "1");
	      if(result)
	      {
	        resultString="Successfully Added "+item +
	        " to order";
	      }
	    }
	    catch(IllegalArgumentException iae)
	    {
	       resultString=iae.getMessage();
	    }
	    return resultString;
	  }


	  public static String getTab(){
	    Tab tab;
	    tab = invoker.getTab();
	    return tab.toString();
	  };
}
