package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(strategy = "uuid",name="system-uuid")
    @Column(name = "order_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;
    @Column(nullable = false, name = "total_price")
    private double totalPrice;

    @Column(nullable = false,name = "total_time")
    private int totalItems;
    @Column(nullable = false, name = "order_date")
    private LocalDate orderDate;

    @Column(nullable = false,name = "order_time")
    private LocalTime orderTime;

    @Column(nullable = false)
    private String status;
}
