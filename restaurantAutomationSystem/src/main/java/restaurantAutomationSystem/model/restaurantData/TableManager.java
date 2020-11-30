package restaurantAutomationSystem.model.restaurantData;

import restaurantAutomationSystem.model.Observable;

public class TableManager implements Observer{

	public Tab tab;
	public int instanceId;
	
	public TableManager()
	{
		this.tab=new Tab();
	}
	
	public TableManager(Tab newTab)
	{
		this.tab=new Tab(newTab);
	}
	
	@Override
	public String getClassId() {
		return this.getClass().getName();
	}

	@Override
	public int getInstanceId() {
		return this.instanceId;
	}

	@Override
	public void refreshData(Observable tabManager) 
	{
		Tab[] tabManagerData=(Tab[]) tabManager.getObjectFromObservable();
		Tab tabToUpdate=tabManagerData[this.instanceId];
		this.tab=new Tab(tabToUpdate);
	}

	public Tab getTab()
	{
		return tab;
	}
	
	@Override
	public void setInstanceId(int id)
	{
		this.instanceId=id;
	}
}
