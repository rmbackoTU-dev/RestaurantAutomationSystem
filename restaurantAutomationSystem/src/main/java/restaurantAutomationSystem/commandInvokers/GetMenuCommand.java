package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.restaurantData.Aggregator;
import restaurantAutomationSystem.restaurantData.Menu;

public class GetMenuCommand implements RestaurantCommandInterface {

	private Menu restaurantMenu;
	
	public GetMenuCommand(Aggregator systemData)
	{
		this.restaurantMenu=systemData.getMenu();
	}
	
	public Object execute() throws CommandErrorException
	{
		return this.restaurantMenu;
	}

}
