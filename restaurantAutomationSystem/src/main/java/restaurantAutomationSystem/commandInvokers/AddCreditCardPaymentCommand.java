package restaurantAutomationSystem.commandInvokers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import restaurantAutomationSystem.commandInvokers.exceptions.CommandErrorException;
import restaurantAutomationSystem.model.RestaurantClock;
import restaurantAutomationSystem.model.restaurantData.Aggregator;

public class AddCreditCardPaymentCommand implements RestaurantCommandInterface {

	private Aggregator data;
	private int tabNumber;
	private String account_Number;
	private String payment_Provider;
	private String security_Token;
	private String expiration_date;
	private String charge_limit;
	private String  cash_amount;
	
	public AddCreditCardPaymentCommand(Aggregator systemData, int tabIndex,
			String accountNum, String provider, String token, String expiration,
			String amount, String limit)
	{
		this.data=systemData;
		this.tabNumber=tabIndex;
		this.account_Number=accountNum;
		this.payment_Provider=provider;
		this.security_Token=token;
		this.charge_limit=limit;
		this.cash_amount=amount;
		this.expiration_date=expiration;
		
	}
	
	public Object execute() throws CommandErrorException {
		if(this.tabNumber < 0)
		{
			throw new CommandErrorException("The tab has not yet been opened ",
						"Please open a tab first");
		}
		
		if(account_Number == "" || account_Number == null)
		{
			throw new CommandErrorException("Account number is null", "An account number was not set please"
					+ " check your input and try again");
		}
		
		if(payment_Provider == "" || payment_Provider == null)
		{
			throw new CommandErrorException("Payment provider is null", "A payment provider was not set please"
					+" check your input and try again");
		}
		
		if(security_Token == "" || security_Token == null)
		{
			throw new CommandErrorException("Security token is null", "A security token  was not set please"
					+" check your input and try again");
		}
		
		if(expiration_date == "" || expiration_date == null)
		{
			throw new CommandErrorException("Expiration Date is null", "A expiration date was not set please"
					+" check your input and try again");
		}
		
		if(cash_amount == "" || cash_amount == null)
		{
			throw new CommandErrorException("Cash Amount is null", "A cash amount was not set please"
					+" check your input and try again");
		}
		
		if(charge_limit == "" || charge_limit == null )
		{
			throw new CommandErrorException("Charge Limit is null", "A charge limit amount was not set please"
					+" check your input and try again");
		}
		
		Pattern currencyPattern=Pattern.compile("\\d+.\\d\\d");
		Pattern datePattern=Pattern.compile("//d//d//-//d//d");
		Matcher limitMatcher=currencyPattern.matcher(this.charge_limit);
		Matcher amountMatcher=currencyPattern.matcher(this.cash_amount);
		Matcher expirationMatcher=datePattern.matcher(this.expiration_date);
		
		if(!(limitMatcher.matches()))
		{
			throw new CommandErrorException("Charge limit is not in currency format", "Enter the charge limit in "
					+"the format D(D).CC, where D is Dollars, C is Cents and () means any number of additional places");
		}
		
	    if(!(amountMatcher.matches()))
		{
			throw new CommandErrorException("Cash Amount is not in currency format", "Enter the Cash amount in "
					+"the format D(D).CC, where D is Dollars, C is Cents and () means any number of additional places");
		}
	    
	    if(!(expirationMatcher.matches()))
		{
			throw new CommandErrorException("Expiration Date is not in currency format", "Enter the Expiration Date in "
					+"the format MM-YY, where M is Month, Y is Years");
		}
	    
	    
		
		BigDecimal bigDecimalCashAmount=new BigDecimal(cash_amount);
		bigDecimalCashAmount=bigDecimalCashAmount.setScale(2, RoundingMode.CEILING);
		BigDecimal bigDecimalChargeLimit=new BigDecimal(charge_limit);
		bigDecimalChargeLimit=bigDecimalChargeLimit.setScale(2, RoundingMode.CEILING);
		try
		{
			this.data.setCardPayment(this.tabNumber, account_Number,
					this.payment_Provider, this.security_Token, this.expiration_date, bigDecimalCashAmount,
					bigDecimalChargeLimit);
			return true;
		}
		catch(IllegalArgumentException e)
		{
			return false;
		}
		
		
	}

}
