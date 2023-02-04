package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
