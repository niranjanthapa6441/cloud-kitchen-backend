package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.UserSignUpRequest;
import com.example.CloudKitchenBackend.Request.UserUpdateRequest;
import com.example.CloudKitchenBackend.Service.UserService;
import com.example.CloudKitchenBackend.Util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return RestResponse.ok(service.users(phoneNumber, role, page, size),"App User List");
    }
    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@Valid @RequestBody UserSignUpRequest request){
        return RestResponse.ok(service.save(request),"User has been registered successfully");
    }
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable int id){
        return RestResponse.ok(service.delete(id),"User deleted Successful");
    }
    @PostMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable int id,@Valid @RequestBody UserUpdateRequest request){
        return RestResponse.ok(service.update(request, id),"User updated Successful");
    }
    
}
