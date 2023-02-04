package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.Model.Category;
import com.example.CloudKitchenBackend.Repositories.CategoryRepo;
import com.example.CloudKitchenBackend.Request.CategoryRequest;
import com.example.CloudKitchenBackend.Service.CategoryService;
import com.example.CloudKitchenBackend.Util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo repo;
    @Override
    public String save(CategoryRequest request) {
        checkValidation(request);
        repo.save(toCategory(request));
        return "Category Saved Successfully";
    }

    @Override
    public String delete(int id) {
        repo.deleteById(id);
        return "Deleted Successfully";
    }

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category findById(int id) {
        Optional<Category> category= repo.findById(id);
        if (category.isEmpty()){
            throw new NullPointerException("Category Not Found");
        }
        return category.get();
    }

    @Override
    public String update(CategoryRequest request, int id) {
        Category findCategory=findById(id);
        checkValidation(request);
        Category category=toCategory(request);
        Category updateCategory=category;
        updateCategory.setId(findCategory.getId());
        repo.save(updateCategory);
        return "Category has been updated";
    }
    private Category toCategory(CategoryRequest request) {
        Category category= new Category();
        category.setCategory(request.getCategory());
        category.setDescription(request.getDescription());
        return category;
    }
    private void checkValidation(CategoryRequest request) {
        Optional<Category> category= repo.findByCategory(request.getCategory());
        if (category.isPresent()){
            throw new CustomException(CustomException.Type.CATEGORY_ALREADY_EXIST);
        }
    }
}
