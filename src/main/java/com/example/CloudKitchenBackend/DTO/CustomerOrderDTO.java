package com.example.CloudKitchenBackend.DTO;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class CustomerOrderDTO {
    private String orderId;
    private double totalPrice;

    private int totalItems;
    private LocalDate orderDate;

    private LocalTime orderTime;

    private String status;

    List<OrderFoodDTO> orderFoods;
}
