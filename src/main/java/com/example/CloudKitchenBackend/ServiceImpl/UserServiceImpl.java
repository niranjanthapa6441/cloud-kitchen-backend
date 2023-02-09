package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.FoodDTO;
import com.example.CloudKitchenBackend.DTO.FoodListDTO;
import com.example.CloudKitchenBackend.DTO.UserDTO;
import com.example.CloudKitchenBackend.DTO.UserListDTO;
import com.example.CloudKitchenBackend.Model.Food;
import com.example.CloudKitchenBackend.Model.Role;
import com.example.CloudKitchenBackend.Model.User;
import com.example.CloudKitchenBackend.Repositories.RoleRepo;
import com.example.CloudKitchenBackend.Repositories.UserRepo;
import com.example.CloudKitchenBackend.Request.UserSignUpRequest;
import com.example.CloudKitchenBackend.Request.UserUpdateRequest;
import com.example.CloudKitchenBackend.Service.UserService;
import com.example.CloudKitchenBackend.Util.CustomException;
import com.example.CloudKitchenBackend.Util.DateTimeFormatter;
import com.example.CloudKitchenBackend.enums.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public UserListDTO users(String phoneNumber, String role, int page, int size) {
        List<User> users = new ArrayList<>();
        List<UserDTO> usersList = new ArrayList<>();
        Pageable paging= PageRequest.of(page, size);
        Page<User> pageUsers = null;
        if (phoneNumber == null && role == null)
            pageUsers= repo.findAll(paging);
        else if(phoneNumber != null)
            pageUsers= repo.findUserByPhoneNumber(phoneNumber,paging);
        else if (role != null)
            pageUsers= repo.findUserByRole(ERole.valueOf(role),paging);
        else if(phoneNumber != null && role != null)
            pageUsers= repo.findUserByPhoneNumberAndRole(phoneNumber,role,paging);
        users = pageUsers.getContent();
        for (User user:users
        ) {
            usersList.add(toUserDTO(user));
        }
        UserListDTO userListDTO = toUserListDTO(usersList,pageUsers.getNumber(),pageUsers.getTotalElements(),pageUsers.getTotalPages());
        return userListDTO;
    }
    @Override
    public UserDTO findById(int id) {
        return null;
    }

    @Override
    public String update(UserUpdateRequest request, int id) {
        return null;
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
    private void checkValidation(UserSignUpRequest request) {
        checkEmail(request.getEmail());
        checkUsername(request.getUsername());
        checkPhoneNumber(request.getPhoneNumber());
    }

    private void checkPhoneNumber(String phoneNumber) {
        if(repo.findUserByPhoneNumber(phoneNumber).isPresent()){
            throw new CustomException(CustomException.Type.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

    private void checkUsername(String username) {
        if(repo.findUserByUsername(username).isPresent()){
            throw new CustomException(CustomException.Type.USERNAME_ALREADY_EXIST);
        }
    }
    private UserListDTO toUserListDTO(List<UserDTO> usersList, int number, long totalElements, int totalPages) {
        return UserListDTO.builder()
                .userList(usersList).
                currentPage(number).
                totalPages(totalPages).
                totalElements(totalElements)
                .build();
    }
    private void checkEmail(String email) {
        if(repo.findUserByEmail(email).isPresent()){
            throw new CustomException(CustomException.Type.EMAIL_ALREADY_EXITS);
        }
    }
}
