package restaurantAutomationSystem.view;

import java.util.*;

import restaurantAutomationSystem.controllers.SystemInterfaceController;

public class UserInterface {

  //private SystemInterface systemInterface;
  Scanner cmdScanner;

  public UserInterface (){
    //systemInterface = SystemInterface.getSystemInterface();
    SystemInterfaceController.initialize();
  }

  public void start(){
	this.cmdScanner=new Scanner(System.in);
    boolean loopVar = true;
    while (loopVar)
    {
    String screen_lines ="done\n";
    System.out.println("Welcome");
    System.out.println("Choices: \n "+
    		"1 for the current menu\n"+
    		" 2 for all of the menus"+
    		" 3 to open a tab\n"+
    	    " 4 to add a payment\n"+
    		" 5 to place an order\n"+
    		" 6 to view the tab\n"+
    		"7 to split the tab\n"+
    		"8 to pay the bill\n"
    		+ " 9 to exit");

    String item = new String();
    int selection = cmdScanner.nextInt();
      if(selection == 1)
      {
          screen_lines =SystemInterfaceController.getMenu(); 
      }
      else if(selection ==2) 
      {
    	screen_lines=SystemInterfaceController.getAllMenus(); 
      }
      else if(selection == 3)
	  {	
    	  screen_lines=SystemInterfaceController.openTab();
	  }
	  else if(selection == 4)
	  {  
        String[] paymentParams=this.getPaymentInformation();
        String paymentType=paymentParams[0];
        if(paymentType == "Cash")
        {
        	screen_lines=SystemInterfaceController.addCashPayment(paymentParams[1], paymentParams[2]);
        }
        else if(paymentType =="Credit Card")
        {
        	screen_lines=SystemInterfaceController.addCardPayment(paymentParams[1], paymentParams[2],
        			paymentParams[3], paymentParams[4], paymentParams[5] , paymentParams[6 ], 
        			paymentParams[7], paymentParams[8]);
        }
	  }
	  else if(selection == 5)
	  {
		  String[] answers=getMenuItemChoice();
		  if(!(answers == null))
		  {
			  screen_lines=SystemInterfaceController.placeOrder(answers[0], Integer.getInteger(answers[1]),
					  Integer.getInteger(answers[2]), Integer.getInteger(answers[3]));
		  }
	  }
	  else if(selection ==6 )
	  {
		  System.out.println("Enter in the tab number of the tab you would like to see");
		  int tabIndex=this.cmdScanner.nextInt();
		  screen_lines=SystemInterfaceController.getTab(tabIndex);
	  }
	  else if(selection == 7)
	  {
		  System.out.println("Please enter the tab number of the tab you would like to split");
		  int tabIndex=this.cmdScanner.nextInt();
		  screen_lines=SystemInterfaceController.splitTab(tabIndex);
	  }
	  else if(selection ==  8)
	  {
		  System.out.println("Please enter the tab of the bill you want to pay");
		  int tabIndex=this.cmdScanner.nextInt();
		  String tabIndexString=Integer.toString(tabIndex);
		  System.out.println("Please enter the security token for your payment ");
		  String token=this.cmdScanner.nextLine();
		  screen_lines=SystemInterfaceController.payBill(tabIndexString, token);
	  }
	  else if(selection == 9)
	  {
		  screen_lines="Exiting \n";
		  loopVar=false;
	  }
	  else if(selection == 10)
	  {
		   screen_lines="Error 10 is not a valid option select another option";
	  }
	  
	  System.out.println(screen_lines);
    }
  }
  
  public void stop()
  {
	  this.cmdScanner.close();
  }
  
  private String[] getPaymentInformation()
  {
	  String[] params=null;
	  System.out.println("Select a payment type\n\n 1. Cash \n 2. Credit Card\n");
	  int paymentChoice=this.cmdScanner.nextInt();
	  boolean valid=false;
	  while(!valid)
	  {
		  if(paymentChoice == 1)
		  {
			  valid= true;
			  params=new String[3];
			  params[0]="Cash";
			  System.out.println("Please enter in your tab number: ");
			  int tabIndex=this.cmdScanner.nextInt();
			  params[1]=Integer.toString(tabIndex);
			  System.out.println("Please enter in the amount of cash you are adding: ");
			  params[2]=this.cmdScanner.nextLine();
		  }
		  else if(paymentChoice == 2)
		  {
			  valid =true;
			  params=new String[9];
			  params[0]="Credit Card";
			  System.out.println("Please enter in your tab number: ");
			  int tabIndex=this.cmdScanner.nextInt();
			  params[1]=Integer.toString(tabIndex);
			  System.out.println("Please enter in your cards account number: ");
			  params[2]=this.cmdScanner.nextLine();
			  System.out.println("Please enter in your card provider: ");
			  params[3]=this.cmdScanner.nextLine();
			  System.out.println("Please enter in your security token from your card"+
			       "(Remember this you will need it later): ");
			  params[4]=this.cmdScanner.nextLine();
			  System.out.println("Please enter the expiration Month on the card");
			  params[5]=Integer.toString(this.cmdScanner.nextInt());
			  System.out.println("Please enter the expiration Year on the card");
			  params[6]=Integer.toString(this.cmdScanner.nextInt());
			  System.out.println("Please enter the amount available on the card in the format DD.CC "+
			  "where D is US Dollars, and C is US Cents");
			  params[7]=this.cmdScanner.nextLine();
			  System.out.println("Please enter the charge limit on your credit card in the format DD.CC "+
			  " where D is US Dollars, and C is US Cents");  
			  params[8]=this.cmdScanner.nextLine();
		  }
		  else
		  {
			  System.out.println("Please select a valid option Cash or Credit");
		  }
	  }
	  return params;  
  }
  
  
  private String[] getMenuItemChoice()
  {
	  String choice;
	  String[] params=new String[4];
	  System.out.println("Please select a menu item from the current menu, or press q to exit to the main menu");
	  choice=this.cmdScanner.nextLine();
	  if(choice == "q")
	  {
		  return null;
	  }
	  else
	  {
		  params[0]=choice;
		  System.out.println("How many items would you like?");
		  choice=Integer.toString(this.cmdScanner.nextInt());
		  params[1]=choice;
		  System.out.println("Enter the tab number for the tab you would like to add this time to?");
		  choice=Integer.toString(this.cmdScanner.nextInt());
		  params[2]=choice;
		  System.out.println("Enter the order number you would like to add this item to?");
		  choice=Integer.toString(this.cmdScanner.nextInt());
		  params[3]=choice;
		  return params;
	  }
	  
  }
}