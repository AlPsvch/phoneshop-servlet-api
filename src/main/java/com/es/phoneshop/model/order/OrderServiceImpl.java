package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private static volatile OrderService instance;

    public static OrderService getInstance() {
        OrderService localInstance = instance;
        if (localInstance == null) {
            synchronized (OrderServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new OrderServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();

        order.setCartItems(cart.getCartItems().stream().map(CartItem::new).collect(Collectors.toList()));
        order.setProductsCost(cart.getTotalPrice());

        return order;
    }

    @Override
    public void placeOrder(Order order, ContactDetails contactDetails, DeliveryMode deliveryMode, PaymentMethod paymentMethod) {
        order.setSecureId(generateUniqueId());
        order.setContactDetails(contactDetails);
        order.setDeliveryMode(deliveryMode);
        order.setDeliveryCost(deliveryMode.getDeliveryPrice());
        order.setPaymentMethod(paymentMethod);
        calculateTotalOrderCost(order);

        ArrayListOrderDao.getInstance().save(order);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private void calculateTotalOrderCost(Order order) {
        BigDecimal totalOrderCost = order.getProductsCost().add(order.getDeliveryCost());
        order.setTotalOrderCost(totalOrderCost);
    }
}
