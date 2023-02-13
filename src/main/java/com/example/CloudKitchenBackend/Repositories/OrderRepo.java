package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Order;
import com.example.CloudKitchenBackend.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,String> {
    Page<Order> findByCustomer(Optional<User> customer, Pageable paging);
}
