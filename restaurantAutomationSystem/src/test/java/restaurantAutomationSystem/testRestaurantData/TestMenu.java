package restaurantAutomationSystem.testRestaurantData;

import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuItem;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

public class TestMenu {

	private Menu testMenu;
	
	@AfterEach
	public void tearDown()
	{
		this.testMenu=null;
	}
	
	@Test
	public void testDefaultConstructor() 
	{
		this.testMenu=new Menu();
		Assert.assertEquals(0, this.testMenu.size());
	}
	
	@Test
	public void testParamConstructorException() 
	{
		
		int testSize=0;
		Assertions.assertThrows(IllegalArgumentException.class, () -> 
		{
			this.testMenu=new Menu(testSize);
		});
	}
	
	@Test
	public void testCopyConstructor()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		MenuItem twoItem=new MenuItem("Fish", 2, 5.99, 1, true);
		MenuItem threeItem=new MenuItem("Bananas", 3, 6.99, 0, true);
		this.testMenu.addMenuItem(oneItem);
		this.testMenu.addMenuItem(twoItem);
		this.testMenu.addMenuItem(threeItem);
		Menu aCopyMenu=new Menu(this.testMenu);
		RestaurantIterator aCopyMenuIterator=aCopyMenu.getAllItemsIterator();
		int currentIndex;
		String[] expectedDescriptions= {"Rice", "Fish", "Bananas"};
		MenuItem actualItem;
		while(aCopyMenuIterator.hasNext())
		{
			actualItem=(MenuItem) aCopyMenuIterator.next();
			currentIndex=aCopyMenuIterator.getCurrentIndex();
			Assertions.assertEquals(expectedDescriptions[currentIndex], 
					actualItem.getDescription());
		}
		
	}
	
	@Test
	public void testAdd() 
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		this.testMenu.addMenuItem(oneItem);
		RestaurantIterator aMenuIterator=this.testMenu.getAllItemsIterator();
		MenuItem actualItem=(MenuItem) aMenuIterator.next();
		Assertions.assertEquals("Rice", actualItem.getDescription());
	}
	
	@Test
	public void testDeleteOneItem()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		this.testMenu.addMenuItem(oneItem);
		RestaurantIterator aMenuIterator=this.testMenu.getAllItemsIterator();
		aMenuIterator.next();
		this.testMenu.deleteMenuItem(aMenuIterator);
		Assertions.assertThrows(IllegalStateException.class, () ->
		{
			aMenuIterator.next();
		});
		
	}
	
	@Test
	public void testDeleteTwoItemsLastDelete()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		MenuItem twoItem=new MenuItem("Fish", 2, 1.99, 1, true);
		this.testMenu.addMenuItem(oneItem);
		this.testMenu.addMenuItem(twoItem);
		RestaurantIterator aMenuIterator=this.testMenu.getAllItemsIterator();
		aMenuIterator.next();
		aMenuIterator.next();
		this.testMenu.deleteMenuItem(aMenuIterator);
		MenuItem currentItem=(MenuItem) aMenuIterator.next();
		Assertions.assertEquals("Rice", currentItem.getDescription());
		Assertions.assertThrows(IllegalStateException.class, () -> 
		{
			aMenuIterator.next();
		});
	}
	
	@Test
	public void testDeleteTwoItemsfirstDelete()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		MenuItem twoItem=new MenuItem("Fish", 2, 1.99, 1 , true);
		this.testMenu.addMenuItem(oneItem);
		this.testMenu.addMenuItem(twoItem);
		RestaurantIterator aMenuIterator=this.testMenu.getAllItemsIterator();
		aMenuIterator.next();
		this.testMenu.deleteMenuItem(aMenuIterator);
		MenuItem currentItem=(MenuItem) aMenuIterator.next();
		Assertions.assertEquals("Fish", currentItem.getDescription());
	}
	
	@Test
	public void testDeleteThreeItemsMiddleDelete()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		MenuItem twoItem=new MenuItem("Fish", 2, 1.99, 1, true);
		MenuItem threeItem=new MenuItem("Bananas", 3, 1.50, 2, true);
		this.testMenu.addMenuItem(oneItem);
		this.testMenu.addMenuItem(twoItem);
		this.testMenu.addMenuItem(threeItem);
		RestaurantIterator aMenuIterator=this.testMenu.getAllItemsIterator();
		aMenuIterator.next();
		aMenuIterator.next();
		this.testMenu.deleteMenuItem(aMenuIterator);
		MenuItem currentItem=(MenuItem) aMenuIterator.next();
		Assertions.assertEquals("Rice", currentItem.getDescription());
		currentItem=(MenuItem) aMenuIterator.next();
		Assertions.assertEquals("Bananas", currentItem.getDescription());
	}
	
	@Test
	public void testSize()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		MenuItem twoItem=new MenuItem("Fish", 2, 1.99, 1, true);
		MenuItem threeItem=new MenuItem("Bananas", 3, 1.50, 2, true);
		testMenu.addMenuItem(oneItem);
		testMenu.addMenuItem(twoItem);
		testMenu.addMenuItem(threeItem);
		Assertions.assertEquals(3, this.testMenu.size());
	}
	
	@Test
	public void testIsOrderInMenu()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		MenuItem twoItem=new MenuItem("Fish", 2, 1.99, 1, true);
		MenuItem threeItem=new MenuItem("Bananas", 3, 1.50, 2, true);
		testMenu.addMenuItem(oneItem);
		testMenu.addMenuItem(twoItem);
		testMenu.addMenuItem(threeItem);
		Assertions.assertTrue(testMenu.isOrderInMenu(2));
	}
	
	@Test
	public void testIsOrderInMenuFalse()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		MenuItem twoItem=new MenuItem("Fish", 2, 1.99, 1, true);
		MenuItem threeItem=new MenuItem("Bananas", 3, 1.50, 2, true);
		testMenu.addMenuItem(oneItem);
		testMenu.addMenuItem(twoItem);
		testMenu.addMenuItem(threeItem);
		Assertions.assertFalse(testMenu.isOrderInMenu(4));
	}
	
	@Test 
	public void testDeleteWithoutNext()
	{
		this.testMenu=new Menu();
		MenuItem oneItem=new MenuItem("Rice", 1, 0.99, 0, true);
		this.testMenu.addMenuItem(oneItem);
		RestaurantIterator aMenuIterator=this.testMenu.getAllItemsIterator();
		Assertions.assertThrows(IllegalStateException.class, () ->
		{
			this.testMenu.deleteMenuItem(aMenuIterator);
		});
	}
	
	@Test
	public void testToString()
	{
		this.testMenu=new Menu();
		MenuItem testOne=new MenuItem("Wings", 1, 5.99, 0, false);
		MenuItem testTwo=new MenuItem("Jalapeno Poppers", 2, 4.99, 0, false);
		MenuItem testThree=new MenuItem("Pizza", 3, 10.99, 1, false);
		MenuItem testFour=new MenuItem("Cheese Burger", 4, 8.99, 1, false);
        MenuItem testFive=new MenuItem("Burger", 6, 7.99, 1, false);
        MenuItem testSix=new MenuItem("Bananas", 5, 1.00, 2, true);
        MenuItem testSeven=new MenuItem("Apple Pie", 7, 3.99, 2, false);
        MenuItem testEight=new MenuItem("Ice Cream Sundae", 9, 5.99, 2, false);
        MenuItem testNine=new MenuItem("Coke",8, 1.99, 3, false);
        MenuItem testTen=new MenuItem("Pepsi",10, 1.99, 3, false);
        MenuItem testEleven=new MenuItem("Water", 11, 0.50, 3, true);
        this.testMenu.addMenuItem(testOne);
        this.testMenu.addMenuItem(testTwo);
        this.testMenu.addMenuItem(testThree);
        this.testMenu.addMenuItem(testFour);
        this.testMenu.addMenuItem(testSix);
        this.testMenu.addMenuItem(testFive);
        this.testMenu.addMenuItem(testSeven);
        this.testMenu.addMenuItem(testNine);
        this.testMenu.addMenuItem(testEight);
        this.testMenu.addMenuItem(testTen);
        this.testMenu.addMenuItem(testEleven);
		String MenuHeader="\t\tMENU\t\t\n\n\n";
		String AppetizerHeader="Appetizers: \n\n\n";
		String MainDishHeader="\nMain Dishes: \n\n\n";
		String DessertHeader="\nDesserts: \n\n\n";
		String DrinkHeader="\nDrinks: \n\n\n";
		String[] AppetizerString=
			{
					"\t1: Wings\n"
					+ "$5.99\n\n",
					"\t2: Jalapeno Poppers\n"
					+"$4.99\n\n"
			};
		String[] MainDishString=
			{
					"\t3: Pizza\n"
					+"$10.99\n\n",
					"\t4: Cheese Burger\n"
					+"$8.99\n\n",
					"\t6: Burger\n"
					+"$7.99\n\n"
			};
		String[] DessertString=
			{
					"\t5: Bananas\n"
					+"( Heart Healthy )\n"
					+"$1.00\n\n",
					"\t7: Apple Pie\n"
					+"$3.99\n\n",
					"\t9: Ice Cream Sundae\n"
					+"$5.99\n\n"
			};
		String[] DrinkString=
			{
					"\t8: Coke\n"
					+"$1.99\n\n",
					"\t10: Pepsi\n"
					+"$1.99\n\n",
					"\t11: Water\n"
					+"( Heart Healthy )\n"
					+"$0.50\n\n"
			};
		String expectedString=MenuHeader;
		expectedString=expectedString+AppetizerHeader;
		for(int i=0; i< AppetizerString.length; i++)
		{
			expectedString=expectedString+AppetizerString[i];
		}
		expectedString=expectedString+MainDishHeader;
		for(int j=0; j< MainDishString.length; j++)
		{
			expectedString=expectedString+MainDishString[j];
		}
		expectedString=expectedString+DessertHeader;
		for(int k=0; k< MainDishString.length; k++)
		{
			expectedString=expectedString+DessertString[k];
		}
		expectedString=expectedString+DrinkHeader;
		for(int l=0; l< MainDishString.length; l++)
		{
			expectedString=expectedString+DrinkString[l];
		}
		String actualString=testMenu.toString();
		Assertions.assertEquals(expectedString, actualString);
	}
	
	
	
}
