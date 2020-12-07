package restaurantAutomationSystem.model.restaurantData;

import restaurantAutomationSystem.model.Observable;

public class TableManager implements Observer{

	private Tab tab;
	private int instanceId;
	private int tableId;
	
	public TableManager()
	{
		this.tab=new Tab();
	}
	
	public TableManager(Tab newTab)
	{
		this.tab=new Tab(newTab);
	}
	
	public String getClassId() {
		return this.getClass().getName();
	}

	public int getInstanceId() {
		return this.instanceId;
	}

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
	
	public void setInstanceId(int id)
	{
		this.instanceId=id;
	}
	
	public void setTableId(int id)
	{
		this.tableId=id;
	}
	
	public int getTableId()
	{
		return this.tableId;
	}
}
