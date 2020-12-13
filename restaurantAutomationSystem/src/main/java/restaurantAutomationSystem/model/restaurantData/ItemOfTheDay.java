package restaurantAutomationSystem.model.restaurantData;

public class ItemOfTheDay extends MenuDecorator {

	private MenuItem[] itemsOfTheDay;
	private int firstEmpty;
	private int sizeOfList;
	
	public ItemOfTheDay()
	{
		super();
		this.firstEmpty=0;
		this.sizeOfList=5;
		this.itemsOfTheDay=new MenuItem[5];
	}
	
	public ItemOfTheDay(MenuItem[] items)
	{
		super();
		this.firstEmpty=items.length+1;
		this.sizeOfList=items.length;
		this.itemsOfTheDay=items;
	}
	
	public ItemOfTheDay(MenuData menu)
	{
		super(menu);
		this.firstEmpty=0;
		this.sizeOfList=5;
		this.itemsOfTheDay=new MenuItem[5];
	}
	
	public ItemOfTheDay(MenuData menu, MenuItem[] items)
	{
		super(menu);
		this.firstEmpty=items.length+1;
		this.sizeOfList=items.length;
		this.itemsOfTheDay=items;
	}
	
	@Override
	public String toString()
	{
		String menuString=super.toString();
		menuString=menuString+"\n\n Items of the Day \n\n";
		for(int i=0; i< itemsOfTheDay.length; i++)
		{
			menuString=menuString+"\t\t"+itemsOfTheDay[i].toString()+"\n";
		}
		return menuString;		
	}
	
	public void addCategory(MenuItem item)
	{
		int tempSize;
		MenuItem[] tempCategories;
		if(this.sizeOfList == this.firstEmpty )
		{
			tempSize=(this.sizeOfList*2);
			tempCategories=new MenuItem[tempSize];
			try
			{
				for(int i=0; i<this.sizeOfList; i++ )
				{
					tempCategories[i]=this.itemsOfTheDay[i];
				}
				this.itemsOfTheDay=tempCategories;
				this.sizeOfList=tempSize;
				this.itemsOfTheDay[this.firstEmpty]=item;
				this.firstEmpty=this.firstEmpty+1;
			}
			catch(NullPointerException npe)
			{
				System.err.println("The list which is being copied is empty");
			}
		}
		else
		{
			this.itemsOfTheDay[this.firstEmpty]=item;
			this.firstEmpty=firstEmpty+1;
		}
	}
	
	public void removeCategory(int indexOfItem)
	{
		if(indexOfItem == -1)
		{
			throw new IllegalStateException("The item iterator is not set to a item");
		}
		if(indexOfItem == this.firstEmpty-1)
		{
			this.firstEmpty=indexOfItem;
			this.itemsOfTheDay[indexOfItem]=null;
		}
		else
		{
		    //Move back all of the indexes of the list by one
		    for(int i=indexOfItem; i <firstEmpty; i++)
		    {
		    	if(i == firstEmpty-1)
		    	{
		    		this.itemsOfTheDay[i]=null;
		    	}
		    	else
		    	{
		    		this.itemsOfTheDay[i]=this.itemsOfTheDay[i+1];
		    	}
		    	
		    }	
		}
	}
}
