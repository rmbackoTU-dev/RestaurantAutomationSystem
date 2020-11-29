package restaurantAutomationSystem.testRestaurantData;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuItem;
import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.OrderItem;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class TestTab {

		private final static String LINESPACE="\n\n";
		private final static char TABCHAR='\t';
		private final static String TABHEADING="Tables tab:";
		private final static String boilerPlate=TestTab.TABHEADING+TestTab.LINESPACE;
				
	
		@Test
		public void testTabParameterConstructor()
		{
			Menu aMenu=new Menu();
			Order aOrder=new Order();
			Tab aTab=new Tab(aMenu, aOrder);
			Assertions.assertNotNull(aTab);
		}
		
		@Test
		public void testTabNullOrderParameterConstructor()
		{
			Menu aMenu=new Menu();
			Order aOrder=null;
			Assertions.assertThrows(IllegalArgumentException.class, () ->
			{
				Tab aTab=new Tab(aMenu, aOrder);
			});
		}
		
		@Test
		public void testTabNullMenuParameterConstructor()
		{
			Menu aMenu=null;
			Order aOrder=new Order();
			Assertions.assertThrows(IllegalArgumentException.class, () ->
			{
				Tab aTab=new Tab(aMenu, aOrder);
			});
		}
		
		@Test
		public void testTabNotFoundOrderNumberParameterConstructor()
		{
			Menu aMenu=new Menu();
			Order aOrder=new Order();
			MenuItem menuItemOne=new MenuItem("Bananas", 2, 2.99, 2, true);
			OrderItem orderItemOne=new OrderItem(1, 2);
			aMenu.addMenuItem(menuItemOne);
			aOrder.addOrderItem(orderItemOne);
			Assertions.assertThrows(IllegalArgumentException.class, () ->
			{
				Tab aTab=new Tab(aMenu, aOrder);
			});
		}
		
		//Subsumes toString test
		@Test
		public void testGenerateTabOneItem()
		{
			String itemDescription="Bananas";
			String TabItemString=TestTab.boilerPlate+
					TestTab.TABCHAR+"Item 1: "+
					itemDescription+"\n"+
					"( Heart Healthy )"+"\n$2.99"+
			 	    TestTab.LINESPACE+TestTab.LINESPACE+"Total Price:"+TestTab.TABCHAR+"$2.99";
			Menu aMenu=new Menu();
			Order aOrder=new Order();
			MenuItem menuItemOne=new MenuItem(itemDescription, 1, 2.99, 2, true);
			OrderItem orderItemOne=new OrderItem(1, 2);
			aMenu.addMenuItem(menuItemOne);
			aOrder.addOrderItem(orderItemOne);
			Tab aTab=new Tab(aMenu, aOrder);
			Assertions.assertEquals(TabItemString, aTab.toString());
		}
		
		//Subsumes toString test
		@Test
		public void testGenerateTabTwoItems()
		{
			String[] itemDescription= {"Bananas", "Tofu"};
			String TabItemString=TestTab.boilerPlate+
					TestTab.TABCHAR+"Item 1: "+
				    itemDescription[0]+"\n"+
				    "( Heart Healthy )\n"+
				    "$2.99"+
				    TestTab.LINESPACE+TestTab.TABCHAR+"Item 2: "+
				    itemDescription[1]+"\n"+
				    "( Heart Healthy )\n"+
				    "$3.99"
				    +TestTab.LINESPACE+TestTab.LINESPACE+"Total Price:"+TestTab.TABCHAR+"$6.98";
			Menu aMenu=new Menu();
			Order aOrder=new Order();
			MenuItem menuItemOne=new MenuItem(itemDescription[0], 1, 2.99, 2, true);
			OrderItem orderItemOne=new OrderItem(1, 2);
			MenuItem menuItemTwo=new MenuItem(itemDescription[1], 2, 3.99, 2, true);
			OrderItem orderItemTwo=new OrderItem(2, 2);
			aMenu.addMenuItem(menuItemOne);
			aOrder.addOrderItem(orderItemOne);
			aMenu.addMenuItem(menuItemTwo);
			aOrder.addOrderItem(orderItemTwo);
			Tab aTab=new Tab(aMenu, aOrder);
			Assertions.assertEquals(TabItemString, aTab.toString());
		}
		
		//Subsumes toString test
		@Test
		public void testGenerateTabThreeItems()
		{
			String[] itemDescription= {"Bananas", "Tofu", "Cheese"};
			String TabItemString=TestTab.boilerPlate+
					TestTab.TABCHAR+"Item 1: "+
				    itemDescription[0]+"\n"+
				    "( Heart Healthy )\n"+
				    "$2.99"+
				    TestTab.LINESPACE+TestTab.TABCHAR+"Item 2: "+
				    itemDescription[1]+"\n"+
				    "( Heart Healthy )\n"+
				    "$3.99" +
				    TestTab.LINESPACE+TestTab.TABCHAR+"Item 3: "+
				    itemDescription[2]+"\n$3.99" + 
					TestTab.LINESPACE+TestTab.LINESPACE+"Total Price:"+TestTab.TABCHAR+"$10.97";
			Menu aMenu=new Menu();
			Order aOrder=new Order();
			MenuItem menuItemOne=new MenuItem("Bananas", 1, 2.99, 2, true);
			OrderItem orderItemOne=new OrderItem(1, 2);
			MenuItem menuItemTwo=new MenuItem("Tofu", 2, 3.99, 1, true);
			OrderItem orderItemTwo=new OrderItem(2, 2);
			MenuItem menuItemThree=new MenuItem("Cheese", 3, 3.99, 1, false);
			OrderItem orderItemThree=new OrderItem(3, 2);
			aMenu.addMenuItem(menuItemOne);
			aOrder.addOrderItem(orderItemOne);
			aMenu.addMenuItem(menuItemTwo);
			aOrder.addOrderItem(orderItemTwo);
			aMenu.addMenuItem(menuItemThree);
			aOrder.addOrderItem(orderItemThree);
			Tab aTab=new Tab(aMenu, aOrder);
			Assertions.assertEquals(TabItemString, aTab.toString());
		}
		
		@Test
		public void testGenerateTabThreeItemsOneMismatch()
		{
			Menu aMenu=new Menu();
			Order aOrder=new Order();
			MenuItem menuItemOne=new MenuItem("Bananas", 1, 2.99, 2, true);
			OrderItem orderItemOne=new OrderItem(1, 2);
			MenuItem menuItemTwo=new MenuItem("Tofu", 2, 3.99, 1, true);
			OrderItem orderItemTwo=new OrderItem(2, 2);
			MenuItem menuItemThree=new MenuItem("Cheese", 3, 3.99, 1, false);
			OrderItem orderItemThree=new OrderItem(4, 2);
			aMenu.addMenuItem(menuItemOne);
			aOrder.addOrderItem(orderItemOne);
			aMenu.addMenuItem(menuItemTwo);
			aOrder.addOrderItem(orderItemTwo);
			aMenu.addMenuItem(menuItemThree);
			aOrder.addOrderItem(orderItemThree);
			Assertions.assertThrows(IllegalArgumentException.class, () ->
			{
				Tab aTab=new Tab(aMenu, aOrder);
			});
		}
		
		@Test
		public void testCalculateAndGetTabMultiple()
		{
			Menu aMenu=new Menu();
			Order aOrder=new Order();
			MenuItem menuItemOne=new MenuItem("Bananas", 1, 2.99, 2 , true);
			OrderItem orderItemOne=new OrderItem(1, 2);
			MenuItem menuItemTwo=new MenuItem("Tofu", 2, 3.99, 1, true);
			OrderItem orderItemTwo=new OrderItem(2, 2);
			MenuItem menuItemThree=new MenuItem("Cheese", 3, 3.99, 1, false);
			OrderItem orderItemThree=new OrderItem(3, 2);
			aMenu.addMenuItem(menuItemOne);
			aOrder.addOrderItem(orderItemOne);
			aMenu.addMenuItem(menuItemTwo);
			aOrder.addOrderItem(orderItemTwo);
			aMenu.addMenuItem(menuItemThree);
			aOrder.addOrderItem(orderItemThree);
			Tab aTab=new Tab(aMenu, aOrder);
			BigDecimal expectedPrice=new BigDecimal(10.97);
			expectedPrice.setScale(2, RoundingMode.CEILING);
			BigDecimal actualPrice=aTab.getTotalTotal();
			double actualPriceDouble=actualPrice.doubleValue();
			Assertions.assertEquals(expectedPrice.doubleValue(), actualPriceDouble);
		}
		
		
}
