package com.violencedetection.backend.controller;

import com.violencedetection.backend.dto.LoginRequest;
import com.violencedetection.backend.dto.LoginResponse;
import com.violencedetection.backend.dto.RegisterRequest;
import com.violencedetection.backend.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService)
    {
        this.appUserService=appUserService;
    }

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse=appUserService.loginUser(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }

}
