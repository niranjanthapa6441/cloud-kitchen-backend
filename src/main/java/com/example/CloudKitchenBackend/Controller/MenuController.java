package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import com.example.CloudKitchenBackend.Service.MenuFoodService;
import com.example.CloudKitchenBackend.Service.MenuService;

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
    public static final String MENU_FOOD_ID_URI = "menuFood/{id}";
    public static final String SIZE = "5";
    public static final String PAGE = "1";
    public static final String RATING = "0.0";
    public static final String MESSAGE = "Successful";
    public static final String RESTAURANT_URI = "/restaurant";
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
            @RequestParam(defaultValue = RATING) double rating,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = PAGE) int page,
            @RequestParam(defaultValue = SIZE) int size

    ){
        return RestResponse.ok(service.searchMenuFoods(foodName,restaurantName,category,meal, rating,sortBy,page,size),"Data Retrieval Successful");
    }
    @GetMapping(value = RESTAURANT_URI,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> menuByRestaurant(
            @RequestParam(required = false) String foodName,
            @RequestParam String restaurantId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String meal,
            @RequestParam(defaultValue = RATING) double rating,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = PAGE) int page,
            @RequestParam(defaultValue = SIZE) int size

    ){
        return RestResponse.ok(service.restaurantMenu(foodName,restaurantId,category,meal, rating,sortBy,page,size),MESSAGE);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody MenuRequest request
    ){
        return RestResponse.ok( service.save(request));
    }
    @DeleteMapping(value = MENU_FOOD_ID_URI,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam String id){
        return RestResponse.ok(menuFoodService.delete(id), MESSAGE);
    }
    @PostMapping(value =MENU_FOOD_ID_URI,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id, @Valid @RequestBody MenuFoodRequest request){
        return RestResponse.ok(menuFoodService.update(request,id),MESSAGE);
    }
    @GetMapping(value = MENU_FOOD_ID_URI,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(
            @PathVariable String id
    ){
        return RestResponse.ok(menuFoodService.findById(id),MESSAGE);
    }
}
