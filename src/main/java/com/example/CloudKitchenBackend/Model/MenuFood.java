package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "menu_food")
public class MenuFood {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(strategy = "uuid",name="system-uuid")
    @Column(name = "menu_food_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @OneToOne
    @JoinColumn(name="meal_id")
    private Meal meal;

    @Column(name = "description",columnDefinition = "text", nullable = false)
    private String Description;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "discount_percentage",length = 4)
    private double discountPercentage;

    private double rating;

    @Column(name = "image_path",nullable = false)
    private String imagePath;
    @Column(name = "image",nullable = false)
    private String image;
}
