package com.violencedetection.backend.controller;

import com.violencedetection.backend.dto.LoginRequest;
import com.violencedetection.backend.dto.LoginResponse;
import com.violencedetection.backend.dto.RegisterRequest;
import com.violencedetection.backend.dto.UserProfileResponse;
import com.violencedetection.backend.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
    {
        String message= appUserService.registerUser(registerRequest);

        return ResponseEntity.status(201).body(message);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse=appUserService.loginUser(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("api/users/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(Authentication authentication)
    {
        UserProfileResponse profile=appUserService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(profile);
    }

}
