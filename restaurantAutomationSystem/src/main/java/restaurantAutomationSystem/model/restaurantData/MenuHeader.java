package restaurantAutomationSystem.model.restaurantData;

public class MenuHeader extends MenuDecorator {

	private String menuHeader;
	
	public MenuHeader()
	{
		this.menuHeader="";
	}
	
	public MenuHeader(String header)
	{
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
