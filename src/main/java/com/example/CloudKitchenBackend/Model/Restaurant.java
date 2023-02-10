package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue( generator="system-uuid")
    @Column(name = "restaurant_id")
    private String id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "country_code", nullable = false,length = 5)
    private String countryCode;
    @Column(name ="phone_number", nullable = false, length = 10, unique = true)
    private String phoneNumber;

    @Column(name = "telephone_number",nullable = false,length = 10, unique = true)
    private String telephoneNumber;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private RestaurantAddress address;
    private String status;

}
