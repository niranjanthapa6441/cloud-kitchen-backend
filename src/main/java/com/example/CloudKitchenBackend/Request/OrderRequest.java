package com.example.CloudKitchenBackend.Request;

import com.example.CloudKitchenBackend.Model.MenuFood;
import jakarta.persistence.Column;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class OrderRequest {
    @NotBlank(message = "Username should not be empty")
    private String username;

    List<OrderFoodRequest> foods;
    private int totalItems;

    private double totalPrice;

    @NotBlank(message="Payment Partner should Not be empty")
    private String paymentPartner;
    @NotBlank(message="Payment method should Not be empty")
    private String paymentMethod;

    @NotBlank(message="Payment Status should Not be empty")
    private String paymentStatus;
}
