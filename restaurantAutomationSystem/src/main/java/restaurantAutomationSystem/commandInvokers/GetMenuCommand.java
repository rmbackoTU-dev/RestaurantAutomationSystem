package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;

public class GetMenuCommand implements RestaurantCommandInterface {

	private Menu restaurantMenu;
	
	public GetMenuCommand(Aggregator systemData)
	{
		this.restaurantMenu=systemData.getCurrentMenu();
	}
	
	public Object execute() throws CommandErrorException
	{
		return this.restaurantMenu;
	}

}
