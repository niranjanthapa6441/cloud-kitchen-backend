package com.example.CloudKitchenBackend.Request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class MenuFoodRequest {

    @NotBlank(message = "Menu Id should not be empty")
    private int menuId;

    @NotBlank(message = "Food Id should not be empty")
    private int foodId;
    @NotBlank(message = "Meal Id should not be empty")
    private int mealId;
    @NotBlank(message = "Category Id should not be empty")
    private int categoryId;
    @NotBlank(message = "Description should not be empty")
    private String Description;

    @NotBlank(message = "Description should not be empty")
    private double price;

    @NotBlank(message = "Description should not be empty")
    private double discountPercentage;
}
