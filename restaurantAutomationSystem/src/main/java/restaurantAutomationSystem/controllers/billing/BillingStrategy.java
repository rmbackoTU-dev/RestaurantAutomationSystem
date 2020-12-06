package restaurantAutomationSystem.controllers.billing;

import java.math.BigDecimal;

public interface BillingStrategy {
	public boolean payBill(BigDecimal total) throws IllegalStateException;
	
	public void authenticate(String verificationId);
	
	public int getRecordNum();
}
