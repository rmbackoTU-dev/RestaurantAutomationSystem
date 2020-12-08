package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.OrderItem;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class PlaceOrderCommand implements RestaurantCommandInterface {

	private Order orderData;
	private Menu menuData;
	private OrderItem newOrderItem;
	private Tab tabToAddOrder;
	private int orderNumberToUse;
	
	public PlaceOrderCommand(Aggregator systemData, int orderChoice, int orderQuantity, int tabIndex)
	{
		this.menuData=systemData.getCurrentMenu();
		this.newOrderItem=new OrderItem(orderChoice, orderQuantity, menuData);
		this.tabToAddOrder=systemData.getTab(tabIndex);
		this.orderData=new Order();
		this.orderNumberToUse=-1;
	}

	public PlaceOrderCommand(Aggregator systemData, int orderChoice, int orderQuantity, int tabIndex, int orderIndex)
	{
		this.menuData=systemData.getCurrentMenu();
		this.newOrderItem=new OrderItem(orderChoice, orderQuantity, menuData);
		this.tabToAddOrder=systemData.getTab(tabIndex);
		RestaurantIterator tabIterator=this.tabToAddOrder.getAllTabItemsIterator();
		int i=0;
		Order currentOrder=null;
		while((i != orderIndex) && (tabIterator.hasNext()))
		{
			currentOrder=(Order) tabIterator.next();
			i=i+1;
		}
		this.orderData=currentOrder;
		this.orderNumberToUse=orderIndex;
	}
	
	public Object execute() throws CommandErrorException {
		this.orderData.addOrder(this.newOrderItem);
		//copy the order add it to the new aggregator which will attempt to make a new tab
		//with the data
		try
		{
			if(this.orderNumberToUse != -1)
			{
				tabToAddOrder.addOrder(this.orderData);
				this.orderNumberToUse=this.tabToAddOrder.getOrderIndex(orderData);
			}
			else
			{
				tabToAddOrder.changeOrder(this.orderData, this.orderNumberToUse);
			}
			return this.orderNumberToUse;
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
