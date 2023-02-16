package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class MenuFoodListDTO {
    Page<MenuFoodDTO> menuFoods;

    private int currentPage;

    private long totalElements;

    private int totalPages;
}
