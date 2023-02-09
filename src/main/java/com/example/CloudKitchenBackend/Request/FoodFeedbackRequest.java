package com.example.CloudKitchenBackend.Request;

import com.example.CloudKitchenBackend.Model.MenuFood;
import com.example.CloudKitchenBackend.Model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class FoodFeedbackRequest {
    private String feedback;
    private double rating;
    private User customer;
    private String menuFoodId;
}
