package restaurantAutomationSystem.model;

import restaurantAutomationSystem.model.restaurantData.Observer;

public interface Observable {
	
	public void register(Observer subscriber);
	
	public void unregister(Observer subscriber);
	
	
	public void notifySubscribers();
}
