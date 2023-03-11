package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import com.example.CloudKitchenBackend.Service.MenuFoodService;
import com.example.CloudKitchenBackend.Service.MenuService;
import com.example.CloudKitchenBackend.ServiceImpl.MenuServiceImpl;
import com.example.CloudKitchenBackend.ServiceImpl.OrderServiceImpl;
import com.example.CloudKitchenBackend.Util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService service;

    @Autowired
    private MenuFoodService menuFoodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(
            @RequestParam(required = false) String foodName,
            @RequestParam(required = false) String restaurantName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String meal,
            @RequestParam String restaurantId,
            @RequestParam(defaultValue = "0.0") double rating,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size

    ){
        return RestResponse.ok(service.searchMenuFoods(foodName,restaurantId,restaurantName,category,meal, rating,sortBy,page,size),"Data Retrieval Successful");
    }
    @GetMapping(value = "/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> menuByRestaurant(
            @RequestParam(required = false) String foodName,
            @RequestParam String restaurantId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String meal,
            @RequestParam(defaultValue = "0.0") double rating,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size

    ){
        return RestResponse.ok(service.restaurantMenu(foodName,restaurantId,category,meal, rating,sortBy,page,size),"Data Retrieval Successful");
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody MenuRequest request
    ){
        return RestResponse.ok( service.save(request));
    }
    @DeleteMapping(value = "menuFood/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam String id){
        return RestResponse.ok(menuFoodService.delete(id),"Menu cancelled Successful");
    }
    @PostMapping(value ="menuFood/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id, @Valid @RequestBody MenuFoodRequest request){
        return RestResponse.ok(menuFoodService.update(request,id),"Menu updated Successful");
    }
    @GetMapping(value = "menuFood/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(
            @PathVariable String id
    ){
        return RestResponse.ok(menuFoodService.findById(id),"Menu Retrieval Successful");
    }
}
