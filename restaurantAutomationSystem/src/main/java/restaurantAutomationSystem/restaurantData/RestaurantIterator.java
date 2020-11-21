package restaurantAutomationSystem.restaurantData;


/**
 * Classes implementing the MenuIterator provide a way to iterate through
 * a menu, which can either be the whole menu, or a menu filtered on categories.
 * 
 */
public interface RestaurantIterator {
	/**
	 * Provide a way to determine if any menu items
	 * are left if not throw an exception
	 * @return
	 */
	public boolean hasNext();
	
	/**
	 * Provide a way to go to the next menuItem in the Iterator
	 */
	public Object next();
	
	
	/**
	 * Method to get the current index
	 */
	public int getCurrentIndex();
	
	
	/**
	 * Provides a way to reset the Iterator to the beginning of the collection
	 */
	public void resetIter();
}
