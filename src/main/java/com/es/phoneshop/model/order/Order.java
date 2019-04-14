package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;

public class Order extends Cart {
    private String secureId;
    private ContactDetails contactDetails;
    private DeliveryMode deliveryMode;
    private PaymentMethod paymentMethod;
    private BigDecimal productsCost;
    private BigDecimal deliveryCost;
    private BigDecimal totalOrderCost;

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getProductsCost() {
        return productsCost;
    }

    public void setProductsCost(BigDecimal productsCost) {
        this.productsCost = productsCost;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getTotalOrderCost() {
        return totalOrderCost;
    }

    public void setTotalOrderCost(BigDecimal totalOrderCost) {
        this.totalOrderCost = totalOrderCost;
    }
}
