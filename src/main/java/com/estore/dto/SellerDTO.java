package com.estore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SellerDTO {
    @NotNull
    @Email(message = "Email address entered incorrectly.")
    private String email;

    @NotNull
    @Size(min = 5, max = 16, message = "The password must be from 5 to 16 characters.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
