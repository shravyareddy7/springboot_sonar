package com.spring_boot_final_assginment.security_service.controller.service;

import com.spring_boot_final_assginment.security_service.dto.UserDTO;
import com.spring_boot_final_assginment.security_service.entity.User;
import com.spring_boot_final_assginment.security_service.repository.UserRepository;
import com.spring_boot_final_assginment.security_service.service.AuthService;
import com.spring_boot_final_assginment.security_service.utils.Constant;
import com.spring_boot_final_assginment.security_service.utils.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        UserDTO userDTO = new UserDTO(1L, "user1", "password");
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = authService.registerUser(userDTO);

        assertEquals(Constant.SUCCESSFUL_REGISTRATION, result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
    }

    @Test
    public void testLogin_Success() {
        String username = "user1";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(username)).thenReturn("mockJwtToken");

        String token = authService.login(username, password);

        assertEquals("mockJwtToken", token);
        verify(jwtService, times(1)).generateToken(username);
    }
    @Test
    public void testLogin_InvalidCredentials() {
        String username = "user1";
        String password = "wrongPassword";
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(username, password));

        assertEquals(Constant.INVALID_USER_CREDENTIALS, exception.getMessage());
        verify(jwtService, never()).generateToken(username);
    }

    @Test
    public void testLogin_UserNotFound() {
        String username = "user1";
        String password = "password";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(username, password));

        assertEquals(Constant.USER_NOT_FOUND, exception.getMessage());
        verify(jwtService, never()).generateToken(username);
    }

    @Test
    public void testValidateToken_Success() {
        String token = "validToken";

        doNothing().when(jwtService).validateToken(token);

        authService.validateToken(token);

        verify(jwtService, times(1)).validateToken(token);
    }
}
