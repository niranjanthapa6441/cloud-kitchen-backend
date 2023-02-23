package com.example.CloudKitchenBackend.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class FoodRequest {

    @NotBlank(message = "Category Should Not Be Empty")
    private String category;
    @NotBlank(message = "Category Should Not Be Empty")
    private String name;
}
