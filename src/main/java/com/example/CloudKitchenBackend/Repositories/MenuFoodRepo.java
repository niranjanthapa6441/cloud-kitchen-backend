package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.MenuFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuFoodRepo extends JpaRepository<MenuFood,String> {

    @Query("SELECT mf FROM MenuFood mf where mf.menu.menu_id=?1 and mf.food.food_id=?2")
    Optional<MenuFood> findMenuFoodByMenuAndFood(int menuId, int foodId);
}
