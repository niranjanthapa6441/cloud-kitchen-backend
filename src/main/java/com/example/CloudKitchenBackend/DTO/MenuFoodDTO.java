package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuFoodDTO {
    private String name;
    private String description;

    private double rating;

    private String deliveryTime;

    private String restaurantName;

    private String restaurantAddress;

    private String category;

    private String Meal;

    private double price;

    private double discountPrice;
}
