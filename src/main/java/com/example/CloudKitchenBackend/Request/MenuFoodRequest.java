package com.example.CloudKitchenBackend.Request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class MenuFoodRequest {

    private int menuId;

    private int foodId;
    private int mealId;
    private int categoryId;
    @NotBlank(message = "Description should not be empty")
    private String Description;

    private double price;

    private double discountPercentage;

    private String imagePath;

    private String image;
}
