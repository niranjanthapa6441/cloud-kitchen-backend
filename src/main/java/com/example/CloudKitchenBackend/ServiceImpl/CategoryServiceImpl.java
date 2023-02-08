package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.CategoryDTO;
import com.example.CloudKitchenBackend.Model.Category;
import com.example.CloudKitchenBackend.Repositories.CategoryRepo;
import com.example.CloudKitchenBackend.Request.CategoryRequest;
import com.example.CloudKitchenBackend.Service.CategoryService;
import com.example.CloudKitchenBackend.Util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public CategoryDTO findAll(String category, int page, int size) {
        List<Category> categories = new ArrayList<>();
        Pageable paging= PageRequest.of(page, size);
        Page<Category> pageCategories;
        if (category == null)
            pageCategories= repo.findAll(paging);
        else
            pageCategories= repo.findByCategory(category,paging);
        categories = pageCategories.getContent();
        CategoryDTO categoryDTO = toCategoryDTO(categories,pageCategories.getNumber(),pageCategories.getTotalElements(),pageCategories.getTotalPages());
        return categoryDTO;
    }

    private CategoryDTO toCategoryDTO(List<Category> categories, int number, long totalElements, int totalPages) {
        return CategoryDTO.builder().
                categories(categories).
                currentPage(number).
                totalElements(totalElements).
                totalPages(totalPages).build();
    }


    @Override
    public Category findById(int id) {
        Optional<Category> category= repo.findById(id);
        if (!category.isPresent()){
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
