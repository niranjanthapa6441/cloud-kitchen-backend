package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantListDTO {
    private List<RestaurantDTO> restaurantDTOS;
    private int currentPage;

    private long totalElements;

    private int totalPages;
}
