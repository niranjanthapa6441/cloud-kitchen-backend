package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.UserDTO;
import com.example.CloudKitchenBackend.Model.Role;
import com.example.CloudKitchenBackend.Model.User;
import com.example.CloudKitchenBackend.Repositories.RoleRepo;
import com.example.CloudKitchenBackend.Repositories.UserRepo;
import com.example.CloudKitchenBackend.Request.UserSignUpRequest;
import com.example.CloudKitchenBackend.Request.UserUpdateRequest;
import com.example.CloudKitchenBackend.Service.UserService;
import com.example.CloudKitchenBackend.Util.DateTimeFormatter;
import com.example.CloudKitchenBackend.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo repo;
    /*@Autowired
    private PasswordEncoder encoder;*/
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public UserDTO save(UserSignUpRequest request) {
        checkValidation(request);
        User user=toUser(request);
        repo.save(user);
        return toUserSignUpDTO(user);
    }


    @Override
    public String delete(int id) {
        return null;
    }

    @Override
    public UserDTO users(String phoneNumber, String role, int page, int size) {
        return null;
    }

    @Override
    public UserDTO findById(int id) {
        return null;
    }

    @Override
    public String update(UserUpdateRequest request, int id) {
        return null;
    }
    private void checkValidation(UserSignUpRequest request) {

    }
    private User toUser(UserSignUpRequest request) {
        User user= new User();
        DateTimeFormatter dateTimeFormatter= new DateTimeFormatter();
        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(dateTimeFormatter.formatDate(request.getDateOfBirth()));
        user.setEmail(request.getEmail());
//        user.setPassword(encoder.encode(request.getPassword()));
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setRole(role(request.getRole()));
        user.setEnabled(false);
        user.setLocked(false);
        user.setStatus("Active");
        user.setPhoneNumber(request.getPhoneNumber());
        return  user;
    }
    private Role role(String role){
        Role findRole=roleRepo.findByName(ERole.valueOf(role));
        return findRole;
    }
    private UserDTO toUserDTO(User user) {
        return UserDTO.builder().
                role(user.getRole().getName()).
                username(user.getUsername()).
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                middleName(user.getMiddleName()).
                phoneNumber(user.getPhoneNumber()).
                email(user.getEmail()).
                dateOfBirth(user.getDateOfBirth().toString())
        .build();
    }
    private UserDTO toUserSignUpDTO(User user) {
        return UserDTO.builder().
                role(user.getRole().getName()).
                username(user.getUsername())
                .build();
    }
}
