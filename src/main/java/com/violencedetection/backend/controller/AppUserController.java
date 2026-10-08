package com.violencedetection.backend.controller;

import com.violencedetection.backend.dto.RegisterRequest;
import com.violencedetection.backend.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @GetMapping("/")
    public String hello()
    {
        return "Hello From VD";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
    {
        return appUserService.registerUser(registerRequest);
    }

}
