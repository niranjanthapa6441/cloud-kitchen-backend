package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.*;
import com.example.CloudKitchenBackend.Model.Menu;
import com.example.CloudKitchenBackend.Model.MenuFood;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MenuService {
    String save(MenuRequest request);
    /*MenuDTO delete(int id);

    MenuListDTO findAll(String name, int page, int size);

    MenuDTO findById(int id);*/

    MenuDTO update(MenuRequest request, int id);
    MenuFoodListDTO searchMenuFoods(String foodName, String restaurantName, String categoryName, String mealName, double rating, String sortBy, int page, int size);
    RestaurantMenuDTO restaurantMenu(String foodName, String restaurantName, String categoryName, String mealName, double rating, String sortBy, int page, int size);

}
