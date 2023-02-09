package com.example.CloudKitchenBackend.Request;

import com.example.CloudKitchenBackend.Model.RestaurantAddress;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RestaurantRequest {
    @NotBlank(message = "Restaurant Name Should not be empty")
    private String name;

    @NotBlank(message = "Country Code Should not be empty")
    private String countryCode;
    @NotBlank(message = "Phone number  Should not be empty")
    private String phoneNumber;

    @NotBlank(message = "Telephone number Should not be empty")
    private String telephoneNumber;

    @Email
    @NotBlank(message = "Email Should not be empty")
    private String email;

    @NotBlank(message = "Restaurant Address Should not be empty")
    private RestaurantAddress companyAddress;

    @Size(min = 8)
    @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message = "password field must contain characters")
    @NotBlank(message = "Password should not be empty")
    private String password;
}
