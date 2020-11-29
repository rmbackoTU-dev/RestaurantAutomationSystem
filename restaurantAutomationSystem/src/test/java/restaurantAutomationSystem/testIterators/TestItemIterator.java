package restaurantAutomationSystem.testIterators;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuItem;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;
import restaurantAutomationSystem.model.restaurantData.Menu.AllItemsIterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class TestItemIterator {
	
		private Menu testMenu;

		@Test
		public void testConstructorNoItem()
		{
			testMenu=new Menu();
			Assertions.assertThrows(NullPointerException.class, () ->
			{
				RestaurantIterator testIterator=testMenu.getItemIterator(0);
			});
		}
			
		
		@Test
		public void testConstructorNoItemsOfCategory()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 5.99, 1, false);
			testMenu.addMenuItem(testMenuItemOne);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			Assertions.assertThrows(IllegalStateException.class, () ->
			{
				testIterator.next();
			});
		}
		
		@Test
		public void testConstructorwithItemOfCategory()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			MenuItem testItem=(MenuItem) testIterator.next();
			Assertions.assertEquals("Appetizer", testItem.getCategory());
			Assertions.assertEquals("test1", testItem.getDescription());
		}
		
		@Test
		public void testConstructorwithMultipleItemsOfCategory()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			MenuItem testMenuItemTwo=new MenuItem("test2",
					2, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			testMenu.addMenuItem(testMenuItemTwo);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			MenuItem testItem=(MenuItem) testIterator.next();
			Assertions.assertEquals("Appetizer", testItem.getCategory());
			Assertions.assertEquals("test1", testItem.getDescription());
			testItem=(MenuItem) testIterator.next();
			Assertions.assertEquals("Appetizer", testItem.getCategory());
			Assertions.assertEquals("test2", testItem.getDescription());
		}
		
		
		@Test
		public void testHasNextFalse()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			RestaurantIterator testIterator=testMenu.getItemIterator(1);
			Assertions.assertFalse(testIterator.hasNext());
		}
	
		@Test
		public void testHasNextFalseInCategory()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			testIterator.next();
			Assertions.assertFalse(testIterator.hasNext());
		}
		
		@Test
		public void testHasNextTrue()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			MenuItem testMenuItemTwo=new MenuItem("test2",
					1, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			testMenu.addMenuItem(testMenuItemTwo);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			testIterator.next();
			Assertions.assertTrue(testIterator.hasNext());
		}
	
		
		@Test
		public void testHasNextTrueFromInit()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			Assertions.assertTrue(testIterator.hasNext());
		}
		@Test
		public void testNextIllegalState()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			testIterator.next();
			Assertions.assertThrows(IllegalStateException.class, () -> 
			{
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
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			MenuItem testItem=(MenuItem) testIterator.next();
			Assertions.assertEquals("test1", testItem.getDescription());
		}
		
		@Test
		public void testGetWithItem()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
			MenuItem testItem=(MenuItem) testIterator.next();
			Assertions.assertEquals("test1", testItem.getDescription());
		}
		
		@Test
		public void testGetEmptyIter()
		{
			testMenu=new Menu();
			Assertions.assertThrows(NullPointerException.class, () -> 
			{
				testMenu.getItemIterator(0);
			});
		}
		
		@Test
		public void removeFromIter()
		{
			testMenu=new Menu();
			MenuItem testMenuItemOne=new MenuItem("test1",
					1, 10.99, 0, false);
			MenuItem testMenuItemTwo=new MenuItem("test2",
					2, 10.99, 0, false);
			testMenu.addMenuItem(testMenuItemOne);
			testMenu.addMenuItem(testMenuItemTwo);
			RestaurantIterator testIterator=testMenu.getItemIterator(0);
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
					1, 10.99, 1, false);
			MenuItem testMenuItemTwo=new MenuItem("test2",
					2, 9.99, 0, false);
			MenuItem testMenuItemThree=new MenuItem("test3",
					3, 9.99, 1, false);
			MenuItem testMenuItemFour=new MenuItem("test4",
					4, 12.99, 0, true);
			MenuItem testMenuItemFive=new MenuItem("test5",
					5, 9.99, 1, true);
			MenuItem testMenuItemSix=new MenuItem("test6",
					6, 9.99, 0 , true);
			testMenu.addMenuItem(testMenuItemOne);
			testMenu.addMenuItem(testMenuItemTwo);
			testMenu.addMenuItem(testMenuItemThree);
			testMenu.addMenuItem(testMenuItemFour);
			testMenu.addMenuItem(testMenuItemFive);
			testMenu.addMenuItem(testMenuItemSix);
			RestaurantIterator testIterator=testMenu.getItemIterator(1);
			testIterator.next();
			testIterator.next();
			testMenu.deleteMenuItem(testIterator);
			MenuItem testItem=(MenuItem) testIterator.next();
			Assert.assertEquals("test3", testItem.getDescription());
		}
}
