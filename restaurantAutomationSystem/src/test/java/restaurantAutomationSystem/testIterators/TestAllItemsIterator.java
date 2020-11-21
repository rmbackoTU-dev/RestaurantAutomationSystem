package restaurantAutomationSystem.testIterators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import restaurantAutomationSystem.restaurantData.Menu;
import restaurantAutomationSystem.restaurantData.MenuItem;
import restaurantAutomationSystem.restaurantData.RestaurantIterator;

import java.math.BigDecimal;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

public class TestAllItemsIterator {
	
	@Test
	public void testAllItemsConstructor()
	{
		Menu testMenu=new Menu();
		MenuItem testMenuItem=new MenuItem("test1",
				3, 10.99, 2, false);
		testMenu.addMenuItem(testMenuItem);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		MenuItem actualMenuItem=(MenuItem) allItems.next();
		String expectedName="test1";
		String expectedCategory="Dessert";
		String expectedPrice="10.99";
		Assertions.assertEquals(expectedName, actualMenuItem.getDescription());
		Assertions.assertEquals(expectedCategory, actualMenuItem.getCategory());
		Assertions.assertFalse(actualMenuItem.isHeartHealthy());
		Assertions.assertEquals(expectedPrice, actualMenuItem.getPrice());
	}
	
	@Test
	public void testWithoutItemsAllItemsConstructor()
	{
			Menu testMenu=new Menu();
			Assertions.assertThrows(NullPointerException.class, () ->
			{
				RestaurantIterator allItems=testMenu.getAllItemsIterator();
			});
	}
	
	@Test
	public void testHasNextFalse()
	{
		Menu testMenu=new Menu();
		MenuItem testMenuItem=new MenuItem("test1",
				3, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItem);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		allItems.next();
		Assertions.assertFalse(allItems.hasNext());
	}
	
	@Test
	public void testHasNextTrue()
	{
		Menu testMenu=new Menu();
		MenuItem testMenuItem=new MenuItem("test1",
				3, 1.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				3, 2.99, 0, false);
		testMenu.addMenuItem(testMenuItem);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		Assertions.assertTrue(allItems.hasNext());
	}
	
	@Test
	public void testNextIllegalState()
	{
		Menu testMenu=new Menu();
		MenuItem testMenuItem=new MenuItem("test1",
				3, 1.99, 0, false);
		testMenu.addMenuItem(testMenuItem);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		allItems.next();
		Assertions.assertThrows(IllegalStateException.class,  () -> 
		{
			allItems.next();
		});
		
	}
	
	@Test
	public void testNextLegalState()
	{
		Menu testMenu=new Menu();
		MenuItem testMenuItem=new MenuItem("test1",
				3, 1.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				3, 2.99, 0, false);
		testMenu.addMenuItem(testMenuItem);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		allItems.next();
		MenuItem currentMenuItem=(MenuItem) allItems.next();
		Assertions.assertEquals("test2", currentMenuItem.getDescription());
	}
	
	
	@Test
	public void testGetWithItem()
	{
		Menu testMenu=new Menu();
		MenuItem testMenuItem=new MenuItem("test1",
				3, 1.99,  0, false);
		testMenu.addMenuItem(testMenuItem);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		MenuItem currentMenuItem=(MenuItem) allItems.next();
		Assertions.assertEquals("test1", currentMenuItem.getDescription());
	}
	
	@Test
	public void removeFromIter()
	{
		Menu testMenu=new Menu();
		MenuItem testMenuItem=new MenuItem("test1",
				3, 1.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				3, 2.99 , 0, false);
		testMenu.addMenuItem(testMenuItem);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		allItems.next();
		testMenu.deleteMenuItem(allItems);
		MenuItem currentMenuItem=(MenuItem) allItems.next();
		Assertions.assertEquals("test2", currentMenuItem.getDescription());
		Assertions.assertFalse(allItems.hasNext());
	}
	
	
	@Test
	public void resetIterTest()
	{
		Menu testMenu=new Menu();
		MenuItem tempMenuItem;
		MenuItem testMenuItem=new MenuItem("test1",
				3, 1.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				3, 2.99, 0, false);
		testMenu.addMenuItem(testMenuItem);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator allItems= testMenu.getAllItemsIterator();
		allItems.next();
		tempMenuItem=(MenuItem) allItems.next();
		Assertions.assertEquals(tempMenuItem.getDescription(), "test2");
		allItems.resetIter();
		tempMenuItem=(MenuItem) allItems.next();
		Assertions.assertEquals(tempMenuItem.getDescription(), "test1");
		
	}
}
