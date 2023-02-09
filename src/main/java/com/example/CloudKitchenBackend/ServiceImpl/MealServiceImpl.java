package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.MealDTO;
import com.example.CloudKitchenBackend.Model.Meal;
import com.example.CloudKitchenBackend.Request.MealRequest;
import com.example.CloudKitchenBackend.Service.MealService;
import org.springframework.stereotype.Service;

@Service
public class MealServiceImpl implements MealService {
    @Override
    public Meal save(MealRequest request) {
        return null;
    }

    @Override
    public Meal delete(int id) {
        return null;
    }

    @Override
    public MealDTO findAll(String name, int page, int size) {
        return null;
    }

    @Override
    public Meal findById(int id) {
        return null;
    }

    @Override
    public Meal update(MealRequest request, int id) {
        return null;
    }
}
