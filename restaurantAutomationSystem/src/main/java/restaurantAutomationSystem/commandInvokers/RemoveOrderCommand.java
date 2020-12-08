package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class RemoveOrderCommand implements RestaurantCommandInterface {
	
	private int tabNum;
	private Aggregator data;
	private int orderNum;
	
	public RemoveOrderCommand(Aggregator systemData, int tabIndex, int orderIndex)
	{
		this.tabNum=tabIndex;
		this.data=systemData;
		this.orderNum=orderIndex;
	}

	public Object execute() throws CommandErrorException {
		try
		{
			Tab currentTab=this.data.getTab(this.tabNum);
			RestaurantIterator iter=currentTab.getAllTabItemsIterator();
			int i=0;
			while(iter.hasNext() && (i != this.orderNum))
			{
				iter.next();
				i=iter.getCurrentIndex();
			}
			currentTab.removeFromOrder(iter);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
