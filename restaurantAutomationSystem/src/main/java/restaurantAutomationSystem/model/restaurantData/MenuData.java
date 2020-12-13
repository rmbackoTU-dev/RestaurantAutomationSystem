package restaurantAutomationSystem.model.restaurantData;

public interface MenuData {
	
	public void addMenuItem(MenuItem item) throws NullPointerException;
	
	public void deleteMenuItem(RestaurantIterator iter);
	
	public boolean isOrderInMenu(int orderNumber);
	
	public String toString();
	
	public int getMenuId();
	
	
}
