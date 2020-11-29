package restaurantAutomationSystem.model.restaurantData;

public interface Observer {

	/**Get the unique ID for each observer class **/
	public int getClassId();
	
	/**Get the unique ID for each instance of a class **/
	public int getInstanceId();
	
	/** The observer class must have a method which is 
	 * used to be notified by the observable object it is registered with
	 * refreshData allows the observer to change its data
	 * based on the observable change in state
	 * **/
	public void refreshData();
	 
}
