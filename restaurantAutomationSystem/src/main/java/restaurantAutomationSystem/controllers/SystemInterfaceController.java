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
	    MenuData breakfastMenu = new Menu(12);
	    MenuData lunchMenu= new Menu(12);
	    MenuData dinnerMenu=new Menu(12);
	    //breakfast Items:
	    MenuItem egg=new MenuItem("One Egg", 1, 6.45, 0, true);
	    MenuItem omlette=new MenuItem("Omlette", 2, 8.25, 0, true);
	    MenuItem c_omlette=new MenuItem("Cheese Omlette", 3, 9.25, 0, false);
	    MenuItem bhs_omlette=new MenuItem("Bacon,Ham, or Sausage Omlette", 4, 10.55, 0, false);
	    MenuItem pancakes=new MenuItem("Pancakes", 5, 7.95, 1, false);
	    MenuItem b_pancakes=new MenuItem("Buckwheat Pancakes", 6, 8.95, 1, true);
	    MenuItem cc_pancakes=new MenuItem("Chocolate Chip Pancakes", 7, 8.45, 1, false);
	    MenuItem countryFriedSteak=new MenuItem("Country Fried Steak", 8, 13.95, 2, false);
	    MenuItem countryFriedChicken=new MenuItem("Country Fried Chicken", 9, 12.55, 2, false);
	    MenuItem oj=new MenuItem("Orange Juice", 10, 2.95, 3, true);
	    MenuItem milk=new MenuItem("Milk", 12, 2.95, 3, true);
	    //lunch Items
	    MenuItem grilledCheese=new MenuItem("Grilled Cheese", 1, 5.65, 0, false);
	    MenuItem hotCornedBeef=new MenuItem("Hot Corned Beef", 2, 10.15, 0, false);
	    MenuItem tunaFish=new MenuItem("Tuna Fish Sandwitch", 3, 10.25, 0, true);
	    MenuItem hamburger=new MenuItem("Hamburger", 4, 7.50, 1, false);
	    MenuItem cheeseburger=new MenuItem("Cheeseburger", 5, 8.05, 1, false);
	    MenuItem veggieBurger=new MenuItem("Veggie Burger", 6, 7.95, 1, true);
	    MenuItem frenchFries=new MenuItem("French Fries", 7, 4.50, 2, false);
	    MenuItem onionRings=new MenuItem("Onion Rings", 8, 5.95, 2, false);
	    MenuItem appleSauce=new MenuItem("Apple Sauce", 9, 4.05, 2, true);
	    //dinner Items
	    MenuItem meatloaf=new MenuItem("Homemade Meatloaf", 1, 14.55, 0, false);
	    MenuItem grilledChickenWScallops=new MenuItem("Grilled Chicken with Scallops", 2, 22.95, 0, true);
	    MenuItem friedChickenBasket=new MenuItem("Fried Chicken Basket", 3, 15.25, 0, false);
	    MenuItem jumboShrimp=new MenuItem("Jumbo Shrimp", 4, 24.95, 1, false);
	    MenuItem tilapia=new MenuItem("Tilapia Filet", 5, 15.95, 1, true);
	    MenuItem mozzarella=new MenuItem("Mozzarella", 6, 8.95, 2, false);
	    MenuItem friedPickles=new MenuItem("Fried Pickles", 7, 6.95, 2, false);
	    MenuItem cheesecake=new MenuItem("One Slice of Cheesecake", 8, 12.95, 4, false);
	    MenuItem applePie=new MenuItem("One Slice of Apple Pie with Ice Cream", 9, 9.50, 4, false);
	    MenuItem parfait=new MenuItem("Berry Parfait", 12, 6.95, 4, true);
	    
	    //common drink items
	    MenuItem water=new MenuItem("Water", 11, 0.00, 3, true);
	    MenuItem coffee=new MenuItem("Coffee", 13, 3.55, 3, false);
	    MenuItem soda=new MenuItem("Soda", 14, 2.95, 3, false);
	    MenuItem tea=new MenuItem("Tea", 15, 2.95, 3, false);
	    
	    breakfastMenu.addMenuItem(egg);
	    breakfastMenu.addMenuItem(omlette);
	    breakfastMenu.addMenuItem(c_omlette);
	    breakfastMenu.addMenuItem(bhs_omlette);
	    breakfastMenu.addMenuItem(pancakes);
	    breakfastMenu.addMenuItem(b_pancakes);
	    breakfastMenu.addMenuItem(cc_pancakes);
	    breakfastMenu.addMenuItem(countryFriedSteak);
	    breakfastMenu.addMenuItem(countryFriedChicken);
	    breakfastMenu.addMenuItem(oj);
	    breakfastMenu.addMenuItem(coffee);
	    breakfastMenu.addMenuItem(milk);
	    breakfastMenu.addMenuItem(water);
	    
	    lunchMenu.addMenuItem(grilledCheese);
	    lunchMenu.addMenuItem(hotCornedBeef);
	    lunchMenu.addMenuItem(tunaFish);
	    lunchMenu.addMenuItem(hamburger);
	    lunchMenu.addMenuItem(cheeseburger);
	    lunchMenu.addMenuItem(veggieBurger);
	    lunchMenu.addMenuItem(frenchFries);
	    lunchMenu.addMenuItem(onionRings);
	    lunchMenu.addMenuItem(appleSauce);
	    lunchMenu.addMenuItem(soda);
	    lunchMenu.addMenuItem(tea);
	    lunchMenu.addMenuItem(water);

	    dinnerMenu.addMenuItem(meatloaf);
	    dinnerMenu.addMenuItem(grilledChickenWScallops);
	    dinnerMenu.addMenuItem(friedChickenBasket);
	    dinnerMenu.addMenuItem(jumboShrimp);
	    dinnerMenu.addMenuItem(tilapia);
	    dinnerMenu.addMenuItem(mozzarella);
	    dinnerMenu.addMenuItem(friedPickles);
	    dinnerMenu.addMenuItem(cheesecake);
	    dinnerMenu.addMenuItem(applePie);
	    dinnerMenu.addMenuItem(parfait);
	    dinnerMenu.addMenuItem(soda);
	    dinnerMenu.addMenuItem(coffee);
	    dinnerMenu.addMenuItem(tea);
	    dinnerMenu.addMenuItem(water);

	    
	    invoker=new Invoker(systemData);
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
	  
	  public static String payBill(String tabIndex, String token)
	  {
		  Boolean success=false;
		  Integer index=Integer.getInteger(tabIndex);
		  String successString;
		  try
		  {
			  success=invoker.payBill(index, token);
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
	  
	  public static String addCardPayment(String tabIndex,
			  String accountNumber,
			  String providerPayment,
			  String securityToken,
			  String expirationMonth,
			  String expirationYear,
			  String cashAmount,
			  String chargeLimit)
	  {
		  Integer index=Integer.valueOf(tabIndex);
		  Integer month=Integer.valueOf(expirationMonth);
		  Integer year=Integer.valueOf(expirationYear);
		  String successString;
		  Boolean success=false;
		  try
		  {
			  success=invoker.addCreditCardPayment(index, accountNumber, providerPayment,
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
	  
	  public static String addCashPayment(String tabIndex,
			  String amount)
	  {
		  Integer index=Integer.valueOf(tabIndex);
		  BigDecimal cashAmount=new BigDecimal(amount);
		  cashAmount=cashAmount.setScale(2, RoundingMode.CEILING);
		  String successString;
		  Boolean success=false;
		  try
		  {
			  success=invoker.addCashPayment(index, cashAmount);
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
	  
	  public static String removeOrder(String tabIndex, String orderIndex)
	  {
		  Integer tIndex=Integer.valueOf(tabIndex);
		  Integer oIndex=Integer.valueOf(orderIndex);
		  String successString;
		  Boolean success=false;
		  try
		  {
			  success=invoker.removeOrder(tIndex, oIndex);
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
}
