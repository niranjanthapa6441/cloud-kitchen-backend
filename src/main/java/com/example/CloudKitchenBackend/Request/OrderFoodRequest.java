package com.example.CloudKitchenBackend.Request;

import lombok.Data;

@Data
public class OrderFoodRequest {
    private String menuFoodId;

    private int quantity;
}
