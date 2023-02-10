package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodMenuDTO {
    private String name;
    private String description;

    private String rating;

    private String deliveryTime;

    private String restaurantName;

    private String restaurantAddress;

    private String category;

    private String Meal;
}
