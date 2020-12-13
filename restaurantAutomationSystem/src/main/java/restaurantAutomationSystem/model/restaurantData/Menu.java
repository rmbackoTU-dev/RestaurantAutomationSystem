package restaurantAutomationSystem.model.restaurantData;

public class Menu implements MenuData {

	private MenuItem[] menuList;
	private int menuSize=10;
	private int firstEmptyIndex=0;
	
	
	public Menu()
	{
		menuList=new MenuItem[menuSize];
	}
	
	public Menu(int menuSize)
	throws IllegalArgumentException
	{
		if(menuSize < 1)
		{
			throw new IllegalArgumentException("Menu must be of at least size 1");
		}
		this.menuSize=menuSize;
		menuList=new MenuItem[menuSize];
	}
	
	/**
	 * Performs a deep copy of an original menu and uses
	 * it to construct a new menu
	 * @param menuToCopy
	 */
	public Menu(Menu menuToCopy)
	{
		Menu copyMenu=menuToCopy;
		RestaurantIterator iterator=copyMenu.getAllItemsIterator();
		if(copyMenu.size() == 0)
		{
			this.menuList=new MenuItem[menuSize];
		}
		else
		{
			//double the size to add room for more
			int copySize=copyMenu.size()*2;
			this.menuList=new MenuItem[copySize];
			MenuItem currentItem;
			int currentIndex=0;
			while(iterator.hasNext())
			{
				currentItem=(MenuItem) iterator.next();
				currentIndex=iterator.getCurrentIndex();
				this.menuList[currentIndex]=currentItem;
				this.firstEmptyIndex=this.firstEmptyIndex+1;
			}
		}
	}
		
	
	
	public int size()
	{
		return this.firstEmptyIndex;
	}
	
	/**
	 * Adds an item to the menuList
	 * @param item
	 */
	public void addMenuItem(MenuItem item)
	throws NullPointerException
	{
		int tempSize;
		MenuItem[] tempMenuList;
		if(this.menuSize == this.firstEmptyIndex)
		{
			tempSize=(menuSize*2);
			tempMenuList=new MenuItem[tempSize];
			try
			{
				RestaurantIterator items=this.getAllItemsIterator();
				MenuItem anItem;
				while(items.hasNext())
				{
					anItem=(MenuItem)items.next();
					tempMenuList[items.getCurrentIndex()]=anItem;
				}
				this.menuList=tempMenuList;
				this.menuSize=tempSize;
				this.menuList[this.firstEmptyIndex]=item;
				this.firstEmptyIndex=this.firstEmptyIndex+1;
			}
			catch(NullPointerException npe)
			{
				System.err.println("The list which is being copied is empty");
			}
		}
		else
		{
			this.menuList[this.firstEmptyIndex]=item;
			this.firstEmptyIndex=firstEmptyIndex+1;
		}
	}
	
	/**
	 * Give an index with an iterator point to a the list
	 * item that is to be deleted.
	 * If the item is the last item delete,
	 * if the item is a middle item move all the indexes up by 1
	 */
	public void deleteMenuItem(RestaurantIterator itemIter)
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
			this.menuList[indexOfItemToRemove]=null;
			itemIter.resetIter();
			//reset the iterator to the index of the previous Item
			boolean moreIndexes=true;
			int currentIndex=-1;
			int previousIndex=-1;
			while((currentIndex < indexOfItemToRemove) && moreIndexes)
			{
				if(itemIter.hasNext())
				{
					previousIndex=currentIndex;
					itemIter.next();
					currentIndex=itemIter.getCurrentIndex();
				}
				else
				{
					moreIndexes=false;
				}		
			}
			itemIter.resetIter();
			currentIndex=-1;
			//reset again this time only moving to the previous index
			while(currentIndex != previousIndex)
			{
				itemIter.next();
				currentIndex=itemIter.getCurrentIndex();
			}
			
		}
		else
		{
			last=false;
			int currentIndex;
		    int previousIndex;
		    //Move back all of the indexes of the list by one
		    while(!last && itemIter.hasNext())
		    {
		    	itemIter.next();
		    	currentIndex=itemIter.getCurrentIndex();
		    	previousIndex=currentIndex-1;
		    	//Set previous index regardless in order to move back all items from removed
		    	//index
		    	this.menuList[previousIndex]=this.menuList[currentIndex];
		    	if(currentIndex == (this.size()-1))
		    	{
		    		last=true;
		    		this.firstEmptyIndex=currentIndex;
		    		this.menuList[currentIndex]=null;
		    	}
		    }	
		    itemIter.resetIter();
		    //reset the iterator to the previous index
			currentIndex=-1;
			previousIndex=-1;
			boolean moreIndexes=true;
			while((currentIndex < indexOfItemToRemove) && moreIndexes)
			{
				previousIndex=currentIndex;
				if(itemIter.hasNext())
				{
					previousIndex=currentIndex;
					itemIter.next();
					currentIndex=itemIter.getCurrentIndex();
				}
				else
				{
					moreIndexes=false;
				}
					
			}
			itemIter.resetIter();
			currentIndex=-1;
			//reset again this time only moving to the previous index
			while(currentIndex != previousIndex)
			{
				itemIter.next();
				currentIndex=itemIter.getCurrentIndex();
			}
		}
	}
	
	/**
	 * Returns if an order ins in a Menu
	 * @param orderNumber
	 * @return true if found, otherwise false
	 */
	public boolean isOrderInMenu(int orderNumber)
	{
		RestaurantIterator searchIterator=this.getAllItemsIterator();
		boolean found=false;
		MenuItem tempItem;
		while(!found & searchIterator.hasNext())
		{
			tempItem=(MenuItem) searchIterator.next();
			if(tempItem.getOrderNumber() == orderNumber)
			{
				found=true;
			}
		}
		return found;
	}
	
	
	@Override
	public String toString()
	{
		String menuString="\t\tMENU\t\t\n\n\n";
		MenuItem currentItem;
		RestaurantIterator iter=this.getAllItemsIterator();
		while(iter.hasNext())
		{
			currentItem=(MenuItem) iter.next();
			menuString=menuString+"\t\t"+currentItem.toString()+"\n";
		}
		return menuString;
	}
	
	/**
	 * Factory method to return an AllItemsIterator
	 * @return
	 */
	public RestaurantIterator getAllItemsIterator()
	throws NullPointerException
	{
		RestaurantIterator tempItemsIterator=new AllItemsIterator();
		return tempItemsIterator;
	}
	
	
	/**
	 * Factory method to return an ItemIterator
	 *@return AllItemIterator<itemIterator>
	 */
	public RestaurantIterator getItemIterator(int category)
	throws NullPointerException
	{
		
		RestaurantIterator tempItemIterator=new ItemIterator(this, category);
		return tempItemIterator;
	}
	
	/**
	 * Factory method to return an HeartHealthyIterator
	 *@return AllItemIterator<HeartHealthyIterator>
	 */
	public RestaurantIterator getHeartHealthyIterator(boolean heartHealthy)
	throws NullPointerException
	{
		
		RestaurantIterator tempHeartHealthIterator=new HeartHealthyIterator(this, heartHealthy);
		return tempHeartHealthIterator;
	}

	
	/**
	 * Factory method to return an PriceIterator
	 *@return AllItemIterator<PriceIterator>
	 */
	public RestaurantIterator getPriceIterator(double price)
	throws NullPointerException
	{
		
		RestaurantIterator tempPriceIterator=new PriceIterator(this, price);
		return tempPriceIterator;
	}
	
	
	
	/**
	 * Class provides an iterator to iterate through the menu list
	 * @author ryan
	 *
	 */
	public class AllItemsIterator implements RestaurantIterator
	{
	
		private MenuItem currentItem;
		private int currentIndex;
		
		public AllItemsIterator()
		{
			this.currentIndex=-1;
			this.currentItem=null;
			//see if list is empty and throw Null pointer exception if it is 
			if((Menu.this.firstEmptyIndex == 0))
			{
				throw new NullPointerException("List is empty");
			}
		}
		
		/**
		 * Provide a way to determine if any items
		 * are left if not throw an exception
		 * @return
		 */
		public boolean hasNext()
		{
			return (currentIndex+1 < Menu.this.size());
		}
		
		
		/**
		 * Provide a way to go to the next item in the Iterator
		 * and then return the item to the user
		 */
		public MenuItem next()
		{
			if(this.hasNext())
			{
				this.currentIndex=currentIndex+1;
				this.currentItem=Menu.this.menuList[this.currentIndex];
				return this.currentItem;
			}
			else
			{
				throw new IllegalStateException("There is no more items in this list");
			}
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