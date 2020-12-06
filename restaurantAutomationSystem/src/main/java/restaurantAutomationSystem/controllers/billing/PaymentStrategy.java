package restaurantAutomationSystem.controllers.billing;

import java.util.Map;

public  interface PaymentStrategy {

	public void addPaymentData(Map<String, String> dataFields);
	
	public Map<String, String> getRecord(int recordNum);

	public int recordNumForLastRecordAdded();

}
