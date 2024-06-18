package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.*;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {
    String save(MenuRequest request);
    String delete(int id);

    MenuDTO findById(int id);

    MenuDTO update(MenuRequest request, int id);
    MenuFoodListDTO searchMenuFoods(String foodName, String restaurantName, String categoryName, String mealName, double rating, String sortBy, int page, int size);
    RestaurantMenuDTO restaurantMenu(String foodName, String restaurantId, String categoryName, String mealName, double rating, String sortBy, int page, int size);
}
