package restaurantAutomationSystem.model.restaurantData;

public class TableManager implements Observer{

	public Tab[] tabs;
	
	@Override
	public int getClassId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInstanceId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
	}

	public Tab getTab(int Tabid)
	{
		return tabs[Tabid];
	}
	
	public Tab[] getTabs()
	{
		return tabs;
	}
}
