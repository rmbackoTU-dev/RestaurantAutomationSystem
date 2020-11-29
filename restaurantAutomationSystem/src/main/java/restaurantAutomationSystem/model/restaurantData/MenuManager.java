package restaurantAutomationSystem.model.restaurantData;

import java.util.ArrayList;

public class MenuManager implements Observer {

	private MenuData availableMenu;
	private ArrayList<MenuEvent> events;
	
	@Override
	public int getClassId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInstanceId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
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
		event.setMenuManager(this);
	}
	
}
