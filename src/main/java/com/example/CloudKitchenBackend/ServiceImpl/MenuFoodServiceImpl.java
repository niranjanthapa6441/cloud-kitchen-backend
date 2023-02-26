package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Model.Menu;
import com.example.CloudKitchenBackend.Repositories.*;
import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Service.MenuFoodService;
import com.example.CloudKitchenBackend.Util.CustomException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
        Menu menu = getMenu(request);
        validate(multipartFile);
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (fileName.contains(".php%00.")){
            throw new CustomException(CustomException.Type.INVALID_FILE_EXTENSION);
        }
        request.setImage(fileName);
        request.setImagePath(getImagePath(multipartFile, menu, fileName));
        Meal meal= findMeal(request);
        Category category = getCategory(request);
        Food food = getFood(request);
        validateMenuFood(category,menu,meal,food);
        MenuFood menuFood= menuFoodRepo.save(toMenuFood(request,meal,food,category,menu));
        return "Saved Successfully";
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
        menuFood.setPrice(request.getPrice());
        menuFood.setDescription(request.getDescription());
        menuFood.setDiscountPercentage(request.getDiscountPercentage());
        menuFood.setImagePath(request.getImagePath());
        menuFood.setImage(request.getImage());
        menuFood.setRating(0.0);
        return menuFood;
    }

    private void validateMenuFood(Category category, Menu menu, Meal meal, Food food) {
    }
    private void validate(MultipartFile multipartFile) {
        if (multipartFile.getSize()> 3000000)
            throw new CustomException(CustomException.Type.INVALID_FILE_SIZE);
        String extension= FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if(!checkFileExtension(extension))
            throw new CustomException(CustomException.Type.INVALID_FILE_EXTENSION);
        checkMimeType(multipartFile);
        if (!checkMimeType(multipartFile))
            throw new CustomException(CustomException.Type.INVALID_MIME_TYPE);
    }

    private boolean checkFileExtension(String extension) {
        return (extension != null && (extension.equals("png") || extension.equals("jpeg")||extension.equals("jpg")));
    }

    private boolean checkMimeType(MultipartFile multipartFile){
        Tika tika = new Tika();
        String mimeType;
        try {
            mimeType= tika.detect(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (mimeType.equals("image/png") || mimeType.equals("image/jpg") || mimeType.equals("image/jpeg"));

    }
    private Meal findMeal(MenuFoodRequest request) {
        Meal meal= new Meal();
        Optional<Meal> findMeal= mealRepo.findById(request.getMealId());
        if (findMeal.isPresent())
            meal=findMeal.get();
        return meal;
    }
    private String getImagePath(MultipartFile multipartFile, Menu menu, String fileName) {
        String uploadDirectory= "./images/restaurants/"+ menu.getRestaurant().getName().replaceAll("\\s","");
        Path path = Paths.get(uploadDirectory);
        Path filePath= path.resolve(fileName);
        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (InputStream inputStream= multipartFile.getInputStream()){
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath.toString();
    }

    private Menu getMenu(MenuFoodRequest request) {
        Optional<Menu> findMenu= menuRepo.findById(request.getMenuId());
        Menu menu= new Menu();
        if(findMenu.isPresent())
            menu=findMenu.get();
        return menu;
    }

    private Food getFood(MenuFoodRequest request) {
        Food food= new Food();
        Optional<Food> findFood= foodRepo.findById(request.getFoodId());
        if (findFood.isPresent())
            food=findFood.get();
        return food;
    }

    private Category getCategory(MenuFoodRequest request) {
        Category category= new Category();
        Optional<Category> findCategory= categoryRepo.findById(request.getCategoryId());
        if (findCategory.isPresent())
            category= findCategory.get();
        return category;
    }
}
