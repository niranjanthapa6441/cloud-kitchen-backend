package com.example.CloudKitchenBackend.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id",length = 100)
    private String id;
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
    @Column(name = "status",nullable = false)
    private String status;
    private Boolean locked=false;
    private Boolean enabled=false;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
