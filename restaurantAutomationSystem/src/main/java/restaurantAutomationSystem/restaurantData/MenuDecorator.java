package restaurantAutomationSystem.restaurantData;


public abstract class MenuDecorator implements MenuData {

	protected Menu baseMenu;
	
	/**
	 * Default Constructor
	 */
	public MenuDecorator()
	{
		baseMenu=new Menu();
	}
	
	/**
	 * Parameter Constructor
	 */
	public MenuDecorator(int menuSize)
	{
		baseMenu=new Menu(menuSize);
	}
	
	/**
	 * Copy Constructor
	 */
	public MenuDecorator(Menu menuToCopy)
	{
		baseMenu=new Menu(menuToCopy);
	}
	
	@Override
	public String toString()
	{
		return this.baseMenu.toString();
	}
	
	@Override
	public void addMenuItem(MenuItem item)
	{
		this.baseMenu.addMenuItem(item);
	}
	
	@Override
	public void deleteMenuItem(RestaurantIterator iter)
	{
		this.baseMenu.deleteMenuItem(iter);
	}
	
	@Override
	public boolean isOrderInMenu(int orderNumber)
	{
		return this.baseMenu.isOrderInMenu(orderNumber);
	}
	
	public void updateCategories(String[] categories)
	{
		this.baseMenu.setCategories(categories);
	}
	
	
}
