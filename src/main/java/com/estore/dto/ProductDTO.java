package com.estore.dto;

import jakarta.validation.constraints.*;

public class ProductDTO {
    @NotNull
    @Size(min = 2, max = 99, message = "The name must be from 2 to 99 characters.")
    private String name;

    @NotNull(message = "The price must be specified.")
    @DecimalMin(value = "0.00", message = "The price cannot be less than 0.")
    @DecimalMax(value = "99999999999999999.00", message = "Incorrectly entered price.")
    private String price;

    @Size(max = 999, message = "The description must be up to 999 characters.")
    private String description;

    @NotNull
    private int categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
