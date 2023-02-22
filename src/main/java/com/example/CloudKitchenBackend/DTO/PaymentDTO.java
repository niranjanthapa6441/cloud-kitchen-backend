package com.example.CloudKitchenBackend.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDTO {
    private double paidAmount;

    private String paymentDate;

    private String paymentTime;

    private String paymentMethod;

    private String paymentPartner;

    private String status;

}
