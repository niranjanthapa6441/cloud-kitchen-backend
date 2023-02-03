package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Time;
@Data
@Entity
@Table(name = "menu")
public class Menu {
    @SequenceGenerator(
            name = "menu_id_seq",
            sequenceName = "menu_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator="menu_sequence"
    )
    @Column(name = "menu_id")
    private int id;
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "description",nullable = false)
    private String Description;

    @Column(name = "opening_time", nullable = false)
    private Time openingTime;

    @Column(name = "closing_time", nullable = false)
    private Time closingTime;
}
