package com.es.phoneshop.model.order;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArrayListOrderDaoTest {
    private OrderDao orderDao;
    private Order order;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        order = new Order();
        order.setSecureId("1");
    }

    @After
    public void complete() {
        orderDao.delete("1");
    }

    @Test
    public void testSave() {
        orderDao.save(order);
        assertEquals(1, orderDao.findOrders().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveSameOrders() {
        orderDao.save(order);
        orderDao.save(order);
    }

    @Test
    public void testGetOrder() {
        orderDao.save(order);
        assertNotNull(orderDao.getOrder("1"));
    }

    @Test(expected = OrderNotFoundException.class)
    public void testGetOrderWithException() {
        orderDao.getOrder("1");
    }
}
