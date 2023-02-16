package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import com.example.CloudKitchenBackend.Model.MenuFood;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import org.springframework.stereotype.Service;

@Service
public interface MenuFoodService {
    MenuFoodDTO save(MenuFoodRequest request);
    MenuFoodDTO delete(int id);
    MenuFoodDTO findById(int id);

    MenuFoodDTO update(MenuFoodRequest request, int id);
}
