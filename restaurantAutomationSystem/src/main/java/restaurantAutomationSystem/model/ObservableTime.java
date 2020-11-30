package restaurantAutomationSystem.model;

import java.time.LocalDate;
import java.time.LocalTime;

import restaurantAutomationSystem.model.restaurantData.Observer;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class ObservableTime implements Observable {

	private Observer[] subscribers;
	private RestaurantClock clock;
	
	public ObservableTime() 
	{
		clock=new RestaurantClock();
		subscribers=new Observer[5];
	}
	
	public ObservableTime(LocalTime time, LocalDate date)
	{
		boolean am=(time.getHour() < 12);
		clock=new RestaurantClock(time.getHour(), time.getMinute(), date.getMonthValue(), date.getDayOfMonth(), am);
		subscribers=new Observer[5];
	}
	
	@Override
	public void register(Observer subscriber) {
		int index=0;
		Observer currentObserver=subscribers[index];
		//find the first empty index and set the index to place
		//the new subscriber in that place
		while(currentObserver != null)
		{
			index=index+1;
			currentObserver=subscribers[index];
		}
		subscriber.setInstanceId(index);
		this.subscribers[index]=subscriber;
	}

	@Override
	public void unregister(Observer subscriber) {
		int indexToRemove=subscriber.getInstanceId();
		this.subscribers[indexToRemove]=null;
	}

	@Override
	public void notifySubscribers() {
		Observer subscriber;
		for(int i=0; i< subscribers.length; i++)
		{
			subscriber=this.subscribers[i];
			subscriber.refreshData(this);
		}
		
	}

	@Override
	public Object getObjectFromObservable() {
		//Only way to make an observable event was to return a reference to a class that has the data
		//in the case of restaurantClock the object would only store the data. Alternative being to return this
		//which is a trivial function since the caller already has this.
		return this.clock;
	}

	
}
