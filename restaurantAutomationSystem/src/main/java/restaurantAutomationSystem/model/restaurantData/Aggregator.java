package restaurantAutomationSystem.model.restaurantData;

public class Aggregator {
	
	private Menu newMenu;
	private Order newOrder;
	private Tab currentTab;
	
	/**
	 * Default constructor
	 */
	public Aggregator()
	{
		/*
		 * Because there is no items in the menu or order
		 *  they should always match.
		 */
		this.newMenu=new Menu();
		this.newOrder=new Order();
		//TODO Fix Tab Constructor
		//this.currentTab=new Tab(newMenu, newOrder);
	}
	
	/**
	 * A Parameter constructor
	 * @param aMenu
	 * @param anOrder
	 */
	public Aggregator(Menu aMenu, Order anOrder)
	throws IllegalArgumentException
	{
		//create a copy of the menu and order objects
		try
		{
			this.newMenu=new Menu(aMenu);
			this.newOrder=new Order(anOrder);
			//TODO fix Tab constructor
			//this.currentTab=new Tab(newMenu, newOrder);
		}
		catch(IllegalArgumentException iae)
		{
			throw new IllegalArgumentException(iae.getMessage());
		}
	}
	
	/**
	 * A copy constructor
	 */
	public Aggregator(Aggregator anAggregator)
	{
		try
		{
		/*Similar to parameter constructor, but gets its
		menu and order from  another aggregator*/
		this.newMenu=new Menu(anAggregator.getMenu());
		this.newOrder=new Order(anAggregator.getOrder());
		//TODO Fix Tab Constructor
		//this.currentTab=new Tab(newMenu, newOrder);
		}
		catch(IllegalArgumentException iae)
		{
			throw new IllegalArgumentException(iae.getMessage());
		}
	}
	
	public Menu getMenu()
	{
		return this.newMenu;
	}
	
	public Order getOrder()
	{
		return this.newOrder;
	}
	
	public Tab getTab()
	{
		return this.currentTab;
	}
	
}
