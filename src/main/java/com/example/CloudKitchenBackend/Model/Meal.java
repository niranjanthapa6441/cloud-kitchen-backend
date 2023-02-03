package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private int id;

    @Column(name = "meal", nullable = false)
    private String meal;
}
