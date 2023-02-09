package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.FoodDTO;
import com.example.CloudKitchenBackend.DTO.FoodListDTO;
import com.example.CloudKitchenBackend.DTO.MealDTO;
import com.example.CloudKitchenBackend.Model.Food;
import com.example.CloudKitchenBackend.Model.Meal;
import com.example.CloudKitchenBackend.Request.FoodRequest;
import com.example.CloudKitchenBackend.Request.MealRequest;
import org.springframework.stereotype.Service;

@Service
public interface MealService {
    Meal save(MealRequest request);
    Meal delete(int id);

    MealDTO findAll(String name, int page, int size);

    Meal findById(int id);

    Meal update(MealRequest request, int id);
}
