package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Menu;
import restaurantAutomationSystem.model.restaurantData.MenuEvent;

public class GetAllMenusWithTimeCommand implements RestaurantCommandInterface {
	
	private MenuEvent[] allEvents;
	
	public GetAllMenusWithTimeCommand(Aggregator systemData)
	{
		this.allEvents=systemData.getAllEvents();
		
	}

	public Object execute() throws CommandErrorException {
		return this.allEvents;
	}

}
