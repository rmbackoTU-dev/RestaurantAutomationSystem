package restaurantAutomationSystem.model;

import restaurantAutomationSystem.model.restaurantData.Observer;

public class ObservableTime implements Observable {

	private Observer[] subscribers;
	private int hour;
	private int minute;
	private int month;
	private int day;
	private boolean period;
	
	@Override
	public void register(Observer subscriber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregister(Observer subscriber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifySubscribers() {
		// TODO Auto-generated method stub
		
	}
	
	public int getHours()
	{
		return this.hour;
	}
	
	public int getMinutes()
	{
		return this.minute;
	}
	
	public int getMonth()
	{
		return this.month;
	}
	
	public int getDay()
	{
		return this.day;
	}
	
	public boolean getPeriod()
	{
		return this.period;
	}

	
     

	
}
