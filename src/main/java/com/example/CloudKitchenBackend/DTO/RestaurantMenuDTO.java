package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantMenuDTO {
    List<RestaurantMenu> menus;
    private int currentPage;

    private long totalElements;

    private int totalPages;
}
