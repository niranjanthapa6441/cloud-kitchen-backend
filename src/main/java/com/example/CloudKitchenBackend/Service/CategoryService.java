package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.CategoryDTO;
import com.example.CloudKitchenBackend.Model.Category;
import com.example.CloudKitchenBackend.Request.CategoryRequest;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
   Category save(CategoryRequest request);
   String delete(int id);


   CategoryDTO findAll(String category, int page, int size);

    Category findById(int id);

    String update(CategoryRequest request, int id);
}
