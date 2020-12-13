package restaurantAutomationSystem.controllers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.RestaurantClock;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuData;
import restaurantAutomationSystem.model.restaurantData.MenuDecorator;
import restaurantAutomationSystem.model.restaurantData.MenuItem;
import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.OrderItem;
import restaurantAutomationSystem.model.restaurantData.Tab;
import restaurantAutomationSystem.model.restaurantData.MenuEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import restaurantAutomationSystem.commandInvokers.Invoker;

public class SystemInterfaceController {
		private static Aggregator systemData;
	  private static Invoker invoker;
	  private static SystemInterfaceController currentInstance=null;
	  
	  //gets data from the model
	  public static void initialize(){
	    
	    invoker=new Invoker(systemData);
	    MenuFactory newFactory=new MenuFactory();
	    MenuEvent[] events=newFactory.getEvents();
	    systemData=new Aggregator(events.length);
	    //Add the events and menus to the Aggregator
	    for(int i=0; i< events.length; i++)
	    {
	    	systemData.addMenuEvent(events[i]);
	    }
	    
	  }


	  public static String getMenu()
	  {
	      Menu menu;
	      String resultString;
	      try
	      {
	    	  menu = invoker.getMenu();
	    	  resultString=menu.toString();
	      }
	      catch(IllegalStateException ise)
	      {
	    	  resultString=ise.getMessage();
	      }
	      return resultString;
	  }
	  
	  public static String getAllMenus()
	  {
		  HashMap<RestaurantClock, Menu> menuTimes;
		  String allMenusString="";
		  menuTimes=(HashMap) invoker.getAllMenusWithTime();
		  Set timeKeySet=menuTimes.keySet();
		  Iterator timeIter=timeKeySet.iterator();
		  LocalDate today=LocalDate.now();
		  RestaurantClock todaysDate=new RestaurantClock();
		  todaysDate.setMonth(today.getMonthValue());
		  todaysDate.setDay(today.getDayOfMonth());
		  RestaurantClock currentClock;
		  allMenusString=todaysDate.getDateString()+"\n";
		  Menu currentMenu;
		  while(timeIter.hasNext())
		  {
			  currentClock=(RestaurantClock) timeIter.next();
			  allMenusString=allMenusString+currentClock.getTimeString()+"\n\n\n";
			  currentMenu=menuTimes.get(currentClock);
			  allMenusString=allMenusString+currentMenu.toString();
		  }
		  return null;
	  }
	  
	  public static String payBill(int tabIndex, String token)
	  {
		  Boolean success=false;
		  String successString;
		  try
		  {
			  success=invoker.payBill(tabIndex, token);
		  }
		  catch(IllegalStateException ise)
		  {
			  successString=ise.getMessage();
		  }
		  if(success)
		  {
			  successString="Bill was paid successfully. Thank you, have a wonderful day";
		  }
		  else
		  {
			  successString="Bill payment unsuccessful. Please check your payment and try again";
		  }
		  
		  return successString;
	  }
	  
	  public static String openTab()
	  {
		  Tab newTab=new Tab();
		  String successString;
		  int tabNumber=-1;
		  try
		  {
		  	tabNumber=invoker.openNewTab(newTab);
		  }
		  catch(IllegalStateException ise)
		  {
			  successString=ise.getMessage();
		  }
		  
		  if(tabNumber != -1)
		  {
			  successString="The a new tab was added. "+tabNumber+" is your tab number keep track "+
					  "of this for when you place a new order";
		  }
		  else
		  {
			  successString="Your tab was not successfully added, "+
					  "we may be a little busy right now please try again later";
					  
		  }
		  return successString;
	  }
	  
	  public static String addCardPayment(int tabIndex,
			  String accountNumber,
			  String providerPayment,
			  String securityToken,
			  String expirationMonth,
			  String expirationYear,
			  String cashAmount,
			  String chargeLimit)
	  {
		  Integer month=Integer.valueOf(expirationMonth);
		  Integer year=Integer.valueOf(expirationYear);
		  String successString;
		  Boolean success=false;
		  try
		  {
			  success=invoker.addCreditCardPayment(tabIndex, accountNumber, providerPayment,
					  securityToken, month, year, cashAmount, chargeLimit);
		  }
		  catch(IllegalStateException ise)
		  {
			  successString=ise.getMessage();
		  }
		  
		  if(success)
		  {
			  successString="Successfully added $"+cashAmount+" to your balance";
		  }
		  else
		  {
			  successString="Failed to add a credit card to your balance";
		  }
		  return successString;

	  }
	  
	  public static String addCashPayment(int tabIndex,
			  String amount)
	  {
		  BigDecimal cashAmount=new BigDecimal(amount);
		  cashAmount=cashAmount.setScale(2, RoundingMode.CEILING);
		  String successString;
		  Boolean success=false;
		  try
		  {
			  success=invoker.addCashPayment(tabIndex, cashAmount);
		  }
		  catch(IllegalStateException ise)
		  {
			  successString=ise.getMessage();
		  }
		  if(success)
		  {
			  successString="Successfully added $"+amount+" to your balance";
		  }
		  else
		  {
			  successString="Failed to add cash to your balance";
		  }
		  return successString;
	  }
	  
	  public static String removeOrder(int tabIndex, int orderIndex)
	  {
		  String successString;
		  Boolean success=false;
		  try
		  {
			  success=invoker.removeOrder(tabIndex, orderIndex);
		  }
		  catch(IllegalStateException ise)
		  {
			  successString=ise.getMessage();
		  }
		  if(success)
		  {
			  successString="Successfully removed order "+orderIndex+" from your tab";
		  }
		  else
		  {
			  successString="Failed to remove order "+orderIndex+" from your tab";
		  }
		  return successString;
	  }

	  public static String placeOrder(String item, int quantity, int tabIndex, int orderNum)
	  {
	    String resultString="";
	    String quantityString=String.valueOf(quantity);
	    int orderNumber=-1;
	    try
	    {
	    
	      if(orderNum == -1)
	      {
	    	  orderNumber=invoker.placeOrder(item, quantityString, tabIndex);
	      }
	      else
	      {
	    	  orderNumber=invoker.placeOrderWithKnownOrder(item, quantityString, tabIndex, orderNum);
	      }
	      
	      if(orderNumber != -1)
	      {
	        resultString="Successfully Added "+item +
	        " to order. The order number is: "+orderNumber;
	      }
	    }
	    catch(IllegalArgumentException iae)
	    {
	       resultString=iae.getMessage();
	    }
	    return resultString;
	  }


	  public static String getTab(int tabIndex)
	  {
	    Tab tab;
	    String successString;
	    try
	    {
	    	tab = invoker.getTab(tabIndex);
	    	successString=tab.toString();
	    }
	    catch(IllegalStateException ise)
	    {
	    	successString=ise.getMessage();
	    }
	    return successString;
	  };
	  
	  public static String splitTab(int tabIndex)
	  {
		  int[] indexes;
		  String successString;
		  try
		  {
			  indexes=invoker.splitTab(tabIndex);
			  successString="You new tabs, tab numbers are : ";
			  for(int i=0; i< indexes.length; i++)
			  {
				  successString=successString+" "+indexes[i];
			  }
		  }
		  catch(IllegalStateException ise)
		  {
			  successString=ise.getMessage();
		  }
		  return successString;
	  }
}
