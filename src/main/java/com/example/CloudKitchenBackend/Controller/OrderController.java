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
    public static final String ORDER_BY_ID_URI = "/{id}";
    public static final String ORDER_BY_CUSTOMER_URI = "/customer/{id}";

    public static final String SIZE = "5";
    public static final String PAGE = "1";

    public static final String MESSAGE = "Successful";
    @Autowired
    private OrderService service;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAll(
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(defaultValue = PAGE) int page,
            @RequestParam(defaultValue = SIZE) int size
    ){
        return RestResponse.ok(service.findAll(phoneNumber, page, size),MESSAGE);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid @RequestBody OrderRequest request
    ){
        return RestResponse.ok(service.save(request),MESSAGE);
    }
    @DeleteMapping(value = ORDER_BY_ID_URI,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable String id){
        return RestResponse.ok(service.cancel(id),MESSAGE);
    }
    @PostMapping(value =ORDER_BY_ID_URI,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id){
        return RestResponse.ok(service.update(id),MESSAGE);
    }
    @GetMapping(value = ORDER_BY_CUSTOMER_URI,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByCustomerId(
            @PathVariable int id,
            @RequestParam (required = false)String period,
            @RequestParam (required = false)String sortBy,
            @RequestParam (required = false)String status,
            @RequestParam (required = false)String startDate,
            @RequestParam (required = false)String endDate,
            @RequestParam(defaultValue = PAGE) int page,
            @RequestParam(defaultValue = SIZE) int size
    ){
        return RestResponse.ok(service.findOrderByCustomer(id,status,period, sortBy, startDate, endDate, page, size),MESSAGE);
    }
}
