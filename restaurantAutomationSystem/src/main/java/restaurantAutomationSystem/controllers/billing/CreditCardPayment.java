package restaurantAutomationSystem.controllers.billing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *Non-Thread Safe way to add a payment to the payment record
 * no more than 1 record should be needed because the program
 * should not be run any more than once
**/
public class CreditCardPayment implements PaymentStrategy {

	private static int lastRecord=0;
	private static final String CARD_VERIFICATION_FILE="\\src\\main\\java\\restaurantAutomationSystem\\"
			+ "controller\\billing\\resources\\creditCardVerificationInfo.txt";
	private int numbersOfCharacter=0;
	private ArrayList<Integer> recordStartLocations= new ArrayList<Integer>();
	private static final int RECORD_LINES=8;
	private static CreditCardPayment instance=new CreditCardPayment();
	
	private CreditCardPayment()
	{
		this.syncRecordNumber();
	}
	
	public  static CreditCardPayment creditCardPaymentFactory()
	{
		return instance;
	}
	
	@Override
	public  void addPaymentData(Map<String, String> dataFields) {
	
		if(dataFields.size() < 2)
		{
			throw new IllegalArgumentException("Cash payment must have a user id and cash amount.");
		}
		String accountNumber="";
		String paymentProvider="";
		String securityToken="";
		String expirationDate="";
		String amount="";
		String limit="";
		Set<String> dataFieldKeys=dataFields.keySet();
		Iterator<String> iter= (Iterator<String>) dataFieldKeys.iterator();
		String currentKey;
		while(iter.hasNext())
		{
			currentKey=iter.next();
			if(currentKey == "Account-Number")
			{
				accountNumber=dataFields.get(currentKey);
			}
			else if(currentKey == "Payment-Provider")
			{
				paymentProvider=dataFields.get(currentKey);
			}
			else if(currentKey == "Security-Token")
			{
				securityToken=dataFields.get(currentKey);
			}
			else if(currentKey == "Expiration-Date")
			{
				expirationDate=dataFields.get(currentKey);
			}
			else if(currentKey == "Cash-Amount")
			{
				amount=dataFields.get(currentKey);
			}
			else if(currentKey == "Charge-Limit")
			{
				amount=dataFields.get(currentKey);
			}
			else
			{
				throw new IllegalArgumentException("A none valid record field was passed");
			}
		}
		if((paymentProvider == "") || ( accountNumber == "") ||
				(securityToken == "") || (expirationDate == "")
				|| (amount == "") || (limit == ""))
		{
			throw new IllegalArgumentException("A field may have been given multiple times,"
					+ " and the other not at all");
		}
		this.syncRecordNumber();
		this.writeNewRecord(this.lastRecord, accountNumber, paymentProvider,
					securityToken, expirationDate, amount, limit);
	}
	
	private void writeNewRecord(int recordNum, String accountNum, String provider,
			String token, String date, String amount, String limit)
	{
		File cashConfig= new File(CARD_VERIFICATION_FILE);
		try
		{
			FileOutputStream cashResourceOutput=new FileOutputStream(cashConfig);
			OutputStreamWriter out=new OutputStreamWriter(cashResourceOutput);
			if(accountNum == null)
			{
				throw new IllegalArgumentException("Argument must be set");
			}
			if(provider == null)
			{
				throw new IllegalArgumentException("Argument must be set");
			}
			if(token == null)
			{
				throw new IllegalArgumentException("Argument must be set");
			}
			if(date == null)
			{
				throw new IllegalArgumentException("Argument must be set");
			}
			if(amount == null)
			{
				throw new IllegalArgumentException("Argument must be set");
			}
			if(limit == null)
			{
				throw new IllegalArgumentException("Argument must be set");
			}
			
			String[] outputToWrite= {"Record-Number:"+recordNum+"\n","Account-Number:"+
			accountNum+"\n",
					"Payment-Provider:"+provider+"\n","Security-Token:"+token+"\n",
					"Expiration-Date:"+date+"\n","Cash-Amount:"+amount,"Charge-Limit:"+
					limit+"\n","----"};
			String line;
			for(int i=0; i<RECORD_LINES; i++)
			{
				line=outputToWrite[i];
				for(int j=0; j< line.length(); j++)
				{
					out.write(line.charAt(j));
				}
			}
			out.close();
			cashResourceOutput.close();	
		}
		catch(IOException ioe)
		{
			System.err.println("Error while writing a new record "+ioe.getMessage());
			ioe.printStackTrace();
		}
	}

	@Override
	public int recordNumForLastRecordAdded()
	{
		return this.lastRecord;
	}
	
	private void syncRecordNumber()
	{
		File cashConfig= new File(CARD_VERIFICATION_FILE);
		try
		{
			FileInputStream cashResourceInput=new FileInputStream(cashConfig);
			InputStreamReader in=new InputStreamReader(cashResourceInput);
			int fileChar;
			char fileCharacter;
			String line="";
			String[] keyValue=new String[2];
			while((fileChar=in.read()) !=-1)
			{
				fileCharacter=(char) fileChar;
				this.numbersOfCharacter=this.numbersOfCharacter+1;
				if(fileCharacter == '\n')
				{
					keyValue=line.split(":");
					if(keyValue[0] == "Record-Number")
					{
						this.lastRecord=Integer.parseInt(keyValue[1]);
						this.recordStartLocations.add(this.numbersOfCharacter-keyValue.length);
					}					
				}
				else
				{
					line=line+ Character.toString((char) fileChar);
				}
			}
			in.reset();
			in.close();
			cashResourceInput.close();
		}
		catch(IOException ioe)
		{
			System.err.println("Error while reading record numbers from the cash Resource file: "+
					ioe.getMessage());
			ioe.printStackTrace();
		}
	}
	
	private String[] readRecord(int recordNum)
	{
		int filePointerPosition=this.recordStartLocations.get(recordNum);
		File cashConfig=new File(CARD_VERIFICATION_FILE);
		String[] lines=new String[4];
		this.syncRecordNumber();
		try
		{
			FileInputStream configIn=new FileInputStream(cashConfig);
			InputStreamReader in=new InputStreamReader(configIn);
			System.out.println("The File pointer position is"+configIn.getChannel().position());
			in.skip(filePointerPosition);
			int fileChar;
			char fileCharacter;
			String line="";
			for(int i=0; i< lines.length; i++)
			{
				while((fileChar=in.read()) != '\n')
				{
					fileCharacter=(char) fileChar;
					line=line+fileCharacter;
				}
				lines[i]=line;
				line="";
			}
			in.reset();
			in.close();
			configIn.close();
		}
		catch(IOException ioe)
		{
			System.err.println("Error while reading a record "+ ioe.getMessage());
			ioe.printStackTrace();
		}
		return lines;
	}
	
	@Override
	public Map<String, String> getRecord(int recordNum)
	{
		String[] record=this.readRecord(recordNum);
		Map<String, String> recordHashMap=new HashMap<String, String>();
		String line;
		String[] keyValue=new String[2];
		for(int i=0; i<record.length; i++)
		{
			line=record[i];
			if(!(line == "----"))
			{
				keyValue=line.split(":");
				recordHashMap.put(keyValue[0], keyValue[1]);
			}
		}
		return recordHashMap;
	}
}
