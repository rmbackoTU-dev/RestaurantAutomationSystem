package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.OrderItem;

public class PlaceOrderCommand implements RestaurantCommandInterface {

	private Order orderData;
	private Menu menuData;
	private OrderItem newOrderItem;
	
	public PlaceOrderCommand(Aggregator systemData, int orderChoice, int orderQuantity)
	{
		this.menuData=systemData.getMenu();
		this.orderData=systemData.getOrder();
		this.newOrderItem=new OrderItem(orderChoice, orderQuantity);
	}
	
	
	public Object execute() throws CommandErrorException {
		this.orderData.addOrderItem(this.newOrderItem);
		//copy the order add it to the new aggregator which will attempt to make a new tab
		//with the data
		Order newOrder=new Order(this.orderData);
		try
		{
			Aggregator newAggeragator=new Aggregator(this.menuData, newOrder);
			return newAggeragator;
		}
		catch(IllegalArgumentException iae)
		{
			String programmerError="Error occured while processing execute on placeOrder "+
					iae.getCause();
			String userError="Error: You order had issues processing. check your order selection and try again.";
			throw new CommandErrorException(programmerError, userError);
		}
	}

}
