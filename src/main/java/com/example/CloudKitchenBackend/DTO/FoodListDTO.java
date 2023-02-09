package com.example.CloudKitchenBackend.DTO;

import com.example.CloudKitchenBackend.Model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FoodListDTO {
    private List<FoodDTO> foodList;
    private int currentPage;

    private long totalElements;

    private int totalPages;
}
