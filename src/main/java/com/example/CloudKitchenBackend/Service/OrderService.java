package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.CustomerOrderDTO;
import com.example.CloudKitchenBackend.DTO.CustomerOrderListDTO;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface OrderService {
    CustomerOrderDTO save(OrderRequest request);
    CustomerOrderDTO update(String id);

    CustomerOrderDTO cancel(String id);

    HashMap<String,Object> findAll(String phoneNumber, int page, int size);
    HashMap<String,Object> findOrderByCustomer(String username, String period, String sortBy, String startDate, String endDate, int page, int size);

}
