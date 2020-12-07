package restaurantAutomationSystem.model;

public class RestaurantClock {

	private int hour;
	private int minute;
	private int month;
	private int day;
	private boolean period;

	public RestaurantClock()
	{
		this.hour=0;
		this.minute=0;
		this.month=0;
		this.day=0;
		this.period=false;
	}
	
	/**
	 * Parameter constructor
	 */
	public RestaurantClock(int clockHour, int clockMinute, int clockMonth, int clockDay, boolean isAM)
	{
		this.hour=clockHour;
		this.minute=clockMinute;
		this.month=clockMonth;
		this.day=clockDay;
		this.period=isAM;
	}
	
	/**
	 * Copy Constructor
	 */
	public RestaurantClock(RestaurantClock clock)
	{
		this.hour=clock.getHour();
		this.minute=clock.getMinute();
		this.month=clock.getMonth();
		this.day=clock.getDay();
	}
	
	public void setHour(int clockHour)
	{
		this.hour=clockHour;
	}
	
	public void setMinute(int clockMinute)
	{
		this.minute=clockMinute;
	}
	
	public void setMonth(int clockMonth)
	{
		this.month=clockMonth;
	}
	
	public void setDay(int clockDay)
	{
		this.day=clockDay;
	}
	
	public void setPeriod(boolean isAM)
	{
		this.period=isAM;
	}

	
	public int getHour()
	{
		return this.hour;
	}
	
	public int getMinute()
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
