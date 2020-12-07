package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class GetTabCommand implements RestaurantCommandInterface {

	private Tab tabData;
	
	public GetTabCommand(Aggregator systemData, int tabIndex)
	{
		this.tabData=systemData.getTab(tabIndex);
	}
	
	public Tab execute() throws CommandErrorException {
		return this.tabData;
	}

}