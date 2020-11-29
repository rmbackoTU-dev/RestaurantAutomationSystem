package restaurantAutomationSystem.testRestaurantData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuItem;
import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.OrderItem;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;
import restaurantAutomationSystem.model.restaurantData.Tab;
import restaurantAutomationSystem.testRestaurantData.TestOrder;

public class TestAggregator {
	
		public static final String ITEM_DESCRIPT="Banana";
		public static final String TABCHAR="\t";
		public static final String DOUBLE_TAB_CHAR="\t\t";
		public static final String LINESPACE="\n\n";
		public static final String START_OF_MENU=
				TestAggregator.DOUBLE_TAB_CHAR+"MENU"+TestAggregator.DOUBLE_TAB_CHAR+"\n"+
				TestAggregator.LINESPACE;
		public static final String START_OF_TAB="Tables tab:"+TestAggregator.LINESPACE;
		public static final String END_OF_TAB="Total Price:"+TestAggregator.TABCHAR;
		public static final String SINGLE_ITEM_TAB=TestAggregator.START_OF_TAB+
				TestAggregator.TABCHAR+"Item 1: "+TestAggregator.ITEM_DESCRIPT+"\n( Heart Healthy )\n"
				+"$2.50"+TestAggregator.LINESPACE+
				TestAggregator.LINESPACE+TestAggregator.END_OF_TAB+
				"$2.50";
		public static final String SINGLE_ITEM_MENU=TestAggregator.START_OF_MENU+
				"Appetizers: "+TestAggregator.LINESPACE+TestAggregator.LINESPACE+
				"Main Dishes: "+TestAggregator.LINESPACE+TestAggregator.LINESPACE+
				"Desserts: "+TestAggregator.LINESPACE+
				"\n\t1: "+TestAggregator.ITEM_DESCRIPT+"\n( Heart Healthy )"+
				"\n$2.50\n"+TestAggregator.LINESPACE+"Drinks: "+TestAggregator.LINESPACE+"\n";
				
	
	    @Test
		public void testAggregatorConstructor()
		{
			Aggregator newAggergator = new Aggregator();
			Assertions.assertNotNull(newAggergator);
			Assertions.assertNotNull(newAggergator.getMenu());
			Assertions.assertNotNull(newAggergator.getOrder());
			Assertions.assertNotNull(newAggergator.getTab());
		}
		
	    @Test
		public void testAggregatorCopyConstructor()
		{
			Menu newMenu=new Menu();
			Order newOrder=new Order();
			MenuItem newMenuItem=new MenuItem("Banana", 1, 2.50, 2, true);
			OrderItem newOrderItem=new OrderItem(1,1);
			newMenu.addMenuItem(newMenuItem);
			newOrder.addOrderItem(newOrderItem);
			try
			{
				Aggregator newAggregator=new Aggregator(newMenu, newOrder);
				Aggregator copyAggregator=new Aggregator(newAggregator);
				Menu copyMenu=copyAggregator.getMenu();
				Order copyOrder=copyAggregator.getOrder();
				RestaurantIterator orderIterator=copyOrder.getAllItemsIterator();
				OrderItem firstCopyItem=(OrderItem) orderIterator.next();
				Tab copyTab=copyAggregator.getTab();
				Assertions.assertEquals(TestAggregator.SINGLE_ITEM_MENU, 
						copyMenu.toString());
				Assertions.assertEquals(TestAggregator.SINGLE_ITEM_TAB, 
						copyTab.toString());
				Assertions.assertEquals(1, firstCopyItem.getOrderNumber());
			}
			catch(IllegalArgumentException iae)
			{
				Assertions.fail("Aggregator should not throw exception");
			}
			
		}
		
	    @Test
		public void testAggregatorParamConstructor()
		{

			Menu newMenu=new Menu();
			Order newOrder=new Order();
			MenuItem newMenuItem=new MenuItem("Banana", 1, 2.50, 2, true);
			OrderItem newOrderItem=new OrderItem(1,1);
			newMenu.addMenuItem(newMenuItem);
			newOrder.addOrderItem(newOrderItem);
			try
			{
				Aggregator newAggregator=new Aggregator(newMenu, newOrder);
				Menu originalMenu=newAggregator.getMenu();
				Order originalOrder=newAggregator.getOrder();
				RestaurantIterator orderIterator=originalOrder.getAllItemsIterator();
				OrderItem firstOriginalOrderItem=(OrderItem) orderIterator.next();
				Tab originalTab=newAggregator.getTab();
				Assertions.assertEquals(TestAggregator.SINGLE_ITEM_MENU, 
						originalMenu.toString());
				Assertions.assertEquals(TestAggregator.SINGLE_ITEM_TAB, 
						originalTab.toString());
				Assertions.assertEquals(1, firstOriginalOrderItem.getOrderNumber());
			}
			catch(IllegalArgumentException iae)
			{
				Assertions.fail("Aggregator should not throw exception");
			}
		}
		
	    @Test
		public void testGetMenuFromAggregator()
		{
	    	Menu newMenu=new Menu();
			Order newOrder=new Order();
			MenuItem newMenuItem=new MenuItem("Banana", 1, 2.50, 2, true);
			OrderItem newOrderItem=new OrderItem(1,1);
			newMenu.addMenuItem(newMenuItem);
			newOrder.addOrderItem(newOrderItem);
			try
			{
				Aggregator newAggregator=new Aggregator(newMenu, newOrder);
				Menu originalMenu=newAggregator.getMenu();
				Assertions.assertEquals(TestAggregator.SINGLE_ITEM_MENU, 
						originalMenu.toString());
			}
			catch(IllegalArgumentException iae)
			{
				Assertions.fail("Aggregator should not throw exception");
			}
		}
		
	    @Test
		public void testGetOrderFromAggregator()
		{
	    	Menu newMenu=new Menu();
			Order newOrder=new Order();
			MenuItem newMenuItem=new MenuItem("Banana", 1, 2.50, 2, true);
			OrderItem newOrderItem=new OrderItem(1,1);
			newMenu.addMenuItem(newMenuItem);
			newOrder.addOrderItem(newOrderItem);
			try
			{
				Aggregator newAggregator=new Aggregator(newMenu, newOrder);
				Order originalOrder=newAggregator.getOrder();
				RestaurantIterator orderIterator=originalOrder.getAllItemsIterator();
				OrderItem firstOriginalOrderItem=(OrderItem) orderIterator.next();
				Assertions.assertEquals(1, firstOriginalOrderItem.getOrderNumber());
			}
			catch(IllegalArgumentException iae)
			{
				Assertions.fail("Aggregator should not throw exception");
			}
		}
		
	    @Test
		public void testGetTabFromAggregator()
		{
	    	Menu newMenu=new Menu();
			Order newOrder=new Order();
			MenuItem newMenuItem=new MenuItem("Banana", 1, 2.50, 2, true);
			OrderItem newOrderItem=new OrderItem(1,1);
			newMenu.addMenuItem(newMenuItem);
			newOrder.addOrderItem(newOrderItem);
			try
			{
				Aggregator newAggregator=new Aggregator(newMenu, newOrder);
				Tab originalTab=newAggregator.getTab();
				Assertions.assertEquals(TestAggregator.SINGLE_ITEM_TAB, 
						originalTab.toString());
			}
			catch(IllegalArgumentException iae)
			{
				Assertions.fail("Aggregator should not throw exception");
			}
		}
}
