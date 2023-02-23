package com.example.CloudKitchenBackend.Controller;


import com.example.CloudKitchenBackend.Request.OrderRequest;
import com.example.CloudKitchenBackend.Service.OrderService;
import com.example.CloudKitchenBackend.Util.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return RestResponse.ok(service.findAll(phoneNumber, page, size),"Data Retrieval Successful");
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody OrderRequest request
    ){
        return RestResponse.ok(service.save(request),"Order Saved Successful");
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@RequestParam String id){
        return RestResponse.ok(service.cancel(id),"Order cancelled Successful");
    }
    @PostMapping(value ="/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id){
        return RestResponse.ok(service.update(id),"Order updated Successful");
    }
    @GetMapping(value = "/customer/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByCustomerId(
            @RequestParam String username,
            @RequestParam (required = false)String period,
            @RequestParam (required = false)String sortBy,
            @RequestParam (required = false)String startDate,
            @RequestParam (required = false)String endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return RestResponse.ok(service.findOrderByCustomer(username, period, sortBy, startDate, endDate, page, size),"Data Retrieval Successful");
    }
}
