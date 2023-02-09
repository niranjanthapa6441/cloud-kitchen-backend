package com.example.CloudKitchenBackend.Request;

import com.example.CloudKitchenBackend.Model.Food;
import com.example.CloudKitchenBackend.Model.Meal;
import com.example.CloudKitchenBackend.Model.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class MenuFoodRequest {
    private int menuId;

    private int foodId;

    private int mealId;

    private String Description;

    private double price;
}
