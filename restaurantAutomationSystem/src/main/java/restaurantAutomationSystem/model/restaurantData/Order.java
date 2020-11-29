package restaurantAutomationSystem.model.restaurantData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Order implements BillingComponent {
	//TODO: Remove need to check menu at Order level
	private OrderItem[] orderList;
	private int orderSize=10;
	private int firstEmptyIndex=0;
	//private Menu currentMenu;
	
	
	public Order()
	{
		orderList=new OrderItem[orderSize];
		//currentMenu=new Menu();
	}
	
	public Order(int orderSize)
	{
		this.orderSize=orderSize;
		orderList=new OrderItem[orderSize];
	}
	
	/**
	 * Performs a deep copy of an original menu and uses
	 * it to construct a new menu
	 * @param menuToCopy
	 */
	public Order(Order orderToCopy)
	{
		if(orderToCopy.size() == 0)
		{
			this.orderList=new OrderItem[orderSize];
		}
		else
		{
			 RestaurantIterator iterator=orderToCopy.getAllItemsIterator();
			//Double size to make room for more
			int sizeOfCopy=orderToCopy.size()*2;
			this.orderList=new OrderItem[sizeOfCopy];
			int currentIndex=0;
			OrderItem currentItem;
			while(iterator.hasNext())
			{
				currentItem=(OrderItem) iterator.next();
				currentIndex=iterator.getCurrentIndex();
				this.orderList[currentIndex]=currentItem;
				this.firstEmptyIndex=this.firstEmptyIndex+1;
				
			}
		}
	}
	
	//public Menu getCurrentMenu()
	//{
		//return this.currentMenu;
	//}
	
	public int size()
	{
		return this.firstEmptyIndex;
	}
	
     /**
	 * Adds an item to the orderList
	 * @param item
	 */
	public void addOrder(BillingComponent item)
	throws NullPointerException
	{
		if(this.orderSize == this.firstEmptyIndex)
		{
			this.orderSize=(orderSize*2);
			this.orderList=new OrderItem[orderSize];
			try
			{
				AllOrderItemIterator items=this.getAllItemsIterator();
				OrderItem anItem;
				while(items.hasNext())
				{
					anItem=(OrderItem) items.next();
					this.orderList[items.getCurrentIndex()]=anItem;
				}
			}
			catch(NullPointerException npe)
			{
				System.err.println("The list which is being copied is empty");
			}
		}
		else
		{
			this.orderList[this.firstEmptyIndex]=(OrderItem) item;
			this.firstEmptyIndex=firstEmptyIndex+1;
		}
	}
	
	/**
	 * Give an index with an iterator point to a the list
	 * item that is to be deleted.
	 * If the 
	 */
	public void removeFromOrder(RestaurantIterator itemIter)
	{
		int indexOfItemToRemove=itemIter.getCurrentIndex();
		if(indexOfItemToRemove == -1)
		{
			throw new IllegalStateException("The item iterator is not set to a item");
		}
		boolean last;
		if(indexOfItemToRemove == this.size()-1)
		{
			last=true;
			this.firstEmptyIndex=indexOfItemToRemove;
			this.orderList[indexOfItemToRemove]=null;
		}
		else
		{
			last=false;
		}
		int currentIndex;
		int previousIndex;
		//Move back all of the indexes of the list by one
		while(itemIter.hasNext() && !last)
		{
			itemIter.next();
			currentIndex=itemIter.getCurrentIndex();
			previousIndex=currentIndex-1;
			this.orderList[previousIndex]=this.orderList[currentIndex];
			if(currentIndex == (this.size()-1))
			{
				last=true;
				this.firstEmptyIndex=currentIndex;
				this.orderList[currentIndex]=null;
			}
		}
		itemIter.resetIter();
	}
	
	@Override
	public BigDecimal getTotalWithTax()
	{
		return this.getTotalAmount().add(this.calculateTax());
	}
	
	@Override
	public BigDecimal getTotalAmount() {
		BigDecimal total=new BigDecimal(0);
		total=total.setScale(2, RoundingMode.CEILING);
		RestaurantIterator iter=this.getAllItemsIterator();
		OrderItem item;
		while(iter.hasNext())
		{
			item= (OrderItem) iter.next();
			total=total.add(item.getTotalAmount());
		}
		return total;
	}

	@Override
	public BigDecimal calculateTax() {
		BigDecimal taxAmount=new BigDecimal(0);
		taxAmount=taxAmount.setScale(2, RoundingMode.CEILING);
		RestaurantIterator iter=this.getAllItemsIterator();
		BigDecimal currentTaxAmount=new BigDecimal(0);
		currentTaxAmount=currentTaxAmount.setScale(2, RoundingMode.CEILING);
		BigDecimal taxPercentage=new BigDecimal(0.06);
		taxPercentage=taxPercentage.setScale(2, RoundingMode.CEILING);
		OrderItem item;
		while(iter.hasNext())
		{
			item= (OrderItem) iter.next();
			taxAmount=taxAmount.add(item.calculateTax());
		}
		return taxAmount;
	}

	@Override
	public void displayBill() 
	{
		RestaurantIterator iter=this.getAllItemsIterator();
		OrderItem currentItem;
		int i=0;
		while(iter.hasNext())
		{
			System.out.println(i+"\t");
			currentItem=(OrderItem) iter.next();
			currentItem.displayBill();
			System.out.println("\n\n");
		}
	}
	
	@Override
	public String toString()
	{
	    RestaurantIterator iter=this.getAllItemsIterator();
		OrderItem currentItem;
		int i=0;
		String orderString="";
		while(iter.hasNext())
		{
			orderString=orderString+i+"\t";
			currentItem=(OrderItem) iter.next();
			orderString=orderString+currentItem.toString();
			orderString=orderString+"\n\n";
		}	
		return orderString;
	}

	@Override
	public BillingComponent[] splitOrder() 
	{
		RestaurantIterator iter=this.getAllItemsIterator();
		BillingComponent[] orderItemsArray=new OrderItem[this.size()];
		int i=0;
		while(iter.hasNext())
		{
			orderItemsArray[i]=(OrderItem) iter.next();
		}
		return orderItemsArray;
	}
	
	
	/**
	 * Factory method to return an AllItemsIterator
	 * @return
	 */
	public AllOrderItemIterator getAllItemsIterator()
	{
		AllOrderItemIterator tempItemsIterator=new AllOrderItemIterator();
		return tempItemsIterator;
	}
	
	/**
	 * Class provides an iterator to iterate through the menu list
	 * @author ryan
	 *
	 */
	public class AllOrderItemIterator implements RestaurantIterator
	{
	
		private OrderItem currentItem;
		private int currentIndex;
		
		public AllOrderItemIterator()
        throws NullPointerException
		{
			this.currentIndex=-1;
			this.currentItem=null;
			//see if list is empty if the list is empty throw an exception
			if(Order.this.firstEmptyIndex == 0)
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
			return (currentIndex+1 < Order.this.size());
		}
		
		
		/**
		 * Provide a way to go to the next item in the Iterator
		 */
		public OrderItem next()
		{
			if(this.hasNext())
			{
				this.currentIndex=currentIndex+1;
				this.currentItem=Order.this.orderList[this.currentIndex];
			}
			else
			{
				throw new IllegalStateException("There is no more items in this list");
			}
			return this.currentItem;
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
			this.currentItem=null;
		}
		
	}
}