package restaurantAutomationSystem.model.restaurantData;

import java.math.BigDecimal;
import java.math.RoundingMode;


class PriceIterator implements RestaurantIterator {
	
	private RestaurantIterator allItemIterator;
	private MenuItem currentItem;
	private int currentIndex;
	BigDecimal bigDecimalPrice;

	public PriceIterator(Menu currentMenu, double price)
	{
		allItemIterator=currentMenu.getAllItemsIterator();
		//convert price to BigDecimal price
		Double priceConverter=Double.valueOf(price);
		String priceString=priceConverter.toString();
		this.bigDecimalPrice=new BigDecimal(priceString);
		this.bigDecimalPrice=this.bigDecimalPrice.setScale(2, RoundingMode.CEILING);
		currentIndex=-1;
		currentItem=null;
		
//		boolean foundFirst=false;
//		if(allItemIterator.getItem().getPrice().equals(this.bigDecimalPrice))
//		{
//			this.currentItem=allItemIterator.getItem();
//			this.currentIndex=this.allItemIterator.getCurrentIndex();
//		}
//		else
//		{
//			while(allItemIterator.hasNext() && !foundFirst )
//			{
//				if(allItemIterator.getItem().getPrice().equals(this.bigDecimalPrice))
//				{
//						this.currentItem=allItemIterator.getItem();
//						this.currentIndex=this.allItemIterator.getCurrentIndex();
//						foundFirst=true;
//				}
//				else
//				{
//					allItemIterator.next();
//				}
//			}
//		}
//		if(this.currentItem == null)
//		{
//			throw new IllegalStateException("current Item Is Not Set");
//		}
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
			localCurrentItem=(MenuItem) this.allItemIterator.next();
			if(this.bigDecimalPrice.toPlainString().equals(localCurrentItem.getPrice()))
			{
				itemIterHasNext=true;
			}
		}
		this.resetIter();
		for(int j=-1; j< previousCurrentIndexPointer; j++)
		{
			this.currentItem=(MenuItem) this.allItemIterator.next();
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
			if(this.bigDecimalPrice.toPlainString().equals(tempItem.getPrice()))
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
		this.currentIndex=-1;
		this.currentItem=null;
		
	}
	
	
}