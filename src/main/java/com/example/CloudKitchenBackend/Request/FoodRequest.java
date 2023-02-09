package com.example.CloudKitchenBackend.Request;

import com.example.CloudKitchenBackend.Model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FoodRequest {

    @NotBlank(message = "Category Should Not Be Empty")
    private String category;
    @NotBlank(message = "Category Should Not Be Empty")
    private String name;
}
