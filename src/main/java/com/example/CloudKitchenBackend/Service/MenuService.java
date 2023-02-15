package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {
    public MenuFoodListDTO searchMenuFoods(String foodName, String restaurantName, String categoryName, String mealName, Double rating, String sortBy, int page, int size);

}
