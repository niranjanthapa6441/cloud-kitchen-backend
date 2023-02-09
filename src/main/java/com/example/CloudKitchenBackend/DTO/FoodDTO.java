package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodDTO {
    private String category;

    private String name;
}
