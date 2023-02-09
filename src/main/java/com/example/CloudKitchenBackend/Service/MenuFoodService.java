package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.Model.MenuFood;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import org.springframework.stereotype.Service;

@Service
public interface MenuFoodService {
    MenuFood save(MenuFoodRequest request);
    MenuFood delete(int id);

    MenuFoodDTO findAll(String name, int page, int size);

    MenuFood findById(int id);

    MenuFood update(MenuFoodRequest request, int id);
}
