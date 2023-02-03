package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menu_food")
public class MenuFood {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "menu_food-id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @OneToOne
    @JoinColumn(name="meal_id")
    private Meal meal;

    @Column(name = "description", nullable = false)
    private String Description;

    @Column(name = "price", nullable = false)
    private double price;
}
