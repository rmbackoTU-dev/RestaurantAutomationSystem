package restaurantAutomationSystem.model.restaurantData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MenuItem {

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
	
	public void setCategoryNames()
	{
		
	}
	
	public int getCategory()
	throws IllegalStateException
	{
		if(this.category < 4)
		{
			return this.category;
		}
		else
		{
			throw new IllegalStateException("The menu item is not set to a known category");
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