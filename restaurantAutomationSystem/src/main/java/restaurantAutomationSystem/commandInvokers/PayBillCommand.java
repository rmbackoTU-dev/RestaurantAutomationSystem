package restaurantAutomationSystem.commandInvokers;

import java.math.BigDecimal;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.controllers.billing.BillingStrategy;
import restaurantAutomationSystem.model.restaurantData.Aggregator;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class PayBillCommand implements RestaurantCommandInterface {
	
	private Aggregator data;
	private int tabNumber;
	private String token;
	
	public PayBillCommand(Aggregator systemData, int tabIndex, String securityToken)
	{
		this.data=systemData;
		this.tabNumber=tabIndex;
		this.token=securityToken;
	}

	public Object execute() throws CommandErrorException 
	{
		BillingStrategy strategy=data.getBillingStrategy(this.tabNumber);
		if(strategy == null)
		{	
			throw new CommandErrorException("Invalid State Please set Payment first", 
					"Please add a payment in order to pay");
		}
		Tab tab=data.getTab(this.tabNumber);
		if(tab == null)
		{
			throw new CommandErrorException("Invaild State set Tab and Payment first",
					"Please open a tab, then choose a payment type to pay");
		}
		strategy.authenticate(this.token);
		BigDecimal total=tab.getTotalWithTax();
		Boolean success=strategy.payBill(total);
		return success;
	}

}
