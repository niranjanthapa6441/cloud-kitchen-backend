package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.PaymentListDTO;
import com.example.CloudKitchenBackend.Service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentListDTO findAll() {
        return null;
    }

    @Override
    public PaymentListDTO findPaymentByCustomer(String username, String period, String startDate, String endDate, String paymentMethod, String paymentPartner, int page, int size) {
        return null;
    }
}
