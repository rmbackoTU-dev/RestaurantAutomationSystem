package restaurantAutomationSystem.testRestaurantData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import restaurantAutomationSystem.model.restaurantData.MenuDecorator;
import restaurantAutomationSystem.model.restaurantData.ItemOfTheDay;
import restaurantAutomationSystem.model.restaurantData.CategoryHeaders;
import restaurantAutomationSystem.model.restaurantData.MenuData;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuHeader;
import restaurantAutomationSystem.model.restaurantData.MenuItem;

public class TestMenuDecorators {
	
	private static final String DINNER_HEADER="Dinner\n\n\n";
	private static final String MENU_HEADER="\t\tMENU\t\t\n\n\n";
	private static final String MenuItemOneString="0: Bread Sticks\n$5.99";
	private static final String MenuItemTwoString="1: Chicken Alfredo\n$15.50";
	private static final String MenuItemThreeString="2: Tiramisu\n$10.25";
	private static final String MenuItemFourString="3: Water\n( Heart Healthy )\n$0.00";
	private static final String APPETIZER_HEADER="Appetizers: \n\n\n";
	private static final String MAIN_COURSE_HEADER="Main Courses: \n\n\n";
	private static final String DESSERT_HEADER="Desserts: \n\n\n";
	private static final String DRINK_HEADER="Drinks: \n\n\n";
	private static final String ITEMS_OF_THE_DAY_HEADER="\n\n Items of the Day \n\n";
	private String toStringWithHeader=DINNER_HEADER+
			MENU_HEADER+"\t\t"+MenuItemOneString+"\n\t\t"+MenuItemTwoString
			+"\n\t\t"+MenuItemThreeString+"\n\t\t"+MenuItemFourString+"\n";
	private String toStringWithCategories=MENU_HEADER+APPETIZER_HEADER+
			"\t\t"+MenuItemOneString+"\n\n"+MAIN_COURSE_HEADER+"\t\t"+MenuItemTwoString+"\n\n"+
			DESSERT_HEADER+"\t\t"+MenuItemThreeString+"\n\n"+DRINK_HEADER+"\t\t"+
			MenuItemFourString+"\n\n";
	private String toStringWithItemOfTheDay=MENU_HEADER+"\t\t"+MenuItemOneString+"\n\t\t"+MenuItemTwoString
			+"\n\t\t"+MenuItemThreeString+"\n\t\t"+MenuItemFourString+"\n"+ITEMS_OF_THE_DAY_HEADER+
			"\t\t"+MenuItemTwoString+"\n\t\t"+MenuItemThreeString+"\n";
	private String toStringWithHeaderAndCategories=DINNER_HEADER+
			MENU_HEADER+APPETIZER_HEADER+"\t\t"+MenuItemOneString+"\n\n"+MAIN_COURSE_HEADER+"\t\t"+MenuItemTwoString
			+"\n\n"+DESSERT_HEADER+"\t\t"+MenuItemThreeString+"\n\n"+DRINK_HEADER+"\t\t"+MenuItemFourString+"\n\n";
	private String toStringWithHeaderAndItemOfTheDay=DINNER_HEADER+
			MENU_HEADER+"\t\t"+MenuItemOneString+"\n\t\t"+MenuItemTwoString
			+"\n\t\t"+MenuItemThreeString+"\n\t\t"+MenuItemFourString+"\n"+ITEMS_OF_THE_DAY_HEADER+
			"\t\t"+MenuItemTwoString+"\n\t\t"+MenuItemThreeString+"\n";
	private String toStringWithEverything=DINNER_HEADER+
			MENU_HEADER+APPETIZER_HEADER+"\t\t"+MenuItemOneString+"\n\n"+MAIN_COURSE_HEADER+"\t\t"+MenuItemTwoString
			+"\n\n"+DESSERT_HEADER+"\t\t"+MenuItemThreeString+"\n\n"+DRINK_HEADER+"\t\t"+MenuItemFourString+"\n\n"
			+ITEMS_OF_THE_DAY_HEADER+"\t\t"+MenuItemTwoString+"\n\t\t"+MenuItemThreeString+"\n";
	private static final MenuItem itemOne=new MenuItem("Bread Sticks", 0, 5.99, 0, false);
	private static final MenuItem itemTwo=new MenuItem("Chicken Alfredo", 1, 15.50, 1, false);
	private static final MenuItem itemThree=new MenuItem("Tiramisu", 2, 10.25, 2, false);
	private static final MenuItem itemFour=new MenuItem("Water", 3, 0.00, 3, true);
	private MenuData defaultMenu;
	private MenuItem[] itemsOfTheDay;
	private String[] categories; 
	
	@BeforeEach
	public void setUp()
	{
		this.defaultMenu=new Menu();
		defaultMenu.addMenuItem(itemOne);
		defaultMenu.addMenuItem(itemTwo);
		defaultMenu.addMenuItem(itemThree);
		defaultMenu.addMenuItem(itemFour);
		this.itemsOfTheDay=new MenuItem[2];
		this.categories=new String[4];
		this.categories[0]="Appetizers";
		this.categories[1]="Main Courses";
		this.categories[2]="Desserts";
		this.categories[3]="Drinks";
		this.itemsOfTheDay[0]=itemTwo;
		this.itemsOfTheDay[1]=itemThree;
	}
	
	@Test
	public void testConstructMenuWithHeader()
	{
		MenuDecorator customDinnerMenuHeaderAdded=new MenuHeader(this.defaultMenu, "Dinner");
		Assertions.assertEquals(toStringWithHeader, customDinnerMenuHeaderAdded.toString());
	}
	
	@Test
	public void testConstructMenuWithCategories()
	{
		MenuDecorator customDinnerMenuCategoriesAdded=new CategoryHeaders(this.defaultMenu, this.categories);
		Assertions.assertEquals(toStringWithCategories, customDinnerMenuCategoriesAdded.toString());
	}
	
	@Test
	public void testConstructMenuWithItemOfTheDay()
	{
		MenuDecorator customDinnerMenuItemsOfTheDayAdded=new ItemOfTheDay(this.defaultMenu, this.itemsOfTheDay);
		Assertions.assertEquals(toStringWithItemOfTheDay, customDinnerMenuItemsOfTheDayAdded.toString());

	}
	
	@Test
	public void testConstructMenuWithHeaderCategories()
	{
		//Categories have to be added first because categories toString can't call super
		MenuDecorator customDinnerMenuCategoriesAdded=new CategoryHeaders(this.defaultMenu, this.categories);
		MenuDecorator customDinnerMenuHeadersAndCategoriesAdded=new MenuHeader(customDinnerMenuCategoriesAdded, "Dinner");
		Assertions.assertEquals(toStringWithHeaderAndCategories, customDinnerMenuHeadersAndCategoriesAdded.toString());	
	}
	
	@Test
	public void testConstructMenuWithHeaderItemOfTheDay()
	{
		MenuDecorator customDinnerMenuHeaderAdded=new MenuHeader(this.defaultMenu, "Dinner");
		MenuDecorator customDinnerMenuHeadersAndItemsOfTheDayAdded=new ItemOfTheDay(customDinnerMenuHeaderAdded,itemsOfTheDay);
		Assertions.assertEquals(toStringWithHeaderAndItemOfTheDay, customDinnerMenuHeadersAndItemsOfTheDayAdded.toString());
	}
	
	@Test
	public void testConstructMenuWithHeaderItemOfTheDayAndCategory()
	{
			MenuDecorator customDinnerMenuCategoriesAdded=new CategoryHeaders(this.defaultMenu, this.categories);
			MenuDecorator customDinnerMenuHeadersAndCategoriesAdded=new MenuHeader(customDinnerMenuCategoriesAdded, "Dinner");
			MenuDecorator customDinnerAllAddonsAdded=new ItemOfTheDay(customDinnerMenuHeadersAndCategoriesAdded, itemsOfTheDay);
			Assertions.assertEquals(toStringWithEverything, customDinnerAllAddonsAdded.toString());

	}
	
}
