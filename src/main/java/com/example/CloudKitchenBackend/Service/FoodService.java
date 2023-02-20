package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.FoodDTO;
import com.example.CloudKitchenBackend.DTO.FoodListDTO;
import com.example.CloudKitchenBackend.Model.Food;
import com.example.CloudKitchenBackend.Request.FoodRequest;
import org.springframework.stereotype.Service;

@Service
public interface FoodService {
    FoodDTO save(FoodRequest request);
    String delete(int id);

    FoodListDTO findAll(String name, int page, int size);

    Food findById(int id);

    FoodDTO update(FoodRequest request, int id);
}
