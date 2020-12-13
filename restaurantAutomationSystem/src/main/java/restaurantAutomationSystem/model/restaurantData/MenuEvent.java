package restaurantAutomationSystem.model.restaurantData;


import restaurantAutomationSystem.model.RestaurantClock;
import restaurantAutomationSystem.model.restaurantData.MenuManager;

public class MenuEvent {

	private RestaurantClock clock;
	private MenuData availableMenu;
	private MenuManager manager;
	
	/**
	 * Default Constructor
	 * Note a -1 in hour, minute, month, day 
	 * means the field is not applicable to the event.
	 * period is false by default if hour or minute are not set then
	 * then period will not be valid 
	 */
	public MenuEvent()
	{
		this.clock=new RestaurantClock();
		this.manager=null;
		availableMenu=null;
	}
	
	
	/**
	 * Parameter Constructor
	 * @param newMenu
	 */
	public MenuEvent(MenuData aMenu, MenuManager manager)
	{
		this.clock=new RestaurantClock();
		this.manager=manager;
		this.availableMenu=aMenu;
	}
	
	/**
	 * Parameter Constructor
	 * @param aMenu
	 */
	public MenuEvent(MenuData aMenu)
	{
		this.clock=new RestaurantClock();
		this.manager=null;
		this.availableMenu=aMenu;
	}
	
	/**
	 * Copy Constructor
	 * @param event
	 */
	public MenuEvent(MenuEvent event)
	{
		this.clock=event.getClock();
		this.manager=this.getManager();
		this.availableMenu=new Menu((Menu) event.getAvailableMenu());
	}
	
	public void setHour(int h)
	{
		this.clock.setHour(h);
	}
	
	public void setMinutes(int m)
	{
		this.clock.setMinute(m);
	}
	
	public void setDay(int d)
	{
		this.clock.setDay(d);
	}
	
	public void setMonth(int mon)
	{
		this.clock.setMonth(mon);
	}
	
	public void setPeriod(boolean isAm)
	{
		this.clock.setPeriod(isAm);
	}
	
	public RestaurantClock getClock()
	{
		return this.clock;
	}
	
	public MenuManager getManager()
	{
		return this.manager;
	}
	
	public MenuData getAvailableMenu()
	{
		return this.availableMenu;
	}
	
	public void setManager(MenuManager manager)
	{
		if(this.availableMenu != null)
		{
			this.manager=manager;
			this.manager.setAvailableMenu(availableMenu);
		}
		else
		{
			throw new IllegalStateException("Set the menu for the event first");
		}
	}
	
	public void setMenu(MenuData menu)
	{
		this.availableMenu=menu;
	}
	
	public boolean isEventTime(RestaurantClock time)
	{
		return ((time.getHour() == this.clock.getHour()) 
				&& (time.getMinute() == this.clock.getMinute()));
	}
	
	public boolean isEventDay(RestaurantClock date)
	{
		return ((date.getDay() == this.clock.getDay()) 
				&& (date.getMonth() == this.clock.getMonth()));

	}
	
	public void setEventTime(int hour, int minute )
	{
		this.setHour(hour);
		this.setMinutes(minute);
	}
	
	public void setEventDay(int month, int day)
	{
		this.setMonth(month);
		this.setDay(day);
	}
	
	public void changeState()
	throws IllegalStateException
	{
		if(this.manager == null )
		{
			throw new IllegalStateException("Set the menu manager first");
		}
		int menuIdCurrent=this.manager.getAvailableMenu().getMenuId();
		int menuIdOfEvent=this.availableMenu.getMenuId();
		System.out.println("Menu ID Current: "+menuIdCurrent);
		System.out.println("Events Menu Id: "+menuIdOfEvent);
		if(menuIdCurrent != menuIdOfEvent)
		{
			if(!(this.manager == null))
			{
				this.manager.setAvailableMenu(availableMenu);
			}
			else
			{
				throw new IllegalStateException("The current event is not registered");
			}
		}
		
	}
	
	
}
