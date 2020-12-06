package restaurantAutomationSystem.controllers.billing;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Non-Thread Safe way to add a payment to the payment record
 * no more than 1 record should be needed because the program
 * should not be run any more than once
 * @author Ryan Backof
 *
 */
public class CashPayment implements PaymentStrategy {

	private static int lastRecord=0;
	private static final String CASH_VERIFICATION_FILE="\\src\\main\\java\\restaurantAutomationSystem\\"
			+ "controller\\billing\\resources\\cashAmountVerification.txt";
	private int numbersOfCharacter=0;
	private ArrayList<Integer> recordStartLocations= new ArrayList<Integer>();
	private static final int RECORD_LINES=4;
	private  static CashPayment instance=new CashPayment();
	
    private CashPayment()
    {
    	this.syncRecordNumber();
    }
    
    public static CashPayment cashPaymentFactory()
    {
    	return instance;
    }
	
	@Override
	public  void addPaymentData(Map<String, String> dataFields) {
	
		if(dataFields.size() < 2)
		{
			throw new IllegalArgumentException("Cash payment must have a user id and cash amount.");
		}
		String userID="";
		String cashAmount="";
		Set<String> dataFieldKeys=dataFields.keySet();
		Iterator<String> iter= (Iterator<String>) dataFieldKeys.iterator();
		String currentKey;
		while(iter.hasNext())
		{
			currentKey=iter.next();
			if(currentKey == "id")
			{
				userID=dataFields.get(currentKey);
			}
			else if(currentKey == "cash_amount")
			{
				cashAmount=dataFields.get(currentKey);
			}
			else
			{
				throw new IllegalArgumentException("A none valid record field was passed");
			}
		}
		if((cashAmount == "") || (userID == ""))
		{
			throw new IllegalArgumentException("A field may have been given multiple times,"
					+ " and the other not at all");
		}
		this.writeNewRecord(this.lastRecord, userID, cashAmount);
	}

	@Override
	public int recordNumForLastRecordAdded()
	{
		return this.lastRecord;
	}
	
	private void writeNewRecord(int recordNum, String id, String amount)
	{
		File cashConfig= new File(CASH_VERIFICATION_FILE);
		try
		{
			FileOutputStream cashResourceOutput=new FileOutputStream(cashConfig);
			OutputStreamWriter out=new OutputStreamWriter(cashResourceOutput);
			String[] outputToWrite= {"Record-Number:"+recordNum+"\n","id:"+id+"\n",
					"cash_amount:"+amount,"----"};
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
	
	private void syncRecordNumber()
	{
		File cashConfig= new File(CASH_VERIFICATION_FILE);
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
		File cashConfig=new File(CASH_VERIFICATION_FILE);
		String[] lines=new String[4];
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
