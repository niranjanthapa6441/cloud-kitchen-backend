package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.MenuRequest;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import com.example.CloudKitchenBackend.ServiceImpl.MenuServiceImpl;
import com.example.CloudKitchenBackend.ServiceImpl.OrderServiceImpl;
import com.example.CloudKitchenBackend.Util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuServiceImpl service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(
            @RequestParam(required = false) String foodName,
            @RequestParam(required = false) String restaurantName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String meal,
            @RequestParam(defaultValue = "0.0") double rating,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size

    ){
        return RestResponse.ok(service.searchMenuFoods(foodName,restaurantName,category,meal, rating,sortBy,page,size),"Data Retrieval Successful");
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody MenuRequest request
    ){
        return RestResponse.ok(service.save(request),"Order Saved Successful");
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam int id){
        return RestResponse.ok(service.delete(id),"Order cancelled Successful");
    }
    @PostMapping(value ="/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody MenuRequest request){
        return RestResponse.ok(service.update(request,id),"Order updated Successful");
    }
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(
            @PathVariable int id
    ){
        return RestResponse.ok(service.findById(id),"Data Retrieval Successful");
    }
}
