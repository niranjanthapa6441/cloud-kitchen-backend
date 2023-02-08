package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.CategoryRequest;
import com.example.CloudKitchenBackend.Service.CategoryService;
import com.example.CloudKitchenBackend.ServiceImpl.CategoryServiceImpl;
import com.example.CloudKitchenBackend.Util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> categories(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return RestResponse.ok(service.findAll(category, page, size),"Data Retrieval Successful");
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody CategoryRequest request
            ){
        return RestResponse.ok(service.save(request),"Category Saved Successful");
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam int id){
        return RestResponse.ok(service.delete(id),"Category deleted Successful");
    }
    @PostMapping(value ="/update",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@RequestParam int id, @RequestBody CategoryRequest request){
        return RestResponse.ok(service.update(request, id),"Category updated Successful");
    }
}
