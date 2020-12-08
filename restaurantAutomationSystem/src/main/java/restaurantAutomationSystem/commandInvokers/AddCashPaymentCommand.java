package restaurantAutomationSystem.commandInvokers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.restaurantData.Aggregator;

public class AddCashPaymentCommand implements RestaurantCommandInterface {
	
	private Aggregator data;
	private int tabNumber;
	private BigDecimal amountOfCash;
	
	public AddCashPaymentCommand(Aggregator systemData, int tabIndex, 
			BigDecimal amount)
	{
		this.data=systemData;
		this.tabNumber=tabIndex;
		this.amountOfCash=amount;
	}

	/**
	 * Returns true if successful, returns false if fails
	 */
	public Object execute() throws CommandErrorException {
		try
		{
			if(this.tabNumber < 0)
			{
				throw new CommandErrorException("The tab has not yet been opened ",
						"Please open a tab first");
			}
			this.data.setCashPayment(this.tabNumber, amountOfCash);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
