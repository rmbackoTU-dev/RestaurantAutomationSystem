package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;

public class GetMenuCommand implements RestaurantCommandInterface {

	private Aggregator data;
	
	public GetMenuCommand(Aggregator systemData)
	{
		this.data=systemData;
	}
	
	public Object execute() throws CommandErrorException
	{
		Menu currentMenu=this.data.getCurrentMenu();
		return currentMenu;
	}

}
