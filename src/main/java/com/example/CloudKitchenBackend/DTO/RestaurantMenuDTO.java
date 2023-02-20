package com.example.CloudKitchenBackend.DTO;

import com.example.CloudKitchenBackend.Model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantMenuDTO {
    List<RestaurantMenu> menus;
    List<Category> categories;
    private int currentPage;

    private long totalElements;

    private int totalPages;
}
