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

    private String country;

    private String state;

    private String district;

    private String streetName;

    private String streetNumber;
}
