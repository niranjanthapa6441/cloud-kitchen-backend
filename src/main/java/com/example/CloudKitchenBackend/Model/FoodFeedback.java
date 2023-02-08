package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table
public class FoodFeedback {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(strategy = "uuid",name = "system-uuid")
    private String id;
    @Column(nullable = false)
    private String feedback;
    @Column(nullable = false)
    private double rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;
    @ManyToOne
    @JoinColumn(name = "order_menu_food_id")
    private OrderMenuFood orderMenuFood;
}
