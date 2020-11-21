package restaurantAutomationSystem.commandInvokers.exceptions;

public class CommandErrorException extends Throwable {
	private String programmerMessage;
	private String userMessage;

	/**
	 * Create a custom Command Exception so we can forward
	 * errors to the user, but additionally display errors for the 
	 * programmer when debugging.
	 * @param programmerMessage
	 * @param userMessage
	 */
	public CommandErrorException(String programmerMessage,
								String userMessage)
	{
		super();
		this.programmerMessage=programmerMessage;
		this.userMessage=userMessage;
	}
	
	public String getUserMessage()
	{
		return this.userMessage;
	}
	
	public String getProgrammerMessage()
	{
		return this.programmerMessage;
	}
	
	/**
	 * To string for our custom exception
	 */
	@Override
	public String toString()
	{
		String errorString=super.toString();
		errorString=errorString+"\n\n"+
		"User Error: "+this.userMessage+"\n"+
		"Programmer Error"+this.programmerMessage+"\n";
		return errorString;
	}
}
