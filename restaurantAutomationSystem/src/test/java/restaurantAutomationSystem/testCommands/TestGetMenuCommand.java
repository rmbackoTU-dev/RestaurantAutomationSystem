package restaurantAutomationSystem.testCommands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.commandInvokers.GetMenuCommand;
import restaurantAutomationSystem.commandInvokers.GetTabCommand;
import restaurantAutomationSystem.commandInvokers.RestaurantCommandInterface;
import restaurantAutomationSystem.restaurantData.Aggregator;
import restaurantAutomationSystem.restaurantData.Menu;
import restaurantAutomationSystem.restaurantData.Order;
import restaurantAutomationSystem.restaurantData.MenuItem;
import restaurantAutomationSystem.restaurantData.OrderItem;


public class TestGetMenuCommand {

		public static final String ITEM_DESCRIPT="Banana";
		public static final String TABCHAR="\t";
		public static final String DOUBLE_TAB_CHAR="\t\t";
		public static final String LINESPACE="\n\n";	
		public static final String START_OF_MENU=
				TestGetMenuCommand.DOUBLE_TAB_CHAR+"MENU"+TestGetMenuCommand.DOUBLE_TAB_CHAR+
				TestGetMenuCommand.LINESPACE+"\n";
		public static final String SINGLE_ITEM_MENU=TestGetMenuCommand.START_OF_MENU+
				"Appetizers: "+TestGetMenuCommand.LINESPACE+TestGetMenuCommand.LINESPACE+
				"Main Dishes: "+TestGetMenuCommand.LINESPACE+TestGetMenuCommand.LINESPACE+
				"Desserts: "+TestGetMenuCommand.LINESPACE+
				"\n\t1: "+TestGetMenuCommand.ITEM_DESCRIPT+"\n( Heart Healthy )"+
				"\n$1.99\n"+TestGetMenuCommand.LINESPACE+"Drinks: "+TestGetMenuCommand.LINESPACE+"\n";

		
		@Test
		public void testGetMenuCmdConstructor()
		{
			Menu testMenu=new Menu();
			Order testOrder=new Order();
			MenuItem testMenuItem=new MenuItem("Banana", 1, 1.99, 2, true);
			OrderItem testOrderItem=new OrderItem(1,1);
			testMenu.addMenuItem(testMenuItem);
			testOrder.addOrderItem(testOrderItem);
			Aggregator testAggregator=new Aggregator(testMenu, testOrder);
			RestaurantCommandInterface getMenuCmd=new GetMenuCommand(testAggregator);
			Assertions.assertNotNull(getMenuCmd);
			
			
		}
		
		@Test
		public void testExecute()
		{
			Menu testMenu=new Menu();
			Order testOrder=new Order();
			MenuItem testMenuItem=new MenuItem("Banana", 1, 1.99, 2, true);
			OrderItem testOrderItem=new OrderItem(1,1);
			testMenu.addMenuItem(testMenuItem);
			testOrder.addOrderItem(testOrderItem);
			Aggregator testAggregator=new Aggregator(testMenu, testOrder);
			RestaurantCommandInterface getMenuCmd=new GetMenuCommand(testAggregator);
			try
			{
				Menu aggregatorMenu=(Menu) getMenuCmd.execute();
				Assertions.assertEquals(TestGetMenuCommand.SINGLE_ITEM_MENU, aggregatorMenu.toString());
			}
			catch(CommandErrorException cee)
			{
				Assertions.fail("Execute should not throw an exception on execute");
			}
		}
}
