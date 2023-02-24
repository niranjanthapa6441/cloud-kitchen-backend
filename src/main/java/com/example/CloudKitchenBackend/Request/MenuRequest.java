package com.example.CloudKitchenBackend.Request;

import com.example.CloudKitchenBackend.Model.Restaurant;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;


@Data
public class MenuRequest {

    @NotBlank(message = "Restaurant id should not be empty")
    private String restaurantId;

    @NotBlank(message = "Menu description should not be empty")
    private String Description;

    @NotBlank(message = "Opening time Should not be empty")
    private String openingTime;

    @NotBlank(message = "Closing time should not be empty")
    private String closingTime;

    @NotBlank(message = "Food Id should not be empty")
    private int foodId;
    @NotBlank(message = "Meal Id should not be empty")
    private int mealId;
    @NotBlank(message = "Category Id should not be empty")
    private int categoryId;
    @NotBlank(message = "Food Description should not be empty")
    private String foodDescription;

    @NotBlank(message = "Description should not be empty")
    private double price;

    @NotBlank(message = "Description should not be empty")
    private double discountPercentage;

    private String imagePath;

    private String image;
}
