package restaurantAutomationSystem.model;

import restaurantAutomationSystem.model.restaurantData.Observer;
import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;
import restaurantAutomationSystem.model.restaurantData.Tab;
import restaurantAutomationSystem.model.restaurantData.TableManager;


/**
 * Tab Manager used to keep track of all the tabs for all the tables in the restaurant
 * @author rmbackoTU-dev
 *
 */
public class TabManager implements Observable {

	private Observer[] subscribers;
	private Tab[] tabs;
	private int tablesTotal;
	private int tablesAvailable;
	
	public TabManager()
	{
		
		this.tablesTotal=10;
		this.tablesAvailable=this.tablesTotal;
		this.tabs=new Tab[this.tablesTotal];
		this.subscribers=new Observer[this.tablesTotal];
	}
	
	
	public TabManager(int tables)
	{
		this.tablesTotal=tables;
		this.tablesAvailable=this.tablesTotal;
		this.tabs=new Tab[this.tablesTotal];
		this.subscribers=new Observer[this.tablesTotal];
	}
	
	public void addTable()
	{
		this.tablesAvailable=this.tablesAvailable+1;
	}
	
	@Override
	public Tab[] getObjectFromObservable()
	{
		return this.tabs;
	}
	
	public void register(Observer subscriber)
	{
		int index=0;
		Observer currentObserver=subscribers[index];
		//find the first empty index and set the index to place
		//the new subscriber in that place
		while(currentObserver != null)
		{
			index=index+1;
			currentObserver=subscribers[index];
		}
		this.tablesAvailable=tablesAvailable-1;
		subscriber.setInstanceId(index);
		this.subscribers[index]=subscriber;
		this.tabs[index]=new Tab();
	}
	
	public void unregister(Observer subscriber)
	{
		this.tablesAvailable=tablesAvailable+1;
		int indexToRemove=subscriber.getInstanceId();
		this.subscribers[indexToRemove]=null;
		this.tabs[indexToRemove]=null;
	}
	
	public void notifySubscribers()
	{
		//IDEA Add a pattern that allows only the subscriber that 
		//has changed to update its subscribers in parallel
		//without causing concurrent modification exception
		for(int i=0; i<this.tablesTotal; i++)
		{
			if(!(this.subscribers[i] == null))
			{
				this.subscribers[i].refreshData(this);
			}
		}
	}
	
	public void addOrderToBill(int tabId, Order order)
	{
		this.tabs[tabId].addOrder(order);
		this.notifySubscribers();
	}
	
	public void removeOrderFromBill(int tabId, RestaurantIterator iter)
	{
		if(!(iter instanceof Order.AllOrderItemIterator))
		{
			throw new IllegalArgumentException("Iterator must be an order iterator");
		}
		this.tabs[tabId].removeFromOrder(iter);
		this.notifySubscribers();
		
	}
	
	public void changeOrderOnBill(int tabId, int orderId,
			Order newOrder)
	{
		this.tabs[tabId].changeOrder(newOrder, tabId);
		this.notifySubscribers();
		
	}
	
	public int[] splitBill(int tabId)
	{
		Tab[] tablesTabs=(Tab[])this.tabs[tabId].splitOrder();
		int[] newTabIndex=new int[tablesTabs.length];
		//creates new subscribers for n-1 new tabs created by split bill
		//return the indexes to manage those tabs
		Observer newManager;
		for(int i=0; i<tablesTabs.length; i++)
		{
			newManager=new TableManager(tablesTabs[i]);
			this.register(newManager);
			newTabIndex[i]=newManager.getInstanceId();
		}
		return newTabIndex;
	}
	
	
}
