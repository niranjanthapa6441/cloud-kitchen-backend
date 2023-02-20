package com.example.CloudKitchenBackend.Request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FoodRequest {

    @NotBlank(message = "Category Should Not Be Empty")
    private String category;
    @NotBlank(message = "Category Should Not Be Empty")
    private String name;
}
