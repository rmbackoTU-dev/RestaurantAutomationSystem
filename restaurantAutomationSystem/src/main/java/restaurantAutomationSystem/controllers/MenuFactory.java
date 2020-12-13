package restaurantAutomationSystem.controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import restaurantAutomationSystem.model.ObservableTime;
import restaurantAutomationSystem.model.restaurantData.CategoryHeaders;
import restaurantAutomationSystem.model.restaurantData.ItemOfTheDay;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuData;
import restaurantAutomationSystem.model.restaurantData.MenuDecorator;
import restaurantAutomationSystem.model.restaurantData.MenuEvent;
import restaurantAutomationSystem.model.restaurantData.MenuHeader;
import restaurantAutomationSystem.model.restaurantData.MenuItem;
import restaurantAutomationSystem.model.restaurantData.MenuManager;

/**
 * Creates a set of defaults for menus
 * and registers them with a MenuManager
 * allows program to set up menus for front end
 * @author rmbackoTU-dev
 *
 */
public class MenuFactory {

	private MenuEvent[] events;
	
	public MenuFactory()
	{
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
	    
	    String dinnerHeader="Dinner";
	    String lunchHeader="Lunch";
	    String breakfastHeader="Breakfast";
	    
	    String[] breakfastCategories= {"EGGS & OMELETTES", "PANCAKES", "HOT & GREAT GRILLERS", "DRINKS"};
	    String[] lunchCategories= {"SANDWICHES", "BURGERS", "SIDES", "DRINKS"};
	    String[] dinnerCategories= {"ENTREES & ROAST", "FRESH FROM THE SEA", "APPETIZERS","DESSERT","DRINKS"};
	    
	    MenuItem[] itemsOfTheDayBreakfast= {cc_pancakes, c_omlette};
	    MenuItem[] itemsOfTheDayLunch= {hotCornedBeef, tunaFish};
	    MenuItem[] itemsOfTheDayDinner= {meatloaf, applePie, jumboShrimp};
	    
	    MenuDecorator breakfastMenuWithCategories=new CategoryHeaders(breakfastMenu, breakfastCategories);
	    MenuDecorator lunchMenuWithCategories=new CategoryHeaders(lunchMenu, lunchCategories);
	    MenuDecorator dinnerMenuWithCategories=new CategoryHeaders(dinnerMenu, dinnerCategories);
	    
	    MenuDecorator breakfastWithCatAndIOTD=new ItemOfTheDay(breakfastMenuWithCategories, itemsOfTheDayBreakfast);
	    MenuDecorator lunchWithCatAndIOTD=new ItemOfTheDay(lunchMenuWithCategories, itemsOfTheDayLunch);
	    MenuDecorator dinnerWithCatAndIOTD=new ItemOfTheDay(dinnerMenuWithCategories, itemsOfTheDayDinner);
	    
	    MenuDecorator finalBreakfastMenu=new MenuHeader(breakfastWithCatAndIOTD, breakfastHeader);
	    MenuDecorator finalLunchMenu=new MenuHeader(lunchWithCatAndIOTD,lunchHeader);
	    MenuDecorator finalDinnerMenu=new MenuHeader(dinnerWithCatAndIOTD, dinnerHeader);
	    
	   
	    MenuEvent breakfastEvent=new MenuEvent(finalBreakfastMenu);
	    MenuEvent lunchEvent=new MenuEvent(finalLunchMenu);
	    MenuEvent dinnerEvent=new MenuEvent(finalDinnerMenu);
	    
	    breakfastEvent.setEventTime(8, 0);
	    lunchEvent.setEventDay(12, 30);
	    dinnerEvent.setEventTime(4, 30);
	    
	    this.events=new MenuEvent[3];
	    this.events[0]=breakfastEvent;
	    this.events[1]=lunchEvent;
	    this.events[2]=dinnerEvent;
	    
	}
	
	
	public MenuEvent[] getEvents()
	{
		return this.events;
	}
	
	
}
