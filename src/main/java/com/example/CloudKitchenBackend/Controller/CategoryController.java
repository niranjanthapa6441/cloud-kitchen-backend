package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Service.CategoryService;
import com.example.CloudKitchenBackend.ServiceImpl.CategoryServiceImpl;
import com.example.CloudKitchenBackend.Util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl service;

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return RestResponse.ok(service.findAll(category, page, size),"Data Retrieval Successful");
    }
}
