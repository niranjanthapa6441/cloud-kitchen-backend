package com.example.CloudKitchenBackend.Service;

import com.example.CloudKitchenBackend.DTO.UserDTO;
import com.example.CloudKitchenBackend.DTO.UserListDTO;
import com.example.CloudKitchenBackend.Request.UserSignUpRequest;
import com.example.CloudKitchenBackend.Request.UserUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO save(UserSignUpRequest request);
    String delete(int id);


    UserListDTO users(String phoneNumber, String role, int page, int size);

    UserDTO findById(int id);

    String update(UserUpdateRequest request, int id);


}
