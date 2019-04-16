package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private ContactDetails contactDetails;

    private OrderService orderService;
    private DeliveryMode deliveryMode;
    private PaymentMethod paymentMethod;
    private Order order;

    @Before
    public void setup() {
        orderService = OrderServiceImpl.getInstance();
        deliveryMode = DeliveryMode.COURIER;
        paymentMethod = PaymentMethod.CREDIT_CARD;
        order = new Order();
    }

    @Test
    public void testCreateOrder() {
        Cart cart = new Cart();

        assertNotNull(orderService.createOrder(cart));
    }

    @Test
    public void testPlaceOrder() {
        order.setProductsCost(BigDecimal.TEN);
        assertEquals(0, ArrayListOrderDao.getInstance().findOrders().size());
        orderService.placeOrder(order, contactDetails, deliveryMode, paymentMethod);
        assertEquals(1, ArrayListOrderDao.getInstance().findOrders().size());
    }
}
