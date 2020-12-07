package restaurantAutomationSystem.controllers.billing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import restaurantAutomationSystem.controllers.billing.CashPayment;

import restaurantAutomationSystem.model.restaurantData.Tab;

public class CashBilling implements BillingStrategy {

	private int recordNumber;
	private Map<String, String> billingParameters;
	private String verifyID;
	private boolean verified;
	
	public CashBilling(int recordNum)
	{
		this.recordNumber=recordNum;
		CashPayment payment=CashPayment.cashPaymentFactory();
		this.billingParameters=payment.getRecord(this.recordNumber);
		verifyID=this.billingParameters.get("id");
		verified=false;
	}
	
	/**
	 * Main Strategy function to complete a payment of a bill
	 * @return true if success, false if failure
	 */
	public boolean payBill(BigDecimal total) 
	throws IllegalStateException
	{
		this.refreshCashFromFile();
		BigDecimal amountForCustomer;
		CashPayment payment=CashPayment.cashPaymentFactory();
		if(this.verified)
		{
			amountForCustomer=new BigDecimal(this.billingParameters.get("cash_amount"));
			amountForCustomer=amountForCustomer.setScale(2, RoundingMode.CEILING);
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
			throw new IllegalStateException(""
					+ "Please authenticate your payment to pay");
		}
		
	}
	
	/**
	 * Authenticates the payment before paying
	 */
	public void authenticate(String id)
	{
		this.verified=(verifyID == id);
	}
	
	
	private void refreshCashFromFile()
	{
		CashPayment payment=CashPayment.cashPaymentFactory();
		this.billingParameters=payment.getRecord(this.recordNumber);
	}
	
	/**
	 * Returns the record number being used for this record number
	 * @return
	 */
	public int getRecordNum()
	{
		return this.recordNumber;
	}

}
