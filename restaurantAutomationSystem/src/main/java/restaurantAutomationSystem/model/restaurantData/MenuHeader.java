package restaurantAutomationSystem.model.restaurantData;

public class MenuHeader extends MenuDecorator {

	private String menuHeader;
	
	public MenuHeader()
	{
		super();
		this.menuHeader="";
	}
	
	public MenuHeader(String header)
	{
		super();
		this.menuHeader=header;
	}
	
	public MenuHeader(MenuData menu)
	{
		super(menu);
		this.menuHeader="";
	}
	
	public MenuHeader(MenuData menu, String header)
	{
		super(menu);
		this.menuHeader=header;
	}
	
	public void setHeader(String header)
	{
		this.menuHeader=header;
	}
	
	
	public String getHeader(String header)
	{
		return header;
	}
	
	@Override
	public String toString()
	{
		String menuString=this.menuHeader+"\n\n\n"+
				super.toString();
		return menuString;
	}
}
