package restaurantAutomationSystem.controllers.billing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

import restaurantAutomationSystem.controllers.billing.CreditCardPayment;
import restaurantAutomationSystem.model.restaurantData.Tab;

public class CreditCardBilling implements BillingStrategy {

	private int recordNumber;
	private boolean verified=false;
	private Map<String, String> billingParameters;
	private String verifySecurityCode;
	
	public CreditCardBilling(int recordNum)
	{
		this.recordNumber=recordNum;
		CreditCardPayment payment=CreditCardPayment.creditCardPaymentFactory();
		this.billingParameters=payment.getRecord(this.recordNumber);
		this.verifySecurityCode=this.billingParameters.get("Security-Token");
	}
	
	@Override
	public boolean payBill(BigDecimal total) throws IllegalStateException {
		CreditCardPayment payment=CreditCardPayment.creditCardPaymentFactory();
		if(this.verified)
		{
			//check the date
			String expirationDate=this.billingParameters.get("Expiration-Date");
			LocalDate today=LocalDate.now();
			int month=today.getMonthValue();
			int year=today.getYear();
			String[] monthYear=new String[2];
			monthYear=expirationDate.split("-");
			int expirationMonth=Integer.valueOf(monthYear[0]);
			int expirationYear=Integer.valueOf(monthYear[1]);
			if(year > expirationYear)
			{
				return false;
			}
			else if(expirationYear == year)
			{
				if(month > expirationMonth)
				{
					return false;
				}
			}
			//check the credit limit
			String creditLimit=this.billingParameters.get("Charge-Limit");
			BigDecimal chargeLimit=new BigDecimal(creditLimit);
			chargeLimit=chargeLimit.setScale(2, RoundingMode.CEILING);
			if(total.compareTo(chargeLimit) == 1)
			{
				return false;
			}
			//check the money available
			String cashOnCard=this.billingParameters.get("Cash-Amount");
			BigDecimal amountForCustomer=new BigDecimal(cashOnCard);
			if((total.compareTo(amountForCustomer) == -1) || 
					total.equals(amountForCustomer))
			{
				BigDecimal newAmount=amountForCustomer.subtract(total);
				this.billingParameters.put("cash_amount", newAmount.toPlainString());
				payment.addPaymentData(this.billingParameters);
				this.recordNumber=payment.recordNumForLastRecordAdded();
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			throw new IllegalStateException("Please authenticate your bill first");
		}
	}

	@Override
	public void authenticate(String verificationId) {
		String securityToken=this.billingParameters.get("Security-Token");
		this.verified=(securityToken == verificationId);
	}
	
	@Override
	public int getRecordNum()
	{
		return this.recordNumber;
	}

	

}
