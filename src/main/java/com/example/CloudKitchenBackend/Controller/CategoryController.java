package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.CategoryRequest;
import com.example.CloudKitchenBackend.Service.CategoryService;
import com.example.CloudKitchenBackend.Util.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {

        public static final String SIZE = "5";
        public static final String PAGE = "1";
        public static final String MESSAGE = "Successful";
    public static final String CATEGORY_BY_ID_URI = "/{id}";
    @Autowired
    private CategoryService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> categories(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = PAGE) int page,
            @RequestParam(defaultValue = SIZE) int size
    ){
        return RestResponse.ok(service.findAll(category, page, size),MESSAGE);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody CategoryRequest request
            ) {
        return RestResponse.ok(service.save(request),MESSAGE);
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam int id){
        return RestResponse.ok(service.delete(id),MESSAGE);
    }
    @PostMapping(value = CATEGORY_BY_ID_URI,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody CategoryRequest request){
        return RestResponse.ok(service.update(request, id), MESSAGE);
    }
}
