package restaurantAutomationSystem.model.restaurantData;

public class MenuEvent {

	private MenuManager currentManager;
	private int hour;
	private int minute;
	private int month;
	private int day;
	private int period;
	private MenuData availableMenu;
	
	/**
	 * Default Constructor
	 * Note a -1 in hour, minute, month, day, or period 
	 * means the field is not applicable to the event.
	 */
	public MenuEvent()
	{
		
	}
	
	/**
	 * Parameter Constructor
	 * @param newMenu
	 */
	public MenuEvent(MenuData newMenu)
	{
		
	}
	
	/**
	 * Copy Constructor
	 * @param event
	 */
	public MenuEvent(MenuEvent event)
	{
		
	}
	
	public void setMenuManager(MenuManager manager)
	{
		this.currentManager=manager;
	}
	
	public boolean isEventTime()
	{
		//TODO: compare to the set hour and minute
		return false;
	}
	
	public boolean isEventDay()
	{
		//TODO: compare to the set month and day
		return false;
	}
	
	public void setEventTime(int hour, int minute )
	{
		
	}
	
	public void setEventDay(int month, int day)
	{
		
	}
	
	public void changeState()
	throws IllegalStateException
	{
		int menuIdCurrent=0;
		int menuIdOfEvent=0;
		if(menuIdCurrent == menuIdOfEvent)
		{
			throw new IllegalStateException("No state change took place");
		}
		else
		{
			if(!(currentManager == null))
			{
				currentManager.setAvailableMenu(availableMenu);
			}
			else
			{
				throw new IllegalStateException("The current event is not registered");
			}
		}
		
	}
	
	
}
