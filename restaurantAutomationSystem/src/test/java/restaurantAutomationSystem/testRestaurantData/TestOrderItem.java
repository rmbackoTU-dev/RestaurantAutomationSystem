package restaurantAutomationSystem.testRestaurantData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import restaurantAutomationSystem.model.restaurantData.OrderItem;

public class TestOrderItem {

		@Test
		public void testDefaultConstructor()
		{
			OrderItem testOrderItem=new OrderItem();
			Assertions.assertNotNull(testOrderItem);
		}
		
		@Test
		public void testCopyConstructor()
		{
			OrderItem testOrderItem=new OrderItem(1,1);
			OrderItem copyOrderItem=new OrderItem(testOrderItem);
			Assertions.assertEquals(testOrderItem.getOrderNumber(),
					copyOrderItem.getOrderNumber());
			Assertions.assertEquals(testOrderItem.getOrderQuantity(),
					copyOrderItem.getOrderQuantity());
		}
		
		@Test
		//Test subsumes the test for getOrderNumber
		public void testParameterConstructor()
		{
			OrderItem testOrderItem=new OrderItem(1,1);
			Assertions.assertEquals(1, testOrderItem.getOrderQuantity());
			Assertions.assertEquals(1, testOrderItem.getOrderNumber());
		}
		
		@Test
		public void testSetOrderNumber()
		{
			OrderItem testOrderItem=new OrderItem(1,1);
			testOrderItem.setOrderNumber(2);
			Assertions.assertEquals(2, testOrderItem.getOrderNumber());
		}
		
		@Test
		public void testGetQuantity()
		{
			OrderItem testOrderItem=new OrderItem(1, 2);
			int howMany=testOrderItem.getOrderQuantity();
			Assertions.assertEquals(2, howMany);
		}
		
		@Test
		public void testSetQuantity()
		{
			OrderItem testOrderItem=new OrderItem(1, 2);
			testOrderItem.setQuantity(3);
			int howMany=testOrderItem.getOrderQuantity();
			Assertions.assertEquals(3, howMany);
		}
		
}
