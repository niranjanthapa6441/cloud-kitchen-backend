package com.example.CloudKitchenBackend.Request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RestaurantAddressRequest {
    private String country;

    private String state;

    private String district;

    private String streetName;

    private String streetNumber;
}
