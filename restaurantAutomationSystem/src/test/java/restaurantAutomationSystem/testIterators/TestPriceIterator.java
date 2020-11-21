package restaurantAutomationSystem.testIterators;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import restaurantAutomationSystem.restaurantData.Menu;
import restaurantAutomationSystem.restaurantData.MenuItem;
import restaurantAutomationSystem.restaurantData.RestaurantIterator;

public class TestPriceIterator {
	
	private Menu testMenu;
	private static BigDecimal expectedBigDecimalPrice;
	
	@BeforeAll
	public static void setUp()
	{
		expectedBigDecimalPrice=new BigDecimal("10.99");
		expectedBigDecimalPrice=expectedBigDecimalPrice.setScale(2, RoundingMode.CEILING);
	}

	@Test
	public void testConstructorNoItems()
	{
		testMenu=new Menu();
		Assertions.assertThrows(NullPointerException.class, ()-> {
			RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		});
	}
		
	
	@Test
	public void testConstructorNoItemsOfCategory()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 9.99, 3, false );
		testMenu.addMenuItem(testMenuItemOne);		
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		Assertions.assertThrows(IllegalStateException.class, () ->
		{
			testIterator.next();
		});
	}
	
	@Test
	public void testConstructorwithMatchingPrice()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1 , false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		MenuItem testItem=(MenuItem)testIterator.next();
		Assertions.assertTrue(expectedBigDecimalPrice.toPlainString().equals(testItem.getPrice()));
		Assertions.assertEquals("test1", testItem.getDescription());
	}
	
	@Test
	public void testConstructorwithMultiplewithMatchingPrice()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertTrue(expectedBigDecimalPrice.toPlainString().equals(testItem.getPrice()));
		Assertions.assertEquals("test1", testItem.getDescription());
		testItem=(MenuItem) testIterator.next();
		Assertions.assertTrue(expectedBigDecimalPrice.toPlainString().equals(testItem.getPrice()));
		Assertions.assertEquals("test2", testItem.getDescription());
	}
	
	
	@Test
	public void testConstructorwithOneItemWithMatchingPrice()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertTrue(expectedBigDecimalPrice.toPlainString().equals(testItem.getPrice()));
		Assertions.assertEquals("test1", testItem.getDescription());
		testIterator.next();
		Assertions.assertFalse(testIterator.hasNext());
	}
	
	@Test
	public void testHasNextFalse()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		testIterator.next();
		Assertions.assertFalse(testIterator.hasNext());
	}
	
	@Test
	public void testHasNextTrue()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		Assertions.assertTrue(testIterator.hasNext());
	}
	
	@Test
	public void testNextIllegalState()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
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
				1, 10.99 ,1, false);
		MenuItem testMenuItemTwo=new MenuItem("test2",
				2, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertEquals("test1", testItem.getDescription());
	}
	
	@Test
	public void testGetWithItem()
	{
		testMenu=new Menu();
		MenuItem testMenuItemOne=new MenuItem("test1",
				1, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
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
				1, 10.99, 1, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		RestaurantIterator testIterator=testMenu.getPriceIterator(10.99);
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
				1, 9.99, 1, false);
		MenuItem testMenuItemThree=new MenuItem("test3",
				2, 9.99, 2, false);
		MenuItem testMenuItemFour=new MenuItem("test4",
				1, 12.99, 1, false);
		MenuItem testMenuItemFive=new MenuItem("test5",
				2, 9.99, 2, false);
		MenuItem testMenuItemSix=new MenuItem("test6",
				2, 9.99, 2, false);
		testMenu.addMenuItem(testMenuItemOne);
		testMenu.addMenuItem(testMenuItemTwo);
		testMenu.addMenuItem(testMenuItemThree);
		testMenu.addMenuItem(testMenuItemFour);
		testMenu.addMenuItem(testMenuItemFive);
		testMenu.addMenuItem(testMenuItemSix);
		RestaurantIterator testIterator=testMenu.getPriceIterator(9.99);
		testIterator.next();
		testIterator.next();
		testIterator.next();
		testMenu.deleteMenuItem(testIterator);
		MenuItem testItem=(MenuItem) testIterator.next();
		Assertions.assertEquals("test6", testItem.getDescription());
	}
}
