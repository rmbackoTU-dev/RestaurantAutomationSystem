package restaurantAutomationSystem.model.restaurantData;

public interface MenuData {
	
	public void addMenuItem(MenuItem item);
	
	public void deleteMenuItem(RestaurantIterator iter);
	
	public boolean isOrderInMenu(int orderNumber);
	
	public String toString();
	
	
}