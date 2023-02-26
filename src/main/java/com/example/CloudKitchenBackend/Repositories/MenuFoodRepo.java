package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Food;
import com.example.CloudKitchenBackend.Model.Menu;
import com.example.CloudKitchenBackend.Model.MenuFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuFoodRepo extends JpaRepository<MenuFood,String>, JpaSpecificationExecutor<MenuFood> {

    @Query("SELECT mf FROM MenuFood mf where mf.menu=?1 and mf.food=?2")
    MenuFood findMenuFoodByMenuAndFood(Menu menu, Food food);

    @Query("SELECT DISTINCT mf.category.category FROM MenuFood mf WHERE mf.menu.restaurant.id = ?1")
    List<String> findDistinctCategoriesByRestaurantId(String restaurantId);

}
