package restaurantAutomationSystem.model.restaurantData;

import java.util.ArrayList;

import restaurantAutomationSystem.model.Observable;
import restaurantAutomationSystem.model.RestaurantClock;

public class MenuManager implements Observer {

	private MenuData availableMenu;
	private ArrayList<MenuEvent> events;
	private int instanceId;
	
	public MenuManager()
	{
		this.instanceId=0;
		this.events=new ArrayList<MenuEvent>();
	}
	
	@Override
	public String getClassId() 
	{
		return this.getClass().getName();
	}

	@Override
	public int getInstanceId() {
		return instanceId;
	}

	@Override
	public void refreshData(Observable time) {
		RestaurantClock currentTime=(RestaurantClock) time.getObjectFromObservable();
		//Applies the last event that applies for other similar events to apply remove
		//events from the list
		MenuEvent currentEvent;
		for(int i=0; i< events.size(); i++)
		{
			currentEvent=events.get(i);
			if(currentEvent.isEventTime(currentTime) || currentEvent.isEventDay(currentTime))
			{
				currentEvent.changeState();
			}
		}
		
	}

	public MenuData getAvailableMenu()
	{
		return this.availableMenu;
	}
	
	public void setAvailableMenu(MenuData newMenu)
	{
		this.availableMenu=newMenu;
	}
	
	public void registerMenuEvent(MenuEvent event)
	{
		events.add(event);
	}
	
	public void unregisterMenuEvent(int indexOfEvent)
	{
		events.remove(indexOfEvent);
	}

	@Override
	public void setInstanceId(int id) {
		this.instanceId=id;
		
	}
	
}
