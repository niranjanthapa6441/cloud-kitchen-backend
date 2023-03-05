package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.MenuDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodDTO;
import com.example.CloudKitchenBackend.DTO.MenuFoodListDTO;
import com.example.CloudKitchenBackend.DTO.RestaurantMenuDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Repositories.*;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import com.example.CloudKitchenBackend.Service.MenuService;
import com.example.CloudKitchenBackend.Util.Formatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

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
    @Autowired
    private EntityManager entityManager;

    @Override
    public String save(MenuRequest request) {
        validate(request);
        Optional<Restaurant> findRestaurant = restaurantRepo.findById(request.getRestaurantId());
        Restaurant restaurant = new Restaurant();
        if (findRestaurant.isPresent())
            restaurant = findRestaurant.get();
        menuRepo.save(toMenu(request, restaurant));
        return "Saved Successfully";
    }

    @Override
    public MenuDTO update(MenuRequest request, int id) {
        return null;
    }

    private Menu toMenu(MenuRequest request, Restaurant restaurant) {
        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        menu.setDescription(request.getDescription());
        menu.setClosingTime(Formatter.getTimeFromString(request.getClosingTime()));
        menu.setOpeningTime(Formatter.getTimeFromString(request.getOpeningTime()));
        return menu;
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
            predicates.add(cb.like(cb.lower(restaurantJoin.get("name")), "%" + restaurantName.toLowerCase() + "%"));
        }
        if (category != null && !category.isEmpty()) {
            predicates.add(cb.equal(cb.lower(categoryJoin.get("category")), category.toLowerCase()));
        }
        if (mealName != null && !mealName.isEmpty()) {
            predicates.add(cb.equal(cb.lower(mealJoin.get("meal")), mealName.toLowerCase()));
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

        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(foodJoin.get("name")));
        query.orderBy(orderList);
        Predicate[] predicateArr = new Predicate[predicates.size()];
        Predicate predicate = cb.and(predicates.toArray(predicateArr));
        query = query.where(predicate);
        List<MenuFood> foods = entityManager.createQuery(query).getResultList();
        TypedQuery<MenuFood> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        int currentPage = page - 1;
        List<MenuFood> menuFoods = typedQuery.getResultList();
        int totalElements = foods.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<MenuFoodDTO> menuFoodDTOS = getFoodDTOList(menuFoods);
        return toMenuFoodListDTO(menuFoodDTOS, currentPage, totalPages, totalElements);
    }

    @Override
    public RestaurantMenuDTO restaurantMenu(String foodName, String restaurantId, String category, String mealName, double rating, String sortBy, int page, int size) {
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
        if (restaurantId != null && !restaurantId.isEmpty()) {
            predicates.add(cb.equal(restaurantJoin.get("id"),restaurantId));
        }
        if (category != null && !category.isEmpty()) {
            predicates.add(cb.equal(cb.lower(categoryJoin.get("category")), category.toLowerCase()));
        }
        if (mealName != null && !mealName.isEmpty()) {
            predicates.add(cb.equal(cb.lower(mealJoin.get("meal")), mealName.toLowerCase()));
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

        List<Order> orderList = new ArrayList<>();
        orderList.add(cb.asc(foodJoin.get("name")));
        query.orderBy(orderList);
        Predicate[] predicateArr = new Predicate[predicates.size()];
        Predicate predicate = cb.and(predicates.toArray(predicateArr));
        query = query.where(predicate);
        List<MenuFood> foods = entityManager.createQuery(query).getResultList();
        TypedQuery<MenuFood> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        int currentPage = page - 1;
        List<MenuFood> menuFoods = typedQuery.getResultList();
        int totalElements = foods.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<MenuFoodDTO> menuFoodDTOS = getFoodDTOList(menuFoods);
        List<String> categories= menuFoodRepo.findDistinctCategoriesByRestaurantId(restaurantId);
        return toRestaurantMenuDTO(menuFoodDTOS,categories,currentPage,totalPages,totalElements);
    }

    private RestaurantMenuDTO toRestaurantMenuDTO(List<MenuFoodDTO> menuFoodDTOS, List<String> categories, int currentPage, int totalPages, int totalElements) {
        return RestaurantMenuDTO.builder()
                .menus(menuFoodDTOS)
                .categories(categories)
                .currentPage(currentPage)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    private List<MenuFoodDTO> getFoodDTOList(List<MenuFood> menuFoods) {
        List<MenuFoodDTO> menuFoodDTOS = new ArrayList<>();
        for (MenuFood menuFood : menuFoods
        ) {
            menuFoodDTOS.add(toMenuFoodDTO(menuFood));
        }
        return menuFoodDTOS;
    }

    private MenuFoodListDTO toMenuFoodListDTO(List<MenuFoodDTO> menuFoods, int currentPage, int totalPages, long totalElements) {
        return MenuFoodListDTO.builder()
                .foods(menuFoods)
                .currentPage(currentPage)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    private MenuFoodDTO toMenuFoodDTO(MenuFood menuFood) {
        return MenuFoodDTO.builder()
                .menuFoodId(menuFood.getId())
                .name(menuFood.getFood().getName())
                .description(menuFood.getDescription())
                .restaurantName(menuFood.getMenu().getRestaurant().getName())
                .category(menuFood.getCategory().getCategory())
                .Meal(menuFood.getMeal().getMeal())
                .price(menuFood.getPrice())
                .restaurantAddress(fullAddress(menuFood.getMenu().getRestaurant().getAddress()))
                .rating(menuFood.getRating())
                .discountPrice(menuFood.getPrice() * menuFood.getDiscountPercentage() / 100)
                .imagePath(menuFood.getImagePath())
                .build();
    }
    private String fullAddress(RestaurantAddress address) {
        String fullAddress= address.getStreetNumber()+"-"+address.getStreetName()+","+address.getDistrict()+","+address.getState()+","+address.getCountry();
        return fullAddress;
    }
    private void validate(MenuRequest request) {

    }
}
