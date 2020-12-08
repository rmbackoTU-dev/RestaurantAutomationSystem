package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class OpenTabCommand implements RestaurantCommandInterface {
	
	private Tab tabToAdd;
	private Aggregator data;
	
	public OpenTabCommand(Aggregator systemData, Tab newTab)
	{
		this.tabToAdd=newTab;
	}

	public Object execute() throws CommandErrorException {
		int tabIndex=this.data.addTab(this.tabToAdd);
		if(tabIndex != -1)
		{
			return tabIndex;
		}
		else
		{
			throw new CommandErrorException("The tab could not be added to the tab list"
					+ "no more space is available"," It looks like we are busy right now please try again soon");
		}
	}

}
