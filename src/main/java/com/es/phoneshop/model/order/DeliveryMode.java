package com.es.phoneshop.model.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public enum DeliveryMode {
    COURIER("Courier", BigDecimal.valueOf(10)), PICKUP("Pickup", BigDecimal.ZERO);

    private String description;
    private BigDecimal deliveryPrice;

    DeliveryMode(String description, BigDecimal deliveryPrice) {
        this.description = description;
        this.deliveryPrice = deliveryPrice;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public static List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    public static DeliveryMode getDeliveryMode(String description) {
        return getDeliveryModes().stream()
                .filter(deliveryMode -> description.equals(deliveryMode.getDescription()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
