package com.example.CloudKitchenBackend.Request;

import lombok.Data;

@Data
public class OrderFoodRequest {
    private int menuId;
    private int foodId;

    private String foodName;

    private int quantity;

    private double price;
}
