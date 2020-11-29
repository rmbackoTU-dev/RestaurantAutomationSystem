package restaurantAutomationSystem.testRestaurantData;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import restaurantAutomationSystem.model.restaurantData.Order;
import restaurantAutomationSystem.model.restaurantData.OrderItem;
import restaurantAutomationSystem.model.restaurantData.RestaurantIterator;

public class TestOrder {

	private Order testOrder;
	
	@AfterEach
	public void tearDown()
	{
		this.testOrder=null;
	}
	
	@Test
	public void testDefaultConstructor() 
	{
		this.testOrder=new Order();
		Assert.assertEquals(0, this.testOrder.size());
	}
	
	@Test
	public void testParamConstructorException() 
	{
		try
		{
			int testSize=0;
			this.testOrder=new Order(testSize);
		}
		catch(IllegalArgumentException iae)
		{
			Assert.assertThat(iae, CoreMatchers.instanceOf(IllegalArgumentException.class));
		}
	}
	
	@Test
	public void testCopyConstructor()
	{
		this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1, 1);
		OrderItem twoItem=new OrderItem(2,1 );
		OrderItem threeItem=new OrderItem(3, 1);
		this.testOrder.addOrderItem(oneItem);
		this.testOrder.addOrderItem(twoItem);
		this.testOrder.addOrderItem(threeItem);
		Order aCopyOrder=new Order(this.testOrder);
		RestaurantIterator aCopyOrderIterator=aCopyOrder.getAllItemsIterator();
		OrderItem actualItem;
		int currentOrderNumber;
		while(aCopyOrderIterator.hasNext())
		{
			actualItem=(OrderItem) aCopyOrderIterator.next();
			currentOrderNumber=aCopyOrderIterator.getCurrentIndex()+1;
			Assert.assertEquals(currentOrderNumber, 
					actualItem.getOrderNumber());
		}
		
	}
	
	@Test
	public void testAdd() {
		this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1,1);
		this.testOrder.addOrderItem(oneItem);
		RestaurantIterator aMenuIterator=this.testOrder.getAllItemsIterator();
		OrderItem actualItem=(OrderItem) aMenuIterator.next();
		Assert.assertEquals(1, actualItem.getOrderNumber());
	}
	
	@Test
	public void testDeleteWithoutNext()
	{
	    this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1, 1);
		this.testOrder.addOrderItem(oneItem);
		RestaurantIterator aOrderIterator=this.testOrder.getAllItemsIterator();
		Assertions.assertThrows(IllegalStateException.class, () -> 
		{
			testOrder.removeFromOrder(aOrderIterator);
		});
	}
	
	@Test
	public void testDeleteOneItem()
	{
		this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1, 1);
		this.testOrder.addOrderItem(oneItem);
		RestaurantIterator aOrderIterator=this.testOrder.getAllItemsIterator();
		aOrderIterator.next();
		this.testOrder.removeFromOrder(aOrderIterator);
		Assertions.assertThrows(IllegalStateException.class, () -> 
		{
			aOrderIterator.next();
		});
		
	}
	
	@Test
	public void testDeleteTwoItemsLastDelete()
	{
		this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1,1);
		OrderItem twoItem=new OrderItem(2,1 );
		this.testOrder.addOrderItem(oneItem);
		this.testOrder.addOrderItem(twoItem);
		RestaurantIterator aMenuIterator=this.testOrder.getAllItemsIterator();
		aMenuIterator.next();
		aMenuIterator.next();
		this.testOrder.removeFromOrder(aMenuIterator);
		OrderItem currentItem=(OrderItem) aMenuIterator.next();
		Assert.assertEquals(1, currentItem.getOrderNumber());
	}
	
	@Test
	public void testDeleteTwoItemsfirstDelete()
	{
		this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1,1);
		OrderItem twoItem=new OrderItem(2,1);
		this.testOrder.addOrderItem(oneItem);
		this.testOrder.addOrderItem(twoItem);
		RestaurantIterator aMenuIterator=this.testOrder.getAllItemsIterator();
		aMenuIterator.next();
		this.testOrder.removeFromOrder(aMenuIterator);
		OrderItem currentItem=(OrderItem) aMenuIterator.next();
		Assert.assertEquals(2, currentItem.getOrderNumber());
	}
	
	@Test
	public void testDeleteThreeItemsMiddleDelete()
	{
		this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1,1);
		OrderItem twoItem=new OrderItem(2,1);
		OrderItem threeItem=new OrderItem(3,1);
		this.testOrder.addOrderItem(oneItem);
		this.testOrder.addOrderItem(twoItem);
		this.testOrder.addOrderItem(threeItem);
		RestaurantIterator aOrderIterator=this.testOrder.getAllItemsIterator();
		aOrderIterator.next();
		aOrderIterator.next();
		this.testOrder.removeFromOrder(aOrderIterator);
		OrderItem currentItem=(OrderItem) aOrderIterator.next();
		Assert.assertEquals(1, currentItem.getOrderNumber());
	}
	
	@Test
	public void testSize()
	{
		this.testOrder=new Order();
		OrderItem oneItem=new OrderItem(1,1);
		OrderItem twoItem=new OrderItem(2,1);
		OrderItem threeItem=new OrderItem(3,1);
		testOrder.addOrderItem(oneItem);
		testOrder.addOrderItem(twoItem);
		testOrder.addOrderItem(threeItem);
		Assert.assertEquals(3, this.testOrder.size());
	}	
	
	
}
