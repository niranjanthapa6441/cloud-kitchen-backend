package com.example.CloudKitchenBackend.DTO;

import com.example.CloudKitchenBackend.Model.RestaurantAddress;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
public class RestaurantDTO {
    private String id;

    private String name;

    private String countryCode;
    private String phoneNumber;

    private String telephoneNumber;

    private String email;
    private String address;
    private String status;
}
