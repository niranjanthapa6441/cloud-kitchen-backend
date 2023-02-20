package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.MenuDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import com.example.CloudKitchenBackend.DTO.MenuListDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import com.example.CloudKitchenBackend.Service.MenuService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    EntityManager entityManager;

    @Override
    public MenuDTO save(MenuRequest request) {
        return null;
    }

    @Override
    public MenuDTO delete(int id) {
        return null;
    }

    @Override
    public MenuListDTO findAll(String name, int page, int size) {
        return null;
    }

    @Override
    public MenuDTO findById(int id) {
        return null;
    }

    @Override
    public MenuDTO update(MenuRequest request, int id) {
        return null;
    }

    public MenuFoodListDTO searchMenuFoods(String foodName, String restaurantName, String category, String mealName, double rating, String sortBy, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MenuFood> query = cb.createQuery(MenuFood.class);
        Root<MenuFood> menuFoodRoot = query.from(MenuFood.class);
        Join<MenuFood, Food> foodJoin = menuFoodRoot.join("food");
        Join<MenuFood, Menu> menuJoin = menuFoodRoot.join("menu");
        Join<Menu, Restaurant> restaurantJoin = menuJoin.join("restaurant");
        Join<MenuFood, Category> categoryJoin = menuFoodRoot.join("category");
        Join<MenuFood, Meal> mealJoin = menuFoodRoot.join("meal", JoinType.LEFT);
        query.select(menuFoodRoot);
        List<Predicate> predicates = new ArrayList<>();
        if (foodName != null && !foodName.isEmpty()) {
            predicates.add(cb.like(cb.lower(foodJoin.get("name")), "%" + foodName.toLowerCase() + "%"));
        }
        if (restaurantName != null && !restaurantName.isEmpty()) {
            predicates.add(cb.equal(cb.lower(restaurantJoin.get("name")), restaurantName.toLowerCase()));
        }
        if (category != null && !category.isEmpty()) {
            predicates.add(cb.equal(cb.lower(categoryJoin.get("category")), category.toLowerCase()));
        }
        if (mealName != null && !mealName.isEmpty()) {
            predicates.add(cb.equal(cb.lower(mealJoin.get("meal")),mealName.toLowerCase()));
        }
        if (rating != 0.0) {
            predicates.add(cb.greaterThanOrEqualTo(menuFoodRoot.get("rating"), rating));
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            if (sortBy.equals("price")) {
                query.orderBy(cb.asc(menuFoodRoot.get("price")));
            } else if (sortBy.equals("-price")) {
                query.orderBy(cb.desc(menuFoodRoot.get("price")));
            }
        }

        List<Order> orderList= new ArrayList<>();
        orderList.add(cb.asc(foodJoin.get("name")));
        query.orderBy(orderList);
        Predicate[] predicateArr = new Predicate[predicates.size()];
        Predicate predicate = cb.and(predicates.toArray(predicateArr));
        query = query.where(predicate);
        List<MenuFood> foods = entityManager.createQuery(query).getResultList();
        TypedQuery<MenuFood> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        int currentPage= page-1;
        List<MenuFood> menuFoods = typedQuery.getResultList();
        int totalElements = foods.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return toMenuFoodListDTO(toMenuFoodDTO(menuFoods),currentPage,totalPages,totalElements);
    }

    private MenuFoodListDTO toMenuFoodListDTO(List<MenuFoodDTO> menuFoods, int currentPage, int totalPages, long totalElements) {
        return MenuFoodListDTO.builder()
                .menuFoods(menuFoods)
                .currentPage(currentPage)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    private List<MenuFoodDTO> toMenuFoodDTO(List<MenuFood> menuFoods) {
        List<MenuFoodDTO> menuFoodDTOS = new ArrayList<>();
        for (MenuFood menuFood : menuFoods) {
            MenuFoodDTO menuFoodDTO = MenuFoodDTO.builder()
                    .name(menuFood.getFood().getName())
                    .description(menuFood.getDescription())
                    .restaurantName(menuFood.getMenu().getRestaurant().getName())
                    .category(menuFood.getCategory().getCategory())
                    .Meal(menuFood.getMeal().getMeal())
                    .price(menuFood.getPrice())
                    .rating(menuFood.getRating())
                    .build();
            menuFoodDTOS.add(menuFoodDTO);
        }
        return menuFoodDTOS;
    }
}
