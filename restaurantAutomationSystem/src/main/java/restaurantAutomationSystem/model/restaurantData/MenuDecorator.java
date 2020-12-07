package restaurantAutomationSystem.model.restaurantData;

public abstract class MenuDecorator implements MenuData {

	private Menu baseMenu;
	
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
	
	public Menu getMenu()
	{
		return this.baseMenu;
	}
	
	@Override
	public String toString()
	{
		return this.baseMenu.toString();
	}
	
	public void addMenuItem(MenuItem item)
	{
		this.baseMenu.addMenuItem(item);
	}
	
	public void deleteMenuItem(RestaurantIterator iter)
	{
		this.baseMenu.deleteMenuItem(iter);
	}
	
	public boolean isOrderInMenu(int orderNumber)
	{
		return this.baseMenu.isOrderInMenu(orderNumber);
	}
	
//	public void updateCategories(String[] categories)
//	{
//		this.baseMenu.setCategories(categories);
//	}
	
	
}
