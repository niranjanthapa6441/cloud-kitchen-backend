package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import com.example.CloudKitchenBackend.Model.MenuFood;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import org.springframework.stereotype.Service;

@Service
public interface MenuFoodService {
    MenuFoodDTO save(MenuFoodRequest request);
    MenuFoodDTO delete(String id);
    MenuFoodDTO findById(String id);

    MenuFoodDTO update(MenuFoodRequest request, String id);
}
