package com.es.phoneshop.model.order;

import java.util.List;

public interface OrderDao {

    void save(Order order);

    Order getOrder(String secureId);

    List<Order> findOrders();

    void delete(String id);
}
