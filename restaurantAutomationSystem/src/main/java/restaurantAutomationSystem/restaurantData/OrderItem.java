package restaurantAutomationSystem.restaurantData;

public class OrderItem {

	private int orderNumber;
	private int quantity;
	
	/**
	 * Default Constructor
	 */
	public OrderItem()
	{
		this.orderNumber=0;
    this.quantity=0;
	}
	
	/**
	 * Parameter based constructor
	 * @param itemNum
	 */
	public OrderItem(int itemNum, int orderQuantity)
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
}
