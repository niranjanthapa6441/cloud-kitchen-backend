package com.example.CloudKitchenBackend.DTO;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class OrderDetailsDTO {
    private CustomerOrderDTO orderDTO;
    List<OrderFoodDTO> orderFoods;
}
