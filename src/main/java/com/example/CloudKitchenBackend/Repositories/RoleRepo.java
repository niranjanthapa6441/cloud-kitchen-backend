package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Role;
import com.example.CloudKitchenBackend.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Role findByName(ERole name);
}
