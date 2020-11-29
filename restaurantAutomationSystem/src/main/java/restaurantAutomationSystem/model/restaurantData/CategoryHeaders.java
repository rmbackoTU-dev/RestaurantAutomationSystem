package restaurantAutomationSystem.model.restaurantData;

public class CategoryHeaders extends MenuDecorator {

	private String[] categories;
	private int categorySize;
	private int firstEmpty;
	
	public CategoryHeaders()
	{
		this.categorySize=4;
		this.categories=new String[4];
		this.firstEmpty=0;
	}
	
	public CategoryHeaders(String[] categories)
	{
		this.categories=categories;
		this.categorySize=this.categories.length;
		this.firstEmpty=categorySize+1;
	}
	
	public String[] getCategories()
	{
		return this.categories;
	}
	
	
	public void setCategory(int categoryNum, String category)
	{
		if(categoryNum < this.categorySize)
		{
			this.categories[categoryNum]=category;
		}
	}
	
	public void addCategory(String category)
	{
		int tempSize;
		String[] tempCategories;
		if(this.categorySize == this.firstEmpty )
		{
			tempSize=(categorySize*2);
			tempCategories=new String[tempSize];
			try
			{
				MenuItem anItem;
				for(int i=0; i<this.categorySize; i++ )
				{
					tempCategories[i]=this.categories[i];
				}
				this.categories=tempCategories;
				this.categorySize=tempSize;
				this.categories[this.firstEmpty]=category;
				this.firstEmpty=this.firstEmpty+1;
			}
			catch(NullPointerException npe)
			{
				System.err.println("The list which is being copied is empty");
			}
		}
		else
		{
			this.categories[this.firstEmpty]=category;
			this.firstEmpty=firstEmpty+1;
		}
	}
	
	public void removeCategory(int indexOfCategory)
	{
		if(indexOfCategory == -1)
		{
			throw new IllegalStateException("The item iterator is not set to a item");
		}
		boolean last;
		if(indexOfCategory == this.firstEmpty-1)
		{
			last=true;
			this.firstEmpty=indexOfCategory;
			this.categories[indexOfCategory]=null;
		}
		else
		{
		    //Move back all of the indexes of the list by one
		    for(int i=indexOfCategory; i <firstEmpty; i++)
		    {
		    	if(i == firstEmpty-1)
		    	{
		    		this.categories[i]=null;
		    	}
		    	else
		    	{
		    		this.categories[i]=this.categories[i+1];
		    	}
		    	
		    }	
		}
	}
	
	@Override
	public String toString()
	{
		Menu baseMenu=super.getMenu();
		String menuString="\t\tMENU\t\t\n\n\n";
		RestaurantIterator itemIter;
		MenuItem currentItem;
		for(int n=0; n< this.categorySize; n++)
		{
			itemIter=baseMenu.getItemIterator(n);
			menuString=menuString+this.categories[n]+": \n\n\n";
			while(itemIter.hasNext())
			{
				currentItem=(MenuItem) itemIter.next();
				menuString=menuString+"\t"+currentItem.toString()+"\n\n";
			}
		}
		return menuString;
	}
	

}
