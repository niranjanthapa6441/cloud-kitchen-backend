package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
