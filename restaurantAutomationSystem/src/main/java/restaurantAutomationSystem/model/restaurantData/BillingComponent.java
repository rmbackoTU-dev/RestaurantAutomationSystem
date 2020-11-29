package restaurantAutomationSystem.model.restaurantData;

import java.math.BigDecimal;

public interface BillingComponent {

	public BigDecimal getTotalAmount();
	
	public BigDecimal calculateTax();
	
	public BigDecimal getTotalWithTax();
	
	public void displayBill();
	
	public BillingComponent[] splitOrder();
	
	public void addOrder(BillingComponent orderObject);
	
	public void removeFromOrder(RestaurantIterator iter);
	
	
}
