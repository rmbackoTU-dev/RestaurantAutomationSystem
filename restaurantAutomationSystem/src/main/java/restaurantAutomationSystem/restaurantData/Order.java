package restaurantAutomationSystem.restaurantData;


public class Order {

	private OrderItem[] orderList;
	private int orderSize=10;
	private int firstEmptyIndex=0;
	
	
	public Order()
	{
		orderList=new OrderItem[orderSize];
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
	
	public int size()
	{
		return this.firstEmptyIndex;
	}
	
/**
	 * Adds an item to the orderList
	 * @param item
	 */
	public void addOrderItem(OrderItem item)
	throws NullPointerException
	{
		if(this.orderSize == this.firstEmptyIndex)
		{
			this.orderSize=(orderSize*2);
			this.orderList=new OrderItem[orderSize];
			try
			{
				AllItemsIterator items=this.getAllItemsIterator();
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
			this.orderList[this.firstEmptyIndex]=item;
			this.firstEmptyIndex=firstEmptyIndex+1;
		}
	}
	
	/**
	 * Give an index with an iterator point to a the list
	 * item that is to be deleted.
	 * If the 
	 */
	public void deleteOrderItem(RestaurantIterator itemIter)
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
	

	
	/**
	 * Factory method to return an AllItemsIterator
	 * @return
	 */
	public AllItemsIterator getAllItemsIterator()
	{
		AllItemsIterator tempItemsIterator=new AllItemsIterator();
		return tempItemsIterator;
	}
	
	/**
	 * Class provides an iterator to iterate through the menu list
	 * @author ryan
	 *
	 */
	public class AllItemsIterator implements RestaurantIterator
	{
	
		private OrderItem currentItem;
		private int currentIndex;
		
		public AllItemsIterator()
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