package restaurantAutomationSystem.testRestaurantData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import restaurantAutomationSystem.restaurantData.MenuItem;

public class TestMenuItem {

	@Test
	public void  testDefaultConstructor()
	{
		MenuItem testItem=new MenuItem();
		String actualDescription=testItem.getDescription();
		int actualOrderNumber=testItem.getOrderNumber();
		String actualPrice=testItem.getPrice();
		Assertions.assertEquals("", actualDescription);
		Assertions.assertEquals(0, actualOrderNumber);
		Assertions.assertEquals("0.00", actualPrice);
	}
	
	@Test
	public void testCopyConstructor()
	{
		MenuItem testItemToCopy=new MenuItem("Cheese Steak", 1, 10.99, 0, false);
		MenuItem testItem=new MenuItem(testItemToCopy);
		String actualDescription=testItem.getDescription();
		int actualOrderNumber=testItem.getOrderNumber();
		String actualPrice=testItem.getPrice();
		Assertions.assertEquals("Cheese Steak", actualDescription);
		Assertions.assertEquals(1, actualOrderNumber);
		Assertions.assertEquals("10.99", actualPrice);
	}
	
	
	//TODO update for two new parameters
	@Test
	public void testParamConstructor()
	{
		MenuItem testItem=new MenuItem("Cheese Steak", 1, 10.99, 0, false);
		String actualDescription=testItem.getDescription();
		int actualOrderNumber=testItem.getOrderNumber();
		String actualPrice=testItem.getPrice();
		Assertions.assertEquals("Cheese Steak", actualDescription);
		Assertions.assertEquals(1, actualOrderNumber);
		Assertions.assertEquals("10.99", actualPrice);
	}
	
	@Test
	public void testGetItemNumber()
	{
		MenuItem testItem=new MenuItem("Cheese Steak", 1, 10.99, 0, false);
		int actualOrderNumber=testItem.getOrderNumber();
		Assertions.assertEquals(1, actualOrderNumber);
	}
	
	@Test
	public void testGetDescription()
	{
		MenuItem testItem=new MenuItem("Cheese Steak", 1, 10.99, 0, false);
		String actualDescription=testItem.getDescription();
		Assertions.assertEquals("Cheese Steak", actualDescription);
	}
	
	@Test
	public void testGetPrice()
	{
		MenuItem testItem=new MenuItem("Cheese Steak", 1, 10.99, 0, false);
		String actualPrice=testItem.getPrice();
		Assertions.assertEquals("10.99", actualPrice);
	}
	
	@Test
	public void testToString()
	{
		MenuItem testItem=new MenuItem("Cheese Steak", 1, 10.99, 0, false);
		String itemString=testItem.toString();
		Assertions.assertEquals("1: Cheese Steak\n$10.99", itemString);
		MenuItem testItemTwo=new MenuItem("Celery", 1, 10.99, 0, true);
		String itemStringTwo=testItemTwo.toString();
		Assertions.assertEquals("1: Celery\n( Heart Healthy )\n$10.99", itemStringTwo);
	}
	
	@Test
	public void testIsHeartHealthy()
	{
		MenuItem testHealthTrue=new MenuItem("Grapes", 1, 10.99, 0, true);
		Assertions.assertTrue(testHealthTrue.isHeartHealthy());
        MenuItem testHealthFalse=new MenuItem("Cheese Steak", 1, 10.99, 0, false);
		Assertions.assertFalse(testHealthFalse.isHeartHealthy());
	}
	
	@Test
	public void getCategory()
	{
		MenuItem testAppetizer=new MenuItem("Grapes", 1, 10.99, 0, true);
		Assertions.assertEquals(testAppetizer.getCategory(), "Appetizer");
        MenuItem testMainCourse=new MenuItem("Cheese Steak", 1, 10.99, 1, false);
		Assertions.assertEquals(testMainCourse.getCategory(), "Main Dish");
		MenuItem testDessert=new MenuItem("Cake", 1, 10.99, 2, false);
		Assertions.assertEquals(testDessert.getCategory(), "Dessert");
        MenuItem testDrink=new MenuItem("Coke", 1, 10.99, 3, false);
		Assertions.assertEquals(testDrink.getCategory(), "Drink");
		MenuItem testInvalid=new MenuItem("N/A", 1, 10.99, 4, false);
		Assertions.assertEquals(testInvalid.getCategory(), "Unknown Category");

	}
}
