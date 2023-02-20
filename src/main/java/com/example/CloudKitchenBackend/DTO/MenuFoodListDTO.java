package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class MenuFoodListDTO {
    List<MenuFoodDTO> menuFoods;

    private int currentPage;

    private long totalElements;
    private int totalPages;
}
