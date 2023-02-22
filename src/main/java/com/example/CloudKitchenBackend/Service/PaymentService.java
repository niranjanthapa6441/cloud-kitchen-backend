package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.PaymentListDTO;
import com.example.CloudKitchenBackend.Model.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    PaymentListDTO findAll(String username, String period, String startDate, String endDate, String paymentMethod, String paymentPartner, int page, int size);
}
