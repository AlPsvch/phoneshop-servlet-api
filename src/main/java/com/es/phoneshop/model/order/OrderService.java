package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

public interface OrderService {

    Order createOrder(Cart cart);

    void placeOrder(Order order, ContactDetails contactDetails, DeliveryMode deliveryMode, PaymentMethod paymentMethod);
}
