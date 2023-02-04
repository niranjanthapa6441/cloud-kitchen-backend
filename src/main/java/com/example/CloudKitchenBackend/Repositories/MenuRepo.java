package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends JpaRepository<Menu,Integer> {
}
