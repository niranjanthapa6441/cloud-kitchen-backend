package com.example.CloudKitchenBackend.Controller;

import com.example.CloudKitchenBackend.Request.MenuFoodRequest;
import com.example.CloudKitchenBackend.Request.MenuRequest;
import com.example.CloudKitchenBackend.Service.MenuFoodService;
import com.example.CloudKitchenBackend.Util.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/menuFood")
public class MenuFoodController {
    @Autowired
    MenuFoodService service;
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> save(
            @Valid @RequestPart MenuFoodRequest request, @RequestPart("image") MultipartFile multipartFile
    ){
        return RestResponse.ok(service.save(request,multipartFile));
    }
}
