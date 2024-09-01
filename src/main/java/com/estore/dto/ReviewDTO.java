package com.estore.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewDTO {
    @NotNull
    @Size(min = 2, max = 99, message = "The name must be from 2 to 99 characters.")
    private String username;

    @NotNull
    @Min(value = 1, message = "The number of stars must be at least 1.")
    @Max(value = 5, message = "The number of stars must not exceed 5.")
    private int stars;

    @Size(max = 999, message = "The text must be up to 999 characters.")
    private String text;

    private int referenceId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }
}
