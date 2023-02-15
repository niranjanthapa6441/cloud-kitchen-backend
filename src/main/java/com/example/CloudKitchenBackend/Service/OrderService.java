package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.CustomerOrderDTO;
import com.example.CloudKitchenBackend.DTO.CustomerOrderListDTO;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    CustomerOrderDTO save(OrderRequest request);
    CustomerOrderDTO update(String id);

    CustomerOrderDTO cancel(String id);

    CustomerOrderListDTO findOrderByCustomer(int id, int page, int size);

}
