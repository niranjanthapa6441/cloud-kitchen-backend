package com.example.CloudKitchenBackend.DTO;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class CustomerOrderDTO {
    private double totalPrice;

    private int totalItems;
    private LocalDate orderDate;

    private LocalTime orderTime;

    private String status;
}
