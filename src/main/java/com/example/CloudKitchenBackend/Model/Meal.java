package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Meal {

    @SequenceGenerator(
            name = "meal_id_seq",
            sequenceName = "meal_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator="meal_sequence"
    )
    @Column(name = "meal_id")
    private int id;

    @Column(name = "meal", nullable = false)
    private String meal;
}
