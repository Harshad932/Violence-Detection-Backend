package com.violencedetection.backend.service;

import com.violencedetection.backend.dto.LoginRequest;
import com.violencedetection.backend.dto.LoginResponse;
import com.violencedetection.backend.dto.RegisterRequest;
import com.violencedetection.backend.model.AppUser;
import com.violencedetection.backend.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,JwtService jwtService)
    {
        this.appUserRepository=appUserRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    public ResponseEntity<String> registerUser(RegisterRequest registerRequest) {

        if(appUserRepository.existsByUsername(registerRequest.getUsername()))
        {
            return new ResponseEntity<>("Username Already Exists", HttpStatus.BAD_REQUEST);
        }

        if(appUserRepository.existsByEmail(registerRequest.getEmail()))
        {
            return new ResponseEntity<>("Email Already Exists", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser=new AppUser();
        appUser.setUsername(registerRequest.getUsername());
        appUser.setEmail(registerRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        appUserRepository.save(appUser);

        return new ResponseEntity<>("Registered Successfully",HttpStatus.OK);

    }

    public LoginResponse loginUser(LoginRequest loginRequest)
    {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );

        String username=authentication.getName();

        String token=jwtService.generateToken(username);

        return new LoginResponse(token);

    }

}
