package com.spring_boot_final_assginment.security_service.config;

import com.spring_boot_final_assginment.security_service.entity.User;
import com.spring_boot_final_assginment.security_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.spring_boot_final_assginment.security_service.exception.InvalidUserException;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> credentials = repository.findByUsername(username);
        return credentials.map(CustomUserDetails::new).orElseThrow(() -> new InvalidUserException("User not found with username : " + username));
    }
}