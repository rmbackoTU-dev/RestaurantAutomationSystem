package restaurantAutomationSystem.model;

import restaurantAutomationSystem.model.restaurantData.Observer;
import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.OrderItem;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class TabManager implements Observable {

	private Observer[] subscribers;
	private Tab[] tabs;
	
	public void register(Observer subscriber)
	{
		
	}
	
	public void unregister(Observer subscriber)
	{
		
	}
	
	public void notifySubscribers()
	{
		
	}
	
	public void addOrderToBill(int TabId, Order order)
	{
		
	}
	
	public void removeOrderFromBill(int TabId, RestaurantIterator iter)
	{
		
	}
	
	public void changeOrderOnBill(int TabId, int orderId,
			OrderItem item)
	{
		
	}
	
	public void splitBill(int TabId)
	{
		
	}
	
	
}
