package com.spring_boot_final_assginment.security_service.service;

import com.spring_boot_final_assginment.security_service.dto.UserDTO;
import com.spring_boot_final_assginment.security_service.entity.User;
import com.spring_boot_final_assginment.security_service.repository.UserRepository;
import com.spring_boot_final_assginment.security_service.utils.Constant;
import com.spring_boot_final_assginment.security_service.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
        return Constant.SUCCESSFUL_REGISTRATION;
    }

    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(Constant.USER_NOT_FOUND));
        System.out.println(user);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtService.generateToken(username);
        } else {
            throw new RuntimeException(Constant.INVALID_USER_CREDENTIALS);
        }
    }

    public void validateToken(String token){
        System.out.println("2");
        jwtService.validateToken(token);
    }
}
