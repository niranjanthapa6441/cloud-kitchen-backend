package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,String> {
}
