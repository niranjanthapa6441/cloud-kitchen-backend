package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "app_user")
public class User {
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator="customer_sequence"
    )
    @Column(name = "user_id",length = 10)
    private int id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "phone_number",nullable = false,unique = true)
    private String phoneNumber;
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "status",nullable = false)
    private String status;
    private Boolean locked=false;
    private Boolean enabled=false;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
