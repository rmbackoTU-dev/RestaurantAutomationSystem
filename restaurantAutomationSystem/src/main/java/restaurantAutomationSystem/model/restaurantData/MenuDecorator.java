package restaurantAutomationSystem.model.restaurantData;

public abstract class MenuDecorator implements MenuData {

	private Menu baseMenu;
	private MenuData menuWithAddons;
	
	/**
	 * Default Constructor
	 */
	public MenuDecorator()
	{
		this.baseMenu=new Menu();
		this.menuWithAddons=this.baseMenu;
	}
	
	/**
	 * Parameter Constructor
	 */
	public MenuDecorator(int menuSize)
	{
		this.baseMenu=new Menu(menuSize);
		this.menuWithAddons=this.baseMenu;
	}
	
	/**
	 * Copy Constructor
	 */
	public MenuDecorator(MenuData menuToCopy)
	{
		MenuDecorator decorator;
		Menu menuData;
		if(!(menuToCopy instanceof Menu))
		{
			decorator=(MenuDecorator) menuToCopy;
			this.menuWithAddons=decorator;
			menuData=decorator.getMenu();
			this.baseMenu=menuData;
		}
		else
		{
			menuData=(Menu) menuToCopy;
			this.baseMenu=menuData;
			this.menuWithAddons=this.baseMenu;
		}
		
	}

	public int getMenuId()
	{
		return this.baseMenu.getMenuId();
	}
	
	public Menu getMenu()
	{
		return this.baseMenu;
	}
	
	@Override
	public String toString()
	{
		return this.menuWithAddons.toString();
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
	
	
	
}
