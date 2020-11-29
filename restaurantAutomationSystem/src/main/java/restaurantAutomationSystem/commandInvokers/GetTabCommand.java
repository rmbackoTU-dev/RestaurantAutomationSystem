package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class GetTabCommand implements RestaurantCommandInterface {

	private Tab tabData;
	
	public GetTabCommand(Aggregator systemData)
	{
		this.tabData=systemData.getTab();
	}
	
	public Tab execute() throws CommandErrorException {
		return this.tabData;
	}

}