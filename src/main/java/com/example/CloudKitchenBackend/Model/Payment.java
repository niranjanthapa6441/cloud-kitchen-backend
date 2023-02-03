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

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_date",nullable = false)
    private LocalDate paymentDate;

    @Column(name = "payment_time",nullable = false)
    private LocalTime paymentTime;

    @Column(name = "payment_partner",nullable = false)
    private String payment_partner;

    @Column(name = "payment_method",nullable = false)
    private String payment_method;

    @Column(nullable = false)
    private String status;

}
