package restaurantAutomationSystem.model.restaurantData;


import java.math.BigDecimal;
import java.math.RoundingMode;

import restaurantAutomationSystem.model.restaurantData.Order.AllOrderItemIterator;

public class Tab implements BillingComponent 
{
	//TODO remove need to check menu at tab level
    private BigDecimal tabTotal;
    private int numberOfOrders;
    private int firstEmpty;
    private Order[] orders;
    private final static String LINESPACE="\n\n";
    private final static String TABHEADING="Tables tab:";

    /**
     * Default Constructor
     */
    public Tab()
    {
    	this.numberOfOrders=10;
    	this.firstEmpty=0;
    	this.orders=new Order[numberOfOrders];
    }
    
    
    /**
    * Parameter Constructor
    */
    public Tab(Menu menu, Order[] orderArray) 
    {
    	if(menu == null)
    	{
    		throw new IllegalArgumentException("Menu has not been created.");
    	}
    	
    	if(orderArray == null)
    	{
    		throw new IllegalArgumentException("Menu has not been created.");
    	}
    	Order aOrder;
    	for(int i=0; i<orderArray.length; i++)
    	{
    		aOrder=orderArray[i];
    		if(!(this.isCorrectOrder(menu, aOrder)))
    		{
    			throw new IllegalArgumentException("Some of the orders are "
    					+ "not found in the menu given");
    		}
    	}
    	this.tabTotal=new BigDecimal(0);
    	this.tabTotal.setScale(2, RoundingMode.CEILING);
    	this.numberOfOrders=orderArray.length*2;
    	this.orders=new Order[numberOfOrders];
    	this.firstEmpty=numberOfOrders+1;
    	for(int j=0; j< orderArray.length; j++)
    	{
    		this.orders[j]=orderArray[j];
    	}
    }
    
    /**
     * Parameter Constructor without pre-existing order Array
     **/
    public Tab(int tabSize, Menu aMenu)
    {
    	if(aMenu == null)
    	{
    		throw new IllegalArgumentException("Menu has not been created.");
    	}
    	
    	if(tabSize < 1)
    	{
    		throw new IllegalArgumentException("Tab must be at least of size one");
    	}
    	this.numberOfOrders=tabSize;
    	
    }
    
    
    /**
     * Used to get the total, used by toString, but still
     * useful to the user Interface as well to display
     * to the user
     * @return BigDecimal version of the Total price
     */
    public BigDecimal getTotalTotal()
    {
    	return this.tabTotal;
    }
    
    /**
    * Used to get a well formatted version of the tab
    */
    @Override
    public String toString()
    {
    	String tabString=Tab.TABHEADING+Tab.LINESPACE;   	 	
    	RestaurantIterator tabIter=this.getAllTabItemsIterator();
    	Order currentOrder;
    	int i=0;
    	while(tabIter.hasNext())
    	{
    		currentOrder=(Order) tabIter.next();
    		tabString=tabString+"Order "+i+":\n";
    		tabString=tabString+currentOrder.toString();
    		tabString=tabString+Tab.LINESPACE+Tab.LINESPACE;
    	}
    	return tabString;
    }
    
    /**
     * Checks if a tab matches a order to menu items that exist
     * @param aMenu
     * @param aOrder
     * @return
     */
    private boolean isCorrectOrder(Menu aMenu, Order aOrder)
	{
    	//Added to Tab because it is more appropriate to check for correctness
    	//closer to the creation of the object. Otherwise we run into exceptions 
    	//that may or may not happen.
    	boolean allOrdersMatch=false;
    	if( aOrder.size() > 0)
    	{
    		AllOrderItemIterator orderIterator=aOrder.getAllItemsIterator();
    		boolean orderMismatch=false;
    		OrderItem currentItem;
    		int currentOrderNumber;
    		while(orderIterator.hasNext() && !orderMismatch)
    		{
    			currentItem=(OrderItem) orderIterator.next();
    			currentOrderNumber=currentItem.getOrderNumber();
    			orderMismatch=!(aMenu.isOrderInMenu(currentOrderNumber));
    		}
    		allOrdersMatch=!orderMismatch;
    	}
    	else
    	{
    		//if the size of the orders is 0 then all of
    		//the orders are found in the menu
    		allOrdersMatch=true;
    	}
		return allOrdersMatch;
	}

    public int size()
    {
    	return firstEmpty;
    }
    
	@Override
	public BigDecimal getTotalAmount() {
		RestaurantIterator iter=this.getAllTabItemsIterator();
		BigDecimal total=new BigDecimal(0);
		total=total.setScale(2, RoundingMode.CEILING);
		Order currentOrder;
		while(iter.hasNext())
		{
			currentOrder=(Order) iter.next();
			total=total.add(currentOrder.getTotalAmount());
		}
		return total;
	}


	@Override
	public BigDecimal calculateTax() {
		RestaurantIterator iter=this.getAllTabItemsIterator();
		BigDecimal totalTaxes=new BigDecimal(0);
		totalTaxes=totalTaxes.setScale(2, RoundingMode.CEILING);
		Order currentOrder;
		while(iter.hasNext())
		{
			currentOrder=(Order) iter.next();
			totalTaxes=totalTaxes.add(currentOrder.calculateTax());
		}
		return totalTaxes;
	}


	@Override
	public BigDecimal getTotalWithTax() {
		return this.getTotalAmount().add(this.calculateTax());
	}


	@Override
	public void displayBill() {
		System.out.println(Tab.TABHEADING+Tab.LINESPACE);
		RestaurantIterator iter=this.getAllTabItemsIterator();
		Order currentOrder;
		while(iter.hasNext())
		{
			currentOrder=(Order) iter.next();
			currentOrder.displayBill();
		}	
	}


	/**
	 * Split the tab into multiple single order tabs
	 */
	@Override
	public BillingComponent[] splitOrder() {
		Tab[] splitTab=new Tab[this.firstEmpty];
		RestaurantIterator iter=this.getAllTabItemsIterator();
		int i=0;
		Order currentOrder;
		//TODO: Generate new tabs of size 1 and add the current order to it.
		return splitTab;
	}


	@Override
	public void addOrder(BillingComponent order) {
		// TODO Auto-generated method stub
		if(this.numberOfOrders == this.firstEmpty)
		{
			this.numberOfOrders=(numberOfOrders*2);
			this.orders=new Order[numberOfOrders];
			try
			{
				RestaurantIterator ordersIter=this.getAllTabItemsIterator();
				Order anOrder;
				while(ordersIter.hasNext())
				{
					anOrder=(Order) ordersIter.next();
					this.orders[ordersIter.getCurrentIndex()]=anOrder;
				}
			}
			catch(NullPointerException npe)
			{
				System.err.println("The list which is being copied is empty");
			}
		}
		else
		{
			this.orders[this.firstEmpty]=(Order) order;
			this.firstEmpty=firstEmpty+1;
		}
	}


	@Override
	public void removeFromOrder(RestaurantIterator iter) {
		int indexOfItemToRemove=iter.getCurrentIndex();
		if(indexOfItemToRemove == -1)
		{
			throw new IllegalStateException("The order iterator is not set to a item");
		}
		boolean last;
		if(indexOfItemToRemove == this.size()-1)
		{
			last=true;
			this.firstEmpty=indexOfItemToRemove;
			this.orders[indexOfItemToRemove]=null;
		}
		else
		{
			last=false;
		}
		int currentIndex;
		int previousIndex;
		//Move back all of the indexes of the list by one
		while(iter.hasNext() && !last)
		{
			iter.next();
			currentIndex=iter.getCurrentIndex();
			previousIndex=currentIndex-1;
			this.orders[previousIndex]=this.orders[currentIndex];
			if(currentIndex == (this.size()-1))
			{
				last=true;
				this.firstEmpty=currentIndex;
				this.orders[currentIndex]=null;
			}
		}
	}
	
	public RestaurantIterator getAllTabItemsIterator()
	{
		AllTabItemIterator iter=new AllTabItemIterator();
		return iter;
	}
	
	public class AllTabItemIterator implements RestaurantIterator
	{
		private Order currentOrder;
		private int currentIndex;
		
		public AllTabItemIterator()
        throws NullPointerException
		{
			this.currentIndex=-1;
			this.currentOrder=null;
			//see if list is empty if the list is empty throw an exception
			if(Tab.this.firstEmpty == 0)
			{
			  throw new NullPointerException("The list is empty");
			}
		}
		
		/**
		 * Provide a way to determine if any items
		 * are left if not throw an exception
		 * @return
		 */
		public boolean hasNext()
		{
			return (currentIndex+1 < Tab.this.size());
		}
		
		
		/**
		 * Provide a way to go to the next item in the Iterator
		 */
		public Order next()
		{
			if(this.hasNext())
			{
				this.currentIndex=currentIndex+1;
				this.currentOrder=Tab.this.orders[this.currentIndex];
			}
			else
			{
				throw new IllegalStateException("There is no more items in this list");
			}
			return this.currentOrder;
		}
		
		/**
		 * Method to get the current index
		 */
		public int getCurrentIndex()
		{
			return this.currentIndex;
		}
		
		public void resetIter()
		{
			this.currentIndex=-1;
			this.currentOrder=null;
		}
		

	}
}