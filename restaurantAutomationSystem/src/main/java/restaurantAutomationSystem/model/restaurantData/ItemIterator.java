package restaurantAutomationSystem.model.restaurantData;

class ItemIterator implements RestaurantIterator {
	
	private RestaurantIterator itemIterator;

	private int currentIndex=-1;
	private MenuItem currentItem;
	private int categoryNum;
	
	public ItemIterator(Menu currentMenu, int category)
	{
		itemIterator=currentMenu.getAllItemsIterator();
		//Do check to make sure category is one of our categories
		if(category < 4 )
		{
			this.categoryNum=category;
		}
		else
		{
			throw new IllegalArgumentException("A valid category was not passed");
		}
		this.currentItem=null;
	}
	
	public void setCategory(int category)
	{
		//Do check to make sure category is one of our categories
		if(category < 4)
		{
			this.categoryNum=category;
		}
		else
		{
			throw new IllegalArgumentException("A valid category was not passed");
		}
	}
		

	public boolean hasNext() {
		//store the previous currentIndex pointer so we call hasNext from
		//the same state in multiple calls.
		int previousCurrentIndexPointer=this.currentIndex;
		boolean itemIterHasNext=false;
		MenuItem localCurrentItem;
		while ((this.itemIterator.hasNext()) && !itemIterHasNext)
		{
			//item Iterator List should emulate the allItemIterator enough
			//without changing the pointer.
			//provides look ahead without changing state.
			this.currentIndex=this.currentIndex+1;
			localCurrentItem=(MenuItem) this.itemIterator.next();
			if(localCurrentItem.getCategory() == this.categoryNum)
			{
				itemIterHasNext=true;
				this.currentItem=localCurrentItem;
			}
		}
		//Reset the item iterator to previous state
		this.resetIter();
		for(int j=-1; j< previousCurrentIndexPointer; j++)
		{
			if(this.itemIterator.hasNext())
			{
				this.currentItem=(MenuItem) this.itemIterator.next();
			}
		}
		this.currentIndex=previousCurrentIndexPointer;
		return itemIterHasNext;
	}


	public MenuItem next() {
		boolean foundNext=false;
		MenuItem tempItem;
		int tempIndex=0;
		while(this.hasNext() && !foundNext)
		{
			tempItem=(MenuItem) this.itemIterator.next();
			tempIndex=this.itemIterator.getCurrentIndex();
			
			if(tempItem.getCategory() == this.categoryNum)
			{
				this.currentItem=tempItem;
				foundNext=true;
			}
			this.currentIndex=tempIndex;
		}
		if(!foundNext)
		{
			throw new IllegalStateException("There is no more items in this list");
		}
		else
		{
			return this.currentItem;
		}

	}

	
	/**
	 * Gets the parents current index so that the iterator can provide 
	 * the correct location to menu to delete from the list.
	 * If getCurrentIndex is used to remove from the list next must 
	 * be called after getCurrent Index to set a new currentIndex
	 * @return
	 */
	public int getCurrentIndex()
	{
		return this.itemIterator.getCurrentIndex();
	}
	
	/**
	 * Used to reset the Items iterator, is a wrapper around the contained allItemsIterator
	 */
	public void resetIter() {
		this.itemIterator.resetIter();
		this.currentIndex=this.itemIterator.getCurrentIndex();
		this.currentItem=null;
	}

}