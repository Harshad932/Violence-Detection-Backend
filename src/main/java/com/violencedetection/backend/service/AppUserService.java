package com.violencedetection.backend.service;

import com.violencedetection.backend.dto.RegisterRequest;
import com.violencedetection.backend.model.AppUser;
import com.violencedetection.backend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
