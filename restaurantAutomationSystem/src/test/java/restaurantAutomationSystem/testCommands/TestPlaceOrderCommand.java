package restaurantAutomationSystem.testCommands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.commandInvokers.PlaceOrderCommand;
import restaurantAutomationSystem.commandInvokers.RestaurantCommandInterface;
import restaurantAutomationSystem.restaurantData.Aggregator;
import restaurantAutomationSystem.restaurantData.RestaurantIterator;
import restaurantAutomationSystem.restaurantData.Menu;
import restaurantAutomationSystem.restaurantData.Order;
import restaurantAutomationSystem.restaurantData.MenuItem;
import restaurantAutomationSystem.restaurantData.OrderItem;

public class TestPlaceOrderCommand {

	@Test
	public void testPlaceOrderCmdConstructor()
	{
		Menu testMenu=new Menu();
		Order testOrder=new Order();
		MenuItem testMenuItem=new MenuItem("Bananas", 1, 1.99, 2, false);
		testMenu.addMenuItem(testMenuItem);
		Aggregator testAggregator=new Aggregator(testMenu, testOrder);
		int orderNum=1;
		int orderQuantity=1;
		RestaurantCommandInterface placeOrderCmd=new PlaceOrderCommand(testAggregator,orderNum, 
				orderQuantity);
		Assertions.assertNotNull(placeOrderCmd);
	}
	
	@Test
	public void testExecute()
	{
		Menu testMenu=new Menu();
		Order testOrder=new Order();
		MenuItem testMenuItem=new MenuItem("Bananas", 1, 1.99, 2, false);
		testMenu.addMenuItem(testMenuItem);
		Aggregator testAggregator=new Aggregator(testMenu, testOrder);
		int orderNum=1;
		int orderQuantity=1;
		RestaurantCommandInterface placeOrderCmd=new PlaceOrderCommand(testAggregator,orderNum, 
				orderQuantity);
		try
		{
			Aggregator result=(Aggregator) placeOrderCmd.execute();
			Order aggregatorOrder=result.getOrder();
			RestaurantIterator orderIterator=aggregatorOrder.getAllItemsIterator();
			OrderItem newOrder=(OrderItem) orderIterator.next();
			Assertions.assertEquals(1, newOrder.getOrderNumber());
			
		}
		catch(CommandErrorException cee)
		{
			Assertions.fail("Execute should not fail if the Order Numbers match");
		}
	}
	
	@Test
	public void testExecuteThrowsCommandError()
	{
		Menu testMenu=new Menu();
		Order testOrder=new Order();
		MenuItem testMenuItem=new MenuItem("Bananas", 1, 1.99, 2, false);
		testMenu.addMenuItem(testMenuItem);
		Aggregator testAggregator=new Aggregator(testMenu, testOrder);
		int orderNum=2;
		int orderQuantity=1;
		RestaurantCommandInterface placeOrderCmd=new PlaceOrderCommand(testAggregator,orderNum, 
				orderQuantity);
			Assertions.assertThrows(CommandErrorException.class, () ->
			{
				Aggregator result=(Aggregator) placeOrderCmd.execute();
			});
	}
		
}
