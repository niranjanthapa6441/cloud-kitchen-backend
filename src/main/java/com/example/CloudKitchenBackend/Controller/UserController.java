package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.CategoryRequest;
import com.example.CloudKitchenBackend.Request.UserSignUpRequest;
import com.example.CloudKitchenBackend.Request.UserUpdateRequest;
import com.example.CloudKitchenBackend.Service.UserService;
import com.example.CloudKitchenBackend.Util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(
            @RequestParam String phoneNumber,
            @RequestParam String role,
            @RequestParam int page,
            @RequestParam int size
    ){
        return RestResponse.ok(service.users(phoneNumber, role, page, size),"App User List");
    }
    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@Valid @RequestBody UserSignUpRequest request){
        return RestResponse.ok(service.save(request),"User has been registered successfully");
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam int id){
        return RestResponse.ok(service.delete(id),"Category deleted Successful");
    }
    @PostMapping(value = "/update",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestParam int id,@Valid @RequestBody UserUpdateRequest request){
        return RestResponse.ok(service.update(request, id),"Category updated Successful");
    }
}
