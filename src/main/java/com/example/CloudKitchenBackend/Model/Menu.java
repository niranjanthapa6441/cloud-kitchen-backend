package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Time;

public class Menu {
    @Id
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    @OneToOne
    @JoinColumn(name = "restaurant_d")
    private Restaurant restaurant;

    @Column(name = "description",nullable = false)
    private String Description;

    @Column(name = "opening_time", nullable = false)
    private Time openingTime;

    @Column(name = "closing_time", nullable = false)
    private Time closingTime;
}
