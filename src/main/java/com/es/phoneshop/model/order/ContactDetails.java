package com.es.phoneshop.model.order;

import java.io.Serializable;

public class ContactDetails implements Serializable {
    private String firstName;
    private String lastName;
    private String phone;
    private String deliveryAddress;

    public ContactDetails() {
    }

    public ContactDetails(String firstName, String lastName, String phone, String deliveryAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.deliveryAddress = deliveryAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
