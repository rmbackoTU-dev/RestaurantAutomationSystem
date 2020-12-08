package restaurantAutomationSystem.commandInvokers;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class SplitTabCommand implements RestaurantCommandInterface {

	private Aggregator data;
	private int tabNumber;
	
	public SplitTabCommand(Aggregator systemData, int tabIndex)
	{
		this.data=systemData;
		this.tabNumber=tabIndex;
	}
	
	public Object execute() throws CommandErrorException {
		Tab tab=data.getTab(tabNumber);
		if(tab == null)
		{
			throw new CommandErrorException("Invalid state set Tab first", "Please open a tab "
					+ "and add order to the tab to split the Tab");
		}
		//get the new list of tabs remove the tab that was split then add all the tabs
		Tab[] splitTab=(Tab[]) tab.splitOrder();
		int[] tabIndexArray=null;
		if(splitTab.length > data.DEFAULT_MAX_TABS)
		{
			throw new CommandErrorException("Could not split tab not enough available storage", "Could"
					+ " not split the tab please try again later");
		}
		else
		{
			data.removeTab(this.tabNumber);
			Tab currentTab=null;
			tabIndexArray=new int[splitTab.length];
			for(int i=0; i< splitTab.length; i++)
			{
				currentTab=splitTab[i];
				tabIndexArray[i]=data.addTab(currentTab);
			}
		}	
		return tabIndexArray;
	}

}
