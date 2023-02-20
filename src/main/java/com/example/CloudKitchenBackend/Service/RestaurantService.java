package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.RestaurantDTO;
import com.example.CloudKitchenBackend.DTO.RestaurantListDTO;
import com.example.CloudKitchenBackend.Request.RestaurantRequest;
import org.springframework.stereotype.Service;

@Service
public interface RestaurantService {
    RestaurantDTO save(RestaurantRequest request);
    String delete(String id);

    RestaurantListDTO findAll(String restaurantName, double rating, String latitude, String longitude, int page, int size);

    RestaurantDTO findById(int id);

    RestaurantDTO update(RestaurantRequest request, String id);


}
