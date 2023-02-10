package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.FoodMenuDTO;
import com.example.CloudKitchenBackend.Model.MenuFood;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Service.MenuFoodService;
import org.springframework.stereotype.Service;

@Service
public class MenuFoodServiceImpl implements MenuFoodService {


    @Override
    public MenuFood save(MenuFoodRequest request) {
        return null;
    }

    @Override
    public MenuFood delete(int id) {
        return null;
    }

    @Override
    public FoodMenuDTO findAll(String name, int page, int size) {
        return null;
    }

    @Override
    public MenuFood findById(int id) {
        return null;
    }

    @Override
    public MenuFood update(MenuFoodRequest request, int id) {
        return null;
    }
}
