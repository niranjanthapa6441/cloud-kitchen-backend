package com.example.CloudKitchenBackend.Repositories;


import com.example.CloudKitchenBackend.Model.User;
import com.example.CloudKitchenBackend.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    Page<User> findUserByPhoneNumber(String phoneNumber,Pageable paging);

    Page<User> findUserByRole(ERole role, Pageable paging);

    Page<User> findUserByPhoneNumberAndRole(String phoneNumber, String role, Pageable paging);
}
