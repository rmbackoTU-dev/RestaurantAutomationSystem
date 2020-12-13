package restaurantAutomationSystem.testRestaurantData;

import org.junit.jupiter.api.Test;

import restaurantAutomationSystem.model.ObservableTime;
import restaurantAutomationSystem.model.restaurantData.MenuData;
import restaurantAutomationSystem.model.restaurantData.MenuDecorator;
import restaurantAutomationSystem.model.restaurantData.MenuHeader;
import restaurantAutomationSystem.model.restaurantData.MenuItem;
import restaurantAutomationSystem.model.restaurantData.MenuManager;
import restaurantAutomationSystem.model.restaurantData.MenuEvent;
import restaurantAutomationSystem.model.restaurantData.Menu;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class TestMenuObserverAndObservable {

	private MenuData testMenuOne;
	private MenuData testMenuTwo;
	private MenuData testMenuThree;
	private MenuData testMenuFour;
	private static String headerOne="Header One";
	private static String headerTwo="Header Two";
	private static String headerThree="Header Three";
	private static String headerFour="Header Four";
	private MenuManager testManagerOne;
	private MenuManager testManagerTwo;
	private MenuEvent testMenuEventOne;
	private MenuEvent testMenuEventTwo;
	private MenuEvent testMenuEventThree;
	private MenuEvent testMenuEventFour;
	private MenuItem testItemOne;
	private MenuItem testItemTwo;
	private MenuItem testItemThree;
	private MenuItem testItemFour;
	private MenuItem testItemFive;
	private MenuItem testItemSix;
	private ObservableTime time;
	private MenuDecorator menuWithHeaderOne;
	private MenuDecorator menuWithHeaderTwo;
	private MenuDecorator menuWithHeaderThree;
	private MenuDecorator menuWithHeaderFour;
	private String menuOneString;
	private String menuTwoString;
	private String menuThreeString;
	private String menuFourString;
	
	@BeforeEach
	public void setUp()
	{
		LocalDate newDate=LocalDate.of(2021, 1, 1);
		LocalTime newTime=LocalTime.of(7, 0);
		this.time=new ObservableTime(newTime, newDate);
		this.testItemOne=new MenuItem("One", 1, 1.00, 0, false);
		this.testItemTwo=new MenuItem("Two", 2, 2.00, 0, false);
		this.testItemThree=new MenuItem("Three", 3, 3.00, 0, false);
		this.testItemFour=new MenuItem("Four", 4, 4.00, 0, false);
		this.testItemFive=new MenuItem("Five", 5, 5.00, 0, false);
		this.testItemSix=new MenuItem("Six", 6, 6.00, 0, false);
		this.testMenuOne=(MenuData) new Menu();
		this.testMenuOne.addMenuItem(testItemOne);
		this.testMenuOne.addMenuItem(testItemTwo);
		this.testMenuTwo=(MenuData) new Menu();
		this.testMenuTwo.addMenuItem(testItemOne);
		this.testMenuTwo.addMenuItem(testItemThree);
		this.testMenuThree=(MenuData) new Menu();
		this.testMenuThree.addMenuItem(testItemTwo);
		this.testMenuThree.addMenuItem(testItemFour);
		this.testMenuThree.addMenuItem(testItemFive);
		this.testMenuFour=(MenuData) new Menu();
		this.testMenuFour.addMenuItem(testItemSix);
		this.testMenuFour.addMenuItem(testItemThree);
		this.testMenuFour.addMenuItem(testItemTwo);
		this.menuWithHeaderOne=new MenuHeader(testMenuOne, headerOne);
		this.menuWithHeaderTwo=new MenuHeader(testMenuTwo, headerTwo);
		this.menuWithHeaderThree=new MenuHeader(testMenuThree, headerThree);
		this.menuWithHeaderFour=new MenuHeader(testMenuFour, headerFour);
		this.menuOneString=menuWithHeaderOne.toString();
		this.menuTwoString=menuWithHeaderTwo.toString();
		this.menuThreeString=menuWithHeaderThree.toString();
		this.menuFourString=menuWithHeaderFour.toString();
	}
	
	
	@Test
	public void testMultipleMenuManagers()
	{
		LocalTime anotherTime=LocalTime.of(12, 0);
		this.time.setTime(anotherTime);
		LocalDate anotherDate=LocalDate.of(2020, 1, 2);
		this.time.setDate(anotherDate);
		this.testManagerOne=new MenuManager();
		this.testManagerTwo=new MenuManager();
		this.testMenuEventOne=new MenuEvent(this.menuWithHeaderOne, this.testManagerOne);
		this.testMenuEventTwo=new MenuEvent(this.menuWithHeaderTwo, this.testManagerOne);
		this.testMenuEventThree=new MenuEvent(this.menuWithHeaderThree, this.testManagerTwo);
		this.testMenuEventFour=new MenuEvent(this.menuWithHeaderFour, this.testManagerTwo);
		this.testMenuEventOne.setEventDay(1, 1);
		this.testMenuEventTwo.setEventDay(2, 1);
		this.testMenuEventThree.setEventTime(14, 0);
		this.testMenuEventFour.setEventTime(8, 0);
		this.testManagerOne.registerMenuEvent(testMenuEventOne);
		this.testManagerOne.registerMenuEvent(testMenuEventTwo);
		this.testManagerTwo.registerMenuEvent(testMenuEventThree);
		this.testManagerTwo.registerMenuEvent(testMenuEventFour);
		this.testManagerOne.setAvailableMenu(menuWithHeaderOne);
		this.testManagerTwo.setAvailableMenu(menuWithHeaderThree);
		this.time.register(testManagerOne);
		this.time.register(testManagerTwo);
		LocalTime newTime=LocalTime.of(8, 0);
		LocalTime newTimeTwo=LocalTime.of(14, 0);
		LocalDate newDate=LocalDate.of(2021, 1, 1);
		LocalDate newDateTwo=LocalDate.of(2021, 2, 1);
		this.time.setTime(newTime);
		Assertions.assertEquals(this.menuFourString, this.testManagerTwo.getAvailableMenu().toString());
		this.time.setDate(newDate);
		Assertions.assertEquals(this.menuOneString, this.testManagerOne.getAvailableMenu().toString());
		this.time.setTime(newTimeTwo);
		Assertions.assertEquals(this.menuThreeString, this.testManagerTwo.getAvailableMenu().toString());
		this.time.setDate(newDateTwo);
		Assertions.assertEquals(this.menuTwoString, this.testManagerOne.getAvailableMenu().toString());
		
		//TearDown Steps for 2 manager test
		this.testManagerOne.unregisterMenuEvent(0);
		this.testManagerOne.unregisterMenuEvent(0);
		this.testManagerTwo.unregisterMenuEvent(0);
		this.testManagerTwo.unregisterMenuEvent(0);
		this.time.unregister(testManagerOne);
		this.time.unregister(testManagerTwo);
		this.testManagerOne=null;
		this.testManagerTwo=null;
		
		
	}
	
	@Test
	public void testDateMenuChange()
	{
		LocalDate anotherDate=LocalDate.of(2020, 2, 1);
		this.time.setDate(anotherDate);
		this.testManagerOne=new MenuManager();
		this.testMenuEventOne=new MenuEvent(this.menuWithHeaderOne, this.testManagerOne);
		this.testMenuEventTwo=new MenuEvent(this.menuWithHeaderTwo, this.testManagerOne);
		this.testMenuEventOne.setEventDay(2, 1);
		this.testMenuEventTwo.setEventDay(3, 2);
		this.testManagerOne.registerMenuEvent(testMenuEventOne);
		this.testManagerOne.registerMenuEvent(testMenuEventTwo);
		this.testManagerOne.setAvailableMenu(this.menuWithHeaderOne);
		this.time.register(testManagerOne);
		Assertions.assertEquals(this.menuOneString, this.testManagerOne.getAvailableMenu().toString());
		LocalDate newDate=LocalDate.of(2020, 3, 2);
		this.time.setDate(newDate);
		Assertions.assertEquals(this.menuTwoString, this.testManagerOne.getAvailableMenu().toString());
		
		//Tear Down steps for one manager test
		this.testManagerOne.unregisterMenuEvent(0);
		this.testManagerOne.unregisterMenuEvent(0);
		this.time.unregister(testManagerOne);
		this.testManagerOne=null;
		
		
	}
	
	@Test
	public void testTimeMenuChange()
	{
		LocalTime anotherTime=LocalTime.of(12, 45);
		this.time.setTime(anotherTime);
		this.testManagerTwo=new MenuManager();
		this.testMenuEventThree=new MenuEvent(this.menuWithHeaderThree, this.testManagerTwo);
		this.testMenuEventFour=new MenuEvent(this.menuWithHeaderFour, this.testManagerTwo);
		this.testMenuEventThree.setEventTime(8, 30);
		this.testMenuEventFour.setEventTime(12, 45);
		this.testManagerTwo.registerMenuEvent(testMenuEventThree);
		this.testManagerTwo.registerMenuEvent(testMenuEventFour);
		this.testManagerTwo.setAvailableMenu(this.menuWithHeaderFour);
		this.time.register(testManagerTwo);
		Assertions.assertEquals(this.menuFourString, this.testManagerTwo.getAvailableMenu().toString());
		LocalTime newTime=LocalTime.of(8, 30);
		this.time.setTime(newTime);
		Assertions.assertEquals(this.menuThreeString, this.testManagerTwo.getAvailableMenu().toString());
	
		//Tear down steps for one manager test
		this.testManagerTwo.unregisterMenuEvent(0);
		this.testManagerTwo.unregisterMenuEvent(0);
		this.time.unregister(testManagerTwo);
		this.testManagerTwo=null;

		
	}
}
