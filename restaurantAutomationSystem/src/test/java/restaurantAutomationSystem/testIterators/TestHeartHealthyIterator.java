package restaurantAutomationSystem.testIterators;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import restaurantAutomationSystem.restaurantData.Menu;
import restaurantAutomationSystem.restaurantData.MenuItem;
import restaurantAutomationSystem.restaurantData.RestaurantIterator;

public class TestHeartHealthyIterator {

	private Menu testMenu;

	@Test
	public void testConstructorNoItems()
	{
		testMenu=new Menu();
		Assertions.assertThrows(NullPointerException.class, () ->
		{
			RestaurantIterator testIter=testMenu.getHeartHealthyIterator(false);
		});
	}
		
	
	@Test
	public void testConstructorNonHeartHealthyItemsDoNotMatch()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 9.99, 3, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIter=testMenu.getHeartHealthyIterator(true);
		Assertions.assertFalse(testIter.hasNext());
	}
	
	
	@Test
	public void testConstructorHeartHealthyItemsDoNotMatch()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				3, 9.99, 0, true );
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIter=testMenu.getHeartHealthyIterator(false);
		Assertions.assertFalse(testIter.hasNext());
	}
	
	@Test
	public void testConstructorwithNonHeartHealthItem()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertFalse( testItem.isHeartHealthy());
		Assertions.assertEquals("test1", testItem.getDescription());
	}
	
	@Test
	public void testConstructorwithHeartHealthItem()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, true);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(true);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertTrue(testItem.isHeartHealthy());
		Assertions.assertEquals("test1", testItem.getDescription());
	}
	
	@Test
	public void testConstructorwithMultipleHeartHealthyItems()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, true);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 0, true);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(true);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertTrue(testItem.isHeartHealthy());
		Assertions.assertEquals("test1", testItem.getDescription());
		testItem=(MenuItem) testIterator.next();
		Assertions.assertTrue(testItem.isHeartHealthy());
		Assertions.assertEquals("test2", testItem.getDescription());
	}
	
	@Test
	public void testConstructorwithMultipleNonHeartHealthyItems()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertFalse(testItem.isHeartHealthy());
		Assertions.assertEquals("test1", testItem.getDescription());
		testItem=(MenuItem) testIterator.next();
		Assertions.assertFalse(testItem.isHeartHealthy());
		Assertions.assertEquals("test2", testItem.getDescription());
	}
	
	
	@Test
	public void testConstructorwithOneNonHeartHealthyItem()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 9.99,  0, false );
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 9.99, 0,  true);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertFalse(testItem.isHeartHealthy());
		Assertions.assertEquals("test1", testItem.getDescription());
		Assertions.assertFalse(testIterator.hasNext());
	}
	
	

	@Test
	public void testConstructorwithOneHeartHealthyItem()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				1, 9.99, 0, true);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(true);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertTrue(testItem.isHeartHealthy());
		Assertions.assertEquals("test2", testItem.getDescription());
		Assertions.assertFalse(testIterator.hasNext());
	}
	
	@Test
	public void testHasNextFalse()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(true);
		Assertions.assertFalse(testIterator.hasNext());
	}
	
	@Test
	public void testHasNextTrue()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		Assertions.assertTrue(testIterator.hasNext());
	}
	
	@Test
	public void testNextIllegalState()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		testIterator.next();
		Assertions.assertThrows(IllegalStateException.class, () -> {
			testIterator.next();
		});
	}
	
	@Test
	public void testNextLegalState()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		testIterator.next();
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertEquals("test2", testItem.getDescription());
	}
	
	@Test
	public void testGetWithItem()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertEquals("test1", testItem.getDescription());
	}
	
	
	@Test
	public void removeFromIter()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(false);
		testIterator.next();
		testMenu.deleteMenuItem(testIterator);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertEquals("test2", testItem.getDescription());
	}
	
	
	@Test
	public void removeFromEndOfIter()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 0, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 9.99, 0, true);
		MenuItem testMenuItemThree=new MenuItem("test3",
				3, 9.99, 1, false);
		MenuItem testMenuItemFour=new MenuItem("test4",
				4, 12.99, 2, true);
		MenuItem testMenuItemFive=new MenuItem("test5",
				5, 9.99, 2, true);
		MenuItem testMenuItemSix=new MenuItem("test6",
				6, 9.99, 2, true);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		testMenu.addMenuItem(testMenuItemThree);
		testMenu.addMenuItem(testMenuItemFour);
		testMenu.addMenuItem(testMenuItemFive);
		testMenu.addMenuItem(testMenuItemSix);
		RestaurantIterator testIterator=testMenu.getHeartHealthyIterator(true);
		for(int i=0; i<4; i++)
		{
			testIterator.next();
		}
		testMenu.deleteMenuItem(testIterator);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertEquals("test5", testItem.getDescription());
	}
}
