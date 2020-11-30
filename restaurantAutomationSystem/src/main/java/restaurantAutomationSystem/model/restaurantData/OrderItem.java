package restaurantAutomationSystem.model.restaurantData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderItem implements BillingComponent {

	private int orderNumber;
	private int quantity;
	private Menu usedMenu;
	private int orderId;
	
	/**
	 * Default Constructor
	 */
	public OrderItem()
	{
		this.orderNumber=0;
		this.quantity=0;
		usedMenu=new Menu();
	}
	
	/**
	 * Parameter based constructor
	 * @param itemNum
	 */
	public OrderItem(int itemNum, int orderQuantity, Menu aMenu)
  throws IllegalArgumentException
	{
		this.orderNumber=itemNum;
		if(orderQuantity >0 )
    	{
    	this.quantity=orderQuantity;
    	}
		else
		{
			throw new IllegalArgumentException("Set at least"+
					"a quantity of at least 1");
    	}
		
		this.usedMenu=aMenu;
	}
	
	/**
	 * Copy Constructor
	 * @param item
	 */
	public OrderItem(OrderItem item)
  throws IllegalArgumentException
	{
		this.orderNumber=item.getOrderNumber();
		int tempQuantity=item.getOrderQuantity();
		if(tempQuantity >0 )
		{
			this.quantity=tempQuantity;
		}
		else
		{
			throw new IllegalArgumentException("Set at least"+
					"a quantity of at least 1");
		}
		this.usedMenu=new Menu(item.getUsedMenu());
	}
	
	public Menu getUsedMenu()
	{
		return this.usedMenu;
	}
	
	public int getOrderQuantity()
	{
		return this.quantity;
	}
	
	/*
	 * Sets the quantity of the order so that way we can update if we get duplicates
	 */
	public void setQuantity(int orderQuantity)
	{
		this.quantity=orderQuantity;
	}
	
	/**
	 * gets the Item number
	 */
	public int getOrderNumber()
	{
		return this.orderNumber;
	}
	
	/**
	 * sets Order Number
	 */
	public void setOrderNumber(int itemNum)
	{
		this.orderNumber=itemNum;
	}

	@Override
	public BigDecimal getTotalAmount() {
		BigDecimal total=new BigDecimal(0);
		total=total.setScale(2, RoundingMode.CEILING);
		OrderItem item;
		if(this.isCorrectOrder())
		{
			try
			{
				total=total.add(this.getOrderItemAmount());
			}
			catch(IllegalStateException e)
			{
				System.err.println(" Order "+this.getOrderNumber()+
						"is not in the current menu");
			}
		}
		return total;

	}

	@Override
	public BigDecimal calculateTax() {
		BigDecimal taxAmount=new BigDecimal(0);
		taxAmount=taxAmount.setScale(2, RoundingMode.CEILING);
		BigDecimal currentTaxAmount=new BigDecimal(0);
		currentTaxAmount=currentTaxAmount.setScale(2, RoundingMode.CEILING);
		BigDecimal taxPercentage=new BigDecimal(0.06);
		taxPercentage=taxPercentage.setScale(2, RoundingMode.CEILING);
		if(this.isCorrectOrder())
		{
			try
			{
				currentTaxAmount=this.getOrderItemAmount().multiply(
					taxPercentage);
			}
			catch(IllegalStateException e)
			{
				System.err.println(" Order "+this.getOrderNumber()+
						"is not in the current menu");
			}
		}
		taxAmount=taxAmount.add(currentTaxAmount);
		return taxAmount;
	}
	
	
	private boolean isCorrectOrder()
	{
		boolean orderInMenu=false;
    	MenuItem currentItem;
    	RestaurantIterator menuIter=this.usedMenu.getAllItemsIterator();
    	while(menuIter.hasNext() && !orderInMenu)
    	{
    		currentItem=(MenuItem) menuIter.next();
    		if(currentItem.getOrderNumber() == this.getOrderNumber())
    		{
    			orderInMenu=true;
    		}
    	}
		return orderInMenu;
	}
	
	private BigDecimal getOrderItemAmount()
	{
		BigDecimal price=new BigDecimal(0);
		price=price.setScale(2, RoundingMode.CEILING);
		RestaurantIterator menuIter=this.usedMenu.getAllItemsIterator();
		MenuItem currentMenuItem;
		boolean found=false;
		while(menuIter.hasNext())
		{
			currentMenuItem=(MenuItem) menuIter.next();
			if(this.getOrderNumber() == currentMenuItem.getOrderNumber())
			{
				price=new BigDecimal(currentMenuItem.getCategory());
				price.setScale(2, RoundingMode.CEILING);
			}
		}
		if(!found)
		{
			throw new IllegalStateException("Item not in menu,"
					+ " make sure you check the menu before "
					+ " using getOrderItemAmount");
		}
		return price;
	}

	@Override
	public BigDecimal getTotalWithTax() {
		return this.getTotalAmount().add(this.calculateTax());
	}

	@Override
	public void displayBill() {
		System.out.println("Order Item "+this.getOrderNumber()+":"+
				"price: $"+this.getTotalAmount().toPlainString()+
				"tax: $"+this.calculateTax().toPlainString()+
				"Total with tax: $"+this.getTotalWithTax().toPlainString());
	}

	
	@Override
	public String toString()
	{
		String orderItemString="Order Item "+this.getOrderNumber()+":"+
				"price: $"+this.getTotalAmount().toPlainString()+
				"tax: $"+this.calculateTax().toPlainString()+
				"Total with tax: $"+this.getTotalWithTax().toPlainString();
		return orderItemString;
	}
	
	@Override
	/**
	 * @return a array containing the current orderItem since the order
	 * can not be split further
	 */
	public BillingComponent[] splitOrder() {
		BillingComponent[] singletonOrderItem= {this};
		return singletonOrderItem;
	}

	@Override
	public void addOrder(BillingComponent orderObject) {
		System.err.println("OrderItem is already smallest  billing component "
				+ "and can not be added or deleted to itself");
	}

	@Override
	public void removeFromOrder(RestaurantIterator iter) {
		System.err.println("OrderItem is already smallest  billing component "
				+ "and can not be added or deleted to itself");

	}
}
