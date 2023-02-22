package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.CustomerOrder;
import com.example.CloudKitchenBackend.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,String> {

    List<OrderItem> findCustomerOrderByCustomerOrder(CustomerOrder customerOrder);
}
