package com.example.CloudKitchenBackend.DTO;

import lombok.Data;

@Data
public class OrderFoodDTO {
    private String name;

    private int quantity;

    private double unitPrice;

    private double price;
}
