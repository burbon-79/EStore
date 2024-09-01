package com.estore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CartDTO {
    @NotNull
    @Size(min = 2, max = 99, message = "The name must be from 2 to 99 characters.")
    private String fullName;

    @NotNull
    @Size(min = 2, max = 99, message = "The phone number must be from 2 to 99 characters.")
    private String phoneNumber;

    @NotNull
    @Email(message = "Email address entered incorrectly.")
    private String email;

    private String shipping;

    @NotNull
    @Size(min = 2, max = 99, message = "The city must be from 2 to 99 characters.")
    private String city;

    private String payment;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
