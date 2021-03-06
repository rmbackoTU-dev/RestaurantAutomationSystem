package restaurantAutomationSystem.model.restaurantData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import restaurantAutomationSystem.controllers.billing.BillingStrategy;
import restaurantAutomationSystem.controllers.billing.CashBilling;
import restaurantAutomationSystem.controllers.billing.CashPayment;
import restaurantAutomationSystem.controllers.billing.CreditCardBilling;
import restaurantAutomationSystem.controllers.billing.CreditCardPayment;
import restaurantAutomationSystem.controllers.billing.PaymentStrategy;
import restaurantAutomationSystem.model.ObservableTime;
import restaurantAutomationSystem.model.RestaurantClock;

public class Aggregator {
	
	private Menu[] allMenus;
	private MenuManager manager;
	private MenuEvent[] events;
	private Tab[] tabs;
	private ObservableTime time;
	private  Map<Integer, Integer> tabPaymentRecordMap;
	private PaymentStrategy[] payments;
	public static final int DEFAULT_MAX_TABS=50;
	private int tablesAvailable=10;
	private String[] paymentTypes;
	private int maximumMenus;
	private static int menusAdded=0;
	/**
	 * Default constructor
	 */
	public Aggregator()
	{
		/**
		 * Logic:
		 *   --Start of Menu setup logic
		 *   *Construct a list of menus based on input from menuItemPreloadedData
		 *       *add Menus to allMenus for getAllMenus command  which returns a toString for all Menus
		 *   *Construct a list of events based on input from eventPreloadedData
		 *   *Construct a observableTime to keep track of the time for events
		 *   *Construct a menuManager which keeps track of the current active menu
		 *   *register the menuManager with the observableTime
		 *   	*get currentMenu returns a toString for the current active Menu in menuManager
		 *   
		 *   --End of Menu setup logic--
		 *   
		 *   --Start of Tab setup logic
		 *   *Construct a list of Tabs
		 *   	*For each new table constructed with openTabCommand add a tab to a list of tabs 
		 *      *For split tab call the tabs split tab command to get back a list of tabs, remove the tab
		 *      * that called split tab from the list of tabs and add the list of tabs in its place.
		 *      *For placeOrder check if order exist by checking if getOrderIndex returns index or -1
		 *      then add orderItem to existing order using change order at tab index 
		 *      or create new order for tab. place order command 
		 *      should also give the index of tab it would like to place an order under
		 *      *For removeOrder check if order exist by checking if getOrderIndex returns index or -1
		 *      call removeOrder if order exist using returned index, throw IllegalStateException otherwise
		 *      *For viewTab the system interface finds the appropriate tab index and runs toString on it which
		 *      it returns to the systemInterface the user interface then displays the tab to the user.
		 *    -- End of Tab setup Logic--
		 *    
		 *    --Start of Billing Logic
		 *    * Construct a list of paymentStrategies and a list of BillingStrategies
		 *    	*For openTabCommand a payment type is selected and the user is prompted for the information
		 *       for their payment type. The payment is then added to the appropriate payment file and the system interface
		 *       keeps track of their payment type with a list of payment types indexed by tab index number. Additionally a map of
		 *       tab index to record number is tracked so the appropriate record can be checked on payment
		 *      *For payBillCommand the payment type is retrieved from the system interface and the appropriate authorization question
		 *       is asked of the user. Either user id for the cash payment or security  token for credit card payment. The tab total is computed
		 *       by tab index number, and the associated record number is read in and validated with user input. If the user input matches
		 *       a payment is charged to the record. A boolean is returned to the system interface which allows the system interface to tell the customer if
		 *       they are successful or not. And prompts them to add another form of payment
		 *   	*The addPayment command takes a payment and adds the payment to the appropriate tab index in the system interface
		 *       and additionally maps a new record number for the new record added to the tab index  record number mapping  
		 *      *When three bills have been paid via the payBillCommand payBill also changes the time
		 *    --End of Billing Logic
		 */
		
		//Start of Menu Construction logic
		this.maximumMenus=3;
		this.allMenus=new Menu[this.maximumMenus];
		this.events=new MenuEvent[this.maximumMenus];
		this.time=new ObservableTime(LocalTime.now(), LocalDate.now());

		//end of menu construction logic
		
		this.tabs=new Tab[DEFAULT_MAX_TABS];
		this.tabPaymentRecordMap=new HashMap<Integer, Integer>();
		this.paymentTypes=new String[DEFAULT_MAX_TABS];
	}
	
	
	public Aggregator(int numberOfMenus)
	{
		this.maximumMenus=numberOfMenus;
		this.allMenus=new Menu[this.maximumMenus];
		this.events=new MenuEvent[this.maximumMenus];
		this.time=new ObservableTime(LocalTime.now(), LocalDate.now());
	}
	
	public void setMenuManager(MenuManager manager)
	{
		if(this.manager == null)
		{
			this.manager=manager;
			this.time.register(this.manager);
		}
		else
		{
			this.time.unregister(this.manager);
			this.manager=manager;
			this.time.register(this.manager);
		}
	}
	
	
	public void setLocalTime(LocalTime time)
	{
		this.time.setTime(time);
	}
	
	public void setLocalDate(LocalDate date)
	{
		this.time.setDate(date);
	}
	
	/**
	 * Adds a event to the menu and sets the events menu to the current menu
	 * @param event
	 * @throws IllegalStateException
	 */
	public void addMenuEvent (MenuEvent event)
	throws IllegalStateException
	{
		if(this.manager == null)
		{
			throw new IllegalStateException("Set the Menu Manager first");
		}
		menusAdded=menusAdded+1;
		if(menusAdded < this.maximumMenus)
		{
			this.events[menusAdded]=event;
			this.manager.registerMenuEvent(event);
			MenuData menu=event.getAvailableMenu();
			this.allMenus[menusAdded]=(Menu) menu;
			this.manager.setAvailableMenu(event.getAvailableMenu());
		}
		else
		{
			throw new IllegalStateException("Exceeded the maximum number of menus for this aggregator");
		}
	}
	
	
	
	public MenuEvent[] getAllEvents()
	{
		return this.events;
	}
	
	
	public Menu getCurrentMenu()
	{
		Menu currentMenu=(Menu) this.manager.getAvailableMenu();
		return currentMenu;
	}
	
	
	public int addTab(Tab tab)
	{
		int index=-1;
		//find the first empty index and set the index to place
		//the new subscriber in that place
		if(tablesAvailable > 0)
		{
			while(this.tabs[index] != null)
			{
				index=index+1;
				this.tabs[index]=tab;
			}
			this.tablesAvailable=tablesAvailable-1;
		}
		else
		{
			System.err.println("No tables are available");
		}
		return index;
	}
	
	public void removeTab(int tabIndex)
	throws IllegalArgumentException, NullPointerException
	{
		if(tabIndex < 0 || tabIndex >DEFAULT_MAX_TABS)
		{
			throw new IllegalArgumentException("Not a valid tab Index");
		}
		else if(this.tabs[tabIndex] == null)
		{
			throw new NullPointerException("Tab does not exist in list");
		}
		else
		{
			this.tabs[tabIndex]=null;
			this.tablesAvailable=tablesAvailable+1;
		}
	}
	
	public void changeTab(int tabIndex, Tab newTab)
	{
		this.tabs[tabIndex]=new Tab(newTab);
	}
	
	public Tab getTab(int tabIndex)
	{
		return this.tabs[tabIndex];
	}
	
	private void setPaymentType(int tabIndex, String paymentType)
	throws IllegalArgumentException
	{
		if(tabIndex < 0 || tabIndex >DEFAULT_MAX_TABS)
		{
			throw new IllegalArgumentException("Not a valid tab Index");
		}
		else if(paymentType != "Credit Card" &&  paymentType != "Cash")
		{
			throw new IllegalArgumentException("Restaurant only accepts Credit Card or Cash");
		}
		else
		{
			this.paymentTypes[tabIndex]=paymentType;
		}
	}
	
	
	
	public int setCashPayment(int tabIndex, BigDecimal amount)
	{
		Random uidGenerator=new Random();
		Integer uid=(uidGenerator.nextInt())%1000; //Limit the value to 1000 to stop from getting ridiculous values
		CashPayment payment=CashPayment.cashPaymentFactory();
		HashMap<String,String> paymentParams=new HashMap<String, String>();
		paymentParams.put("id", uid.toString());
		paymentParams.put("cash_amount", amount.toPlainString());
		payment.addPaymentData(paymentParams);
		int recordNum=payment.recordNumForLastRecordAdded();
		this.tabPaymentRecordMap.put(tabIndex, recordNum);
		try
		{
			this.setPaymentType(tabIndex, "Cash");
		}
		catch(IllegalArgumentException iae)
		{
			System.err.println("Something went wrong adding a cash payment, please report this to your IT Staff");
		}
		return uid;
	}
	
	public void setCardPayment(int tabIndex, String accountNum, String provider, String token, String date, BigDecimal amount, BigDecimal chargeLimit )
	{
		CreditCardPayment payment=CreditCardPayment.creditCardPaymentFactory();
		HashMap<String,String> paymentParams=new HashMap<String, String>();
		paymentParams.put("Account-Number", accountNum);
		paymentParams.put("Payment-Provider", provider);
		paymentParams.put("Security-Token", token);
		paymentParams.put("Expiration-Date", date);
		paymentParams.put("Cash-Amount", amount.toPlainString());
		paymentParams.put("Charge-Limit", chargeLimit.toPlainString());
		payment.addPaymentData(paymentParams);
		int recordNum=payment.recordNumForLastRecordAdded();
		this.tabPaymentRecordMap.put(tabIndex, recordNum);
		try
		{
			this.setPaymentType(tabIndex, "Credit Card");
		}
		catch(IllegalArgumentException iae)
		{
			System.err.println("Something went wrong adding a cash payment, please report this to your IT Staff");
		}
	}
	
	public BillingStrategy getBillingStrategy(int tabIndex)
	{
		BillingStrategy newStrategy=null;
		if(tabIndex < 0 || tabIndex >DEFAULT_MAX_TABS)
		{
			throw new IllegalArgumentException("Not a valid tab Index");
		}
		String paymentType=this.paymentTypes[tabIndex];
		int recordNum=this.tabPaymentRecordMap.get(tabIndex);
		if(paymentType == "Cash")
		{
			newStrategy=new CashBilling(recordNum);
		}
		else if(paymentType == "Credit Card")
		{
			newStrategy=new CreditCardBilling(recordNum);
		}
		return newStrategy;
	}
	
	
}
