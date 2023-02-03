package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name="category")
public class Category {
    @Id
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name="category_id",length = 20)
    private String id;


    @Column(name = "category", nullable = false, length = 20)
    private String category;

    @Column(name = "description", nullable = false)
    private String description;
}
