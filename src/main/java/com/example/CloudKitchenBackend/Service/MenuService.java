package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.MenuDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import com.example.CloudKitchenBackend.DTO.MenuListDTO;
import com.example.CloudKitchenBackend.Model.Menu;
import com.example.CloudKitchenBackend.Model.MenuFood;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {
    MenuFoodDTO save(MenuRequest request);
    /*MenuDTO delete(int id);

    MenuListDTO findAll(String name, int page, int size);

    MenuDTO findById(int id);

    MenuDTO update(MenuRequest request, int id)*/;
    MenuFoodListDTO searchMenuFoods(String foodName, String restaurantName, String categoryName, String mealName, double rating, String sortBy, int page, int size);

}
