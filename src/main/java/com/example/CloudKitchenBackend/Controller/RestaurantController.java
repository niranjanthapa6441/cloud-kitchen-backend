package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.CategoryRequest;
import com.example.CloudKitchenBackend.Request.RestaurantRequest;
import com.example.CloudKitchenBackend.ServiceImpl.CategoryServiceImpl;
import com.example.CloudKitchenBackend.ServiceImpl.RestaurantServiceImpl;
import com.example.CloudKitchenBackend.Util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantServiceImpl service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findALL(
            @RequestParam(required = false) String restaurantName,
            @RequestParam(defaultValue = "0.0") double rating,
            @RequestParam(required = false) String latitude,
            @RequestParam(required = false) String longitude,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return RestResponse.ok(service.findAll(restaurantName,rating,latitude,longitude,page,size),"Data Retrieval Successful");
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody RestaurantRequest request
    ){
        return RestResponse.ok(service.save(request),"Category Saved Successful");
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam String id){
        return RestResponse.ok(service.delete(id),"Category deleted Successful");
    }
    @PostMapping(value ="/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody RestaurantRequest request){
        return RestResponse.ok(service.update(request, id),"Category updated Successful");
    }
}
