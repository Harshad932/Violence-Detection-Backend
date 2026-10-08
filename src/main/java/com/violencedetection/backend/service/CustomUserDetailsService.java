package com.violencedetection.backend.service;

import com.violencedetection.backend.model.AppUser;
import com.violencedetection.backend.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository)
    {
        this.appUserRepository=appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        AppUser appUser=appUserRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles("USER")
                .build();

    }

}
