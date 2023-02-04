package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.RestaurantAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantAddressRepo extends JpaRepository<RestaurantAddress,String> {
}
