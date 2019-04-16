package com.es.phoneshop.model.order;

import com.es.phoneshop.exceptions.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {

    private static OrderDao instance;

    private List<Order> orders = new ArrayList<>();

    public static synchronized OrderDao getInstance() {
        if(instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    @Override
    public synchronized void save(Order order) {
        if (orders.stream()
                .anyMatch(o -> o.getSecureId().equals(order.getSecureId()))) {
            throw new IllegalArgumentException("Order with such ID already exists");
        } else {
            orders.add(order);
        }
    }

    @Override
    public synchronized Order getOrder(String secureId) {
        return orders.stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public synchronized List<Order> findOrders() {
        return orders;
    }

    @Override
    public synchronized void delete(String id) {
        orders.removeIf(order -> id.equals(order.getSecureId()));
    }
}
