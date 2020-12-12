package restaurantAutomationSystem.model.restaurantData;

class HeartHealthyIterator implements RestaurantIterator {
	private RestaurantIterator allItemIterator;
	private MenuItem currentItem;
	private int currentIndex;
	private boolean heartHealth;

	public HeartHealthyIterator(Menu currentMenu, boolean heartHealthy)
	{
		this.heartHealth=heartHealthy;
		this.allItemIterator=currentMenu.getAllItemsIterator();
		this.currentIndex=-1;
		this.currentItem=null;
	}
		
	
	public boolean hasNext() {
		//store the previous currentIndex pointer so we call hasNext from
				//the same state in multiple calls.
				int previousCurrentIndexPointer=this.currentIndex;
				boolean itemIterHasNext=false;
				MenuItem localCurrentItem;
				while ((this.allItemIterator.hasNext()) && !itemIterHasNext)
				{
					//item Iterator List should emulate the allItemIterator enough
					//without changing the pointer.
					//provides look ahead without changing state.
					this.currentIndex=this.currentIndex+1;
					localCurrentItem=(MenuItem) allItemIterator.next();
					if(localCurrentItem.isHeartHealthy() == this.heartHealth )
					{
						itemIterHasNext=true;
					}
				}
				this.resetIter();
				for(int j=-1; j< previousCurrentIndexPointer; j++)
				{
					if(this.allItemIterator.hasNext())
					{
						this.currentItem=(MenuItem) this.allItemIterator.next();
					}
				}
				this.currentIndex=previousCurrentIndexPointer;
				return itemIterHasNext;
	}


	public MenuItem next() {
		boolean foundNext=false;
		MenuItem tempItem;
		while(this.hasNext() && !foundNext)
		{
			tempItem=(MenuItem) this.allItemIterator.next();
			this.currentIndex=this.allItemIterator.getCurrentIndex();
			if(tempItem.isHeartHealthy() == this.heartHealth)
			{
				this.currentItem=tempItem;
				foundNext=true;
			}
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
		return this.allItemIterator.getCurrentIndex();
	}


	public void resetIter() {
		this.allItemIterator.resetIter();
		this.currentIndex=this.allItemIterator.getCurrentIndex();
		this.currentItem=null;

	}

}