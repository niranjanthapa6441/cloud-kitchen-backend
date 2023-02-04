package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.Model.Category;
import com.example.CloudKitchenBackend.Request.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    String  save(CategoryRequest request);
    String delete(int id);

    List<Category> findAll();

    Category findById(int id);

    String update(CategoryRequest request, int id);
}
