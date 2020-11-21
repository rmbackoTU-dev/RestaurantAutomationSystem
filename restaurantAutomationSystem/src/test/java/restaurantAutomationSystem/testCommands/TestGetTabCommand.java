package restaurantAutomationSystem.testCommands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import restaurantAutomationSystem.restaurantData.Aggregator;
import restaurantAutomationSystem.restaurantData.Menu;
import restaurantAutomationSystem.restaurantData.Order;
import restaurantAutomationSystem.restaurantData.MenuItem;
import restaurantAutomationSystem.restaurantData.OrderItem;
import restaurantAutomationSystem.restaurantData.Tab;
import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.commandInvokers.GetTabCommand;
import restaurantAutomationSystem.commandInvokers.RestaurantCommandInterface;

public class TestGetTabCommand {

	public static final String ITEM_DESCRIPT="Banana";
	public static final String TABCHAR="\t";
	public static final String DOUBLE_TAB_CHAR="\t\t";
	public static final String LINESPACE="\n\n";
	public static final String START_OF_TAB="Tables tab:"+TestGetTabCommand.LINESPACE;
	public static final String END_OF_TAB="Total Price:"+TestGetTabCommand.TABCHAR;
	public static final String SINGLE_ITEM_TAB=TestGetTabCommand.START_OF_TAB+
			TestGetTabCommand.TABCHAR+"Item 1: "+TestGetTabCommand.ITEM_DESCRIPT+"\n$1.99"+
			TestGetTabCommand.LINESPACE+TestGetTabCommand.LINESPACE+TestGetTabCommand.END_OF_TAB+
			"$1.99";
	
	@Test
	public void testGetTabCmdConstructor()
	{
		Menu testMenu=new Menu();
		Order testOrder=new Order();
		MenuItem testMenuItem=new MenuItem("Banana", 1, 1.99, 2, false);
		OrderItem testOrderItem=new OrderItem(1,1);
		testMenu.addMenuItem(testMenuItem);
		testOrder.addOrderItem(testOrderItem);
		Aggregator testAggregator=new Aggregator(testMenu, testOrder);
		RestaurantCommandInterface getTabCmd=new GetTabCommand(testAggregator);
		Assertions.assertNotNull(getTabCmd);
	}
	
	@Test
	public void testExecute()
	{
		Menu testMenu=new Menu();
		Order testOrder=new Order();
		MenuItem testMenuItem=new MenuItem("Banana", 1, 1.99, 2, false);
		OrderItem testOrderItem=new OrderItem(1,1);
		testMenu.addMenuItem(testMenuItem);
		testOrder.addOrderItem(testOrderItem);
		Aggregator testAggregator=new Aggregator(testMenu, testOrder);
		RestaurantCommandInterface getTabCmd=new GetTabCommand(testAggregator);
		try
		{
			Tab aggregatorTab=(Tab) getTabCmd.execute();
			Assertions.assertEquals(TestGetTabCommand.SINGLE_ITEM_TAB, aggregatorTab.toString());
			Assertions.assertEquals(1.99, aggregatorTab.getTabTotal().doubleValue());
		}
		catch(CommandErrorException cee)
		{
			Assertions.fail("Execute should not throw an exception on execute");
		}
	}
}
