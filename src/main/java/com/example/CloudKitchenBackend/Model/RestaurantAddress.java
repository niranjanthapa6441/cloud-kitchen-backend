package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "restaurant_address")
public class RestaurantAddress {
    @Id
    @GenericGenerator(strategy = "uuid",name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "address_id")
    private String id;

    @Column(name = "country",nullable = false)
    private String country;


    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "district",nullable = false)
    private String district;
    @Column(name = "street_name",nullable = false)
    private String streetName;
    @Column(name = "street_number",nullable = false)
    private String streetNumber;
}
