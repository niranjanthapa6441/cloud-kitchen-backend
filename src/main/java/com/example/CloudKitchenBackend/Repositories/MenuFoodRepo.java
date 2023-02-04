package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.MenuFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuFoodRepo extends JpaRepository<MenuFood,String> {

}
