package restaurantAutomationSystem.model.restaurantData;

import restaurantAutomationSystem.model.Observable;

public interface Observer {

	/**Get the unique ID for each observer class **/
	public String getClassId();
	
	/**Get the unique ID for each instance of a class **/
	public int getInstanceId();
	
	/** The observer class must have a method which is 
	 * used to be notified by the observable object it is registered with
	 * refreshData allows the observer to change its data
	 * based on the observable change in state
	 * **/
	public void refreshData(Observable subject);
	
	/**
	 * Set instance id allows the observable to assign the observer and id
	 * based on where they are placed in the current collection of subscribers
	 */
	public void setInstanceId(int id);
	
	 
}
