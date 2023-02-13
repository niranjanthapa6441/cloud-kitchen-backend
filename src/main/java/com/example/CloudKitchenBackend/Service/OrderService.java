package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.CustomerOrderDTO;
import com.example.CloudKitchenBackend.DTO.CustomerOrderListDTO;
import com.example.CloudKitchenBackend.DTO.OrderDetailsDTO;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    CustomerOrderDTO save(OrderRequest request);
    CustomerOrderDTO update(String id);

    CustomerOrderDTO cancel(String id);

    CustomerOrderListDTO findOrderByCustomer(int id, int page, int size);

    OrderDetailsDTO findOrderByOrderId(String id);

}
