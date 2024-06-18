package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(strategy = "uuid",name="system-uuid")
    private String id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_time")
    private LocalTime paymentTime;

    @Column(name = "payment_partner")
    private String paymentPartner;

    @Column(name = "payment_method",nullable = false)
    private String paymentMethod;

    @Column(name = "paid_amount",nullable = false)
    private double paidAmount;

    @Column(nullable = false)
    private String status;


}
