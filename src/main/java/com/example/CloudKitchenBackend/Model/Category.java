package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name="category")
public class Category {
    @SequenceGenerator(
            name = "category_id_seq",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator="category_sequence"
    )
    @Column(name="category_id",length=10)
    private int id;


    @Column(name = "category", nullable = false, length = 20)
    private String category;

    @Column(name = "description", nullable = false)
    private String description;
}
