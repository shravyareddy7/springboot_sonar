package com.spring_boot_final_assginment.security_service.controller;

import com.spring_boot_final_assginment.security_service.dto.UserDTO;
import com.spring_boot_final_assginment.security_service.exception.InvalidTokenException;
import com.spring_boot_final_assginment.security_service.exception.InvalidUserDetails;
import com.spring_boot_final_assginment.security_service.exception.InvalidUserException;
import com.spring_boot_final_assginment.security_service.service.AuthService;
import com.spring_boot_final_assginment.security_service.utils.Constant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.AUTH)
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(Constant.REGISTER_URL)
    public String registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder(Constant.VALIDATION_ERRORS);
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessage.append(error.getField()).append(" --> ").append(error.getDefaultMessage()).append("; ")
            );
            throw new InvalidUserDetails(errorMessage.toString());
        }
        return authService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody UserDTO userDTO) {
        System.out.println("login method");
        try {
            System.out.println("try");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );
            return authService.login(userDTO.getUsername(), userDTO.getPassword());
        } catch (AuthenticationException e) {
            throw new InvalidUserException(Constant.INVALID_USER_CREDENTIALS);
        }
    }

    @PostMapping(Constant.VALIDATE)
    public String validateToken(@RequestParam("token") String token) {
        System.out.println(0);
        try {
            authService.validateToken(token);
            return "Valid User Token";
        } catch (Exception e) {
            throw new InvalidTokenException(Constant.INVALID_TOKEN_MESSAGE);
        }
    }
}
