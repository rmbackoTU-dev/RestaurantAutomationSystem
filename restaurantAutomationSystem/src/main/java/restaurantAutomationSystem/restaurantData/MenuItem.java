package restaurantAutomationSystem.restaurantData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MenuItem {

	public static final int APPETIZERS = 0;
	public static final int MAIN_DISH = 1;
	public static final int DESSERT = 2;
	public static final int DRINK=3;
	private String description;
	private int orderNumber;
	private BigDecimal price;
	private int category;
	private boolean heartHealthy;
	
	public MenuItem()
	{
		this.description="";
		this.orderNumber=0;
		this.price=new BigDecimal(0);
		this.price=this.price.setScale(2, RoundingMode.CEILING);
		this.heartHealthy=false;
	}
	
	public MenuItem(String descript, int orderNum, double price, int mealCategory, boolean heartHealth)
	throws IllegalArgumentException
	{
		
		if(descript == null)
		{
			throw new IllegalArgumentException("Menu Item Description can not be null");
		}
		
		this.description=descript;
		this.orderNumber=orderNum;
		String priceString=Double.toString(price);
		this.price=new BigDecimal(priceString);
		this.price=this.price.setScale(2, RoundingMode.CEILING);
		this.heartHealthy=heartHealth;
		this.category=mealCategory;
	}
	
	
	public MenuItem(MenuItem itemToCopy)
	{
		this.description=itemToCopy.getDescription();
		this.orderNumber=itemToCopy.getOrderNumber();
		this.price=new BigDecimal(itemToCopy.getPrice());
		this.price=this.price.setScale(2, RoundingMode.CEILING);
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public int getOrderNumber()
	{
		return this.orderNumber;
	}
	
	public String getPrice()
	{
		return this.price.toString();
	}
	
	public String getCategory()
	{
		if(this.category == APPETIZERS)
		{
			return "Appetizer";
		}
		else if(this.category == MAIN_DISH)
		{
			return "Main Dish";
		}
		else if(this.category == DESSERT)
		{
			return "Dessert";
		}
		else if(this.category == DRINK)
		{
			return "Drink";
		}
		else
		{
			return "Unknown Category";
		}
	}
	
	public String toString()
	{
		String healthy="";
		if(this.heartHealthy)
		{
			healthy="( Heart Healthy )\n";
		}
		String menuItemString=this.orderNumber+": "+this.description+"\n"
				+healthy+
				"$"+this.price;
		return menuItemString;
	}

	public boolean isHeartHealthy() {
		return this.heartHealthy;
	}
}