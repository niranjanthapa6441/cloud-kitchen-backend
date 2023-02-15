package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.FoodMenuDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Service.MenuService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    EntityManager entityManager;
    public MenuFoodListDTO searchMenuFoods(String foodName, String restaurantName, String categoryName, String mealName, Double rating, String sortBy, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MenuFood> query = cb.createQuery(MenuFood.class);
        Root<MenuFood> menuFoodRoot = query.from(MenuFood.class);
        Join<MenuFood, Food> foodJoin = menuFoodRoot.join("food");
        Join<MenuFood, Menu> menuJoin = menuFoodRoot.join("menu");
        Join<Menu, Restaurant> restaurantJoin = menuJoin.join("restaurant");
        Join<Food, Category> categoryJoin = foodJoin.join("category");
        Join<MenuFood, Meal> mealJoin = menuFoodRoot.join("meal", JoinType.LEFT);
        Join<MenuFood, FoodFeedback> feedbackJoin = menuFoodRoot.join("feedbacks", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if (foodName != null && !foodName.isEmpty()) {
            predicates.add(cb.like(cb.lower(foodJoin.get("name")), "%" + foodName.toLowerCase() + "%"));
        }
        if (restaurantName != null && !restaurantName.isEmpty()) {
            predicates.add(cb.equal(cb.lower(restaurantJoin.get("name")), restaurantName.toLowerCase()));
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            predicates.add(cb.equal(cb.lower(categoryJoin.get("category")), categoryName.toLowerCase()));
        }
        if (mealName != null && !mealName.isEmpty()) {
            predicates.add(cb.equal(cb.lower(mealJoin.get("meal")),mealName.toLowerCase()));
        }
        if (rating != null) {
            predicates.add(cb.equal(cb.avg(feedbackJoin.get("rating")), rating));
        }
        query.where(predicates.toArray(new Predicate[0]));

        if (sortBy != null && !sortBy.isEmpty()) {
            if (sortBy.equals("price")) {
                query.orderBy(cb.asc(menuFoodRoot.get("price")));
            } else if (sortBy.equals("-price")) {
                query.orderBy(cb.desc(menuFoodRoot.get("price")));
            }
        }

        TypedQuery<MenuFood> typedQuery = entityManager.createQuery(query);
        List<MenuFood> menuFoods = typedQuery.getResultList();
        Pageable pageable = PageRequest.of(page, size);
        List<FoodMenuDTO> foodMenuDTOS=toMenuFoodDTO(menuFoods);
        Page<FoodMenuDTO> resultPage = null;
        if (foodMenuDTOS.size() > 0) {
            int from = page * size;
            int to = from + size;
            if (foodMenuDTOS.size() < to) {
                to = foodMenuDTOS.size();
            }
            resultPage = new PageImpl<>(foodMenuDTOS.subList(from, to), pageable, foodMenuDTOS.size()); // list is sliced according to page number and size
        } else
            resultPage = new PageImpl<>(new ArrayList<>(), pageable, 0);
        return toMenuFoodListDTO(resultPage,resultPage.getNumber(),resultPage.getTotalPages(),resultPage.getTotalElements());
    }

    private MenuFoodListDTO toMenuFoodListDTO(Page<FoodMenuDTO> resultPage, int number, int totalPages, long totalElements) {
        return MenuFoodListDTO.builder()
                .menuFoods(resultPage)
                .currentPage(number)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    private List<FoodMenuDTO> toMenuFoodDTO(List<MenuFood> menuFoods) {
        List<FoodMenuDTO> foodMenuDTOs = new ArrayList<>();
        for (MenuFood menuFood : menuFoods) {
            FoodMenuDTO foodMenuDTO = FoodMenuDTO.builder()
                    .name(menuFood.getFood().getName())
                    .description(menuFood.getDescription())
                    .restaurantName(menuFood.getMenu().getRestaurant().getName())
                    .category(menuFood.getFood().getCategory().getCategory())
                    .Meal(menuFood.getMeal().getMeal())
                    .build();
            foodMenuDTOs.add(foodMenuDTO);
        }
        return foodMenuDTOs;
    }
}
