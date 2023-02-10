package com.example.CloudKitchenBackend.DTO;

import lombok.Data;

@Data
public class RestaurantMenu {
    private String name;
    private String description;
    private String rating;
    private String deliveryTime;
    private String category;
    private String Meal;
}
