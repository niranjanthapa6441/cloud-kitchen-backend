package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Model.Menu;
import com.example.CloudKitchenBackend.Repositories.*;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import com.example.CloudKitchenBackend.Service.MenuFoodService;
import com.example.CloudKitchenBackend.Util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class MenuFoodServiceImpl implements MenuFoodService {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private MenuFoodRepo menuFoodRepo;

    @Autowired
    private MealRepo mealRepo;

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;
    @Override
    public String save(MenuFoodRequest request, MultipartFile multipartFile) {
        Optional<Menu> findMenu= menuRepo.findById(request.getMenuId());
        Menu menu= new Menu();
        if(findMenu.isPresent())
            menu=findMenu.get();
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        request.setImage(fileName);
        String uploadDirectory= "./restaurants/"+menu.getRestaurant().getName()+"/"+fileName;
        Path path = Paths.get(uploadDirectory);
        if(!Files.exists(path)){
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (InputStream inputStream= multipartFile.getInputStream()){
            Path filePath= path.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            request.setImagePath(filePath.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Meal meal= findMeal(request);
        Category category= new Category();
        Optional<Category> findCategory= categoryRepo.findById(request.getCategoryId());
        if (findCategory.isPresent())
            category= findCategory.get();
        Food food= new Food();
        Optional<Food> findFood= foodRepo.findById(request.getFoodId());
        if (findFood.isPresent())
            food=findFood.get();
        validateMenuFood(category,menu,meal,food);
        MenuFood menuFood= menuFoodRepo.save(toMenuFood(request,meal,food,category,menu));
        return "Saved SuccessFully";
    }

    @Override
    public MenuFoodDTO delete(String id) {
        return null;
    }

    @Override
    public MenuFoodDTO findById(String id) {
        return null;
    }

    @Override
    public MenuFoodDTO update(MenuFoodRequest request, String id) {
        return null;
    }
    private MenuFood toMenuFood(MenuFoodRequest request, Meal meal, Food food, Category category, Menu menu) {
        MenuFood menuFood= new MenuFood();
        menuFood.setMenu(menu);
        menuFood.setMeal(meal);
        menuFood.setFood(food);
        menuFood.setCategory(category);
        menuFood.setDescription(request.getDescription());
        menuFood.setDiscountPercentage(request.getDiscountPercentage());
        menuFood.setImagePath(request.getImagePath());
        menuFood.setImage(request.getImage());
        menuFood.setRating(0.0);
        return menuFood;
    }

    private void validateMenuFood(Category category, Menu menu, Meal meal, Food food) {
    }

    private Meal findMeal(MenuFoodRequest request) {
        Meal meal= new Meal();
        Optional<Meal> findMeal= mealRepo.findById(request.getMealId());
        if (findMeal.isPresent())
            meal=findMeal.get();
        return meal;
    }
}
