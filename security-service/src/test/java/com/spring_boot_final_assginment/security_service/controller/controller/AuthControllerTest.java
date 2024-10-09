package com.spring_boot_final_assginment.security_service.controller.controller;

import com.spring_boot_final_assginment.security_service.controller.AuthController;
import com.spring_boot_final_assginment.security_service.dto.UserDTO;
import com.spring_boot_final_assginment.security_service.exception.InvalidTokenException;
import com.spring_boot_final_assginment.security_service.exception.InvalidUserDetails;
import com.spring_boot_final_assginment.security_service.exception.InvalidUserException;
import com.spring_boot_final_assginment.security_service.service.AuthService;
import com.spring_boot_final_assginment.security_service.utils.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        UserDTO userDTO = new UserDTO(1L, "user1", "password");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(authService.registerUser(userDTO)).thenReturn("User registered successfully");

        String result = authController.registerUser(userDTO, bindingResult);

        assertEquals("User registered successfully", result);
        verify(authService, times(1)).registerUser(userDTO);
    }

    @Test
    public void testRegisterUser_ValidationErrors() {
        UserDTO userDTO = new UserDTO(1L, "user1", "password");
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(
                new FieldError("userDTO", "username", "must not be blank")
        ));

        InvalidUserDetails exception = assertThrows(InvalidUserDetails.class, () ->
                authController.registerUser(userDTO, bindingResult)
        );

        assertTrue(exception.getMessage().contains("Validation errors"));
        verify(authService, never()).registerUser(userDTO);
    }

    @Test
    public void testGenerateToken_Success() {
        UserDTO userDTO = new UserDTO(1L, "user1", "password");
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authService.login(userDTO.getUsername(), userDTO.getPassword())).thenReturn("mockToken");

        String token = authController.generateToken(userDTO);

        assertEquals("mockToken", token);
        verify(authService, times(1)).login(userDTO.getUsername(), userDTO.getPassword());
    }

    @Test
    public void testGenerateToken_InvalidCredentials() {
        UserDTO userDTO = new UserDTO(1L, "user1", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InvalidUserException("Invalid User Credentials: Please check your username and password.") {});

        InvalidUserException exception = assertThrows(InvalidUserException.class, () ->
                authController.generateToken(userDTO)
        );

        assertEquals(Constant.INVALID_USER_CREDENTIALS, exception.getMessage());
        verify(authService, never()).login(userDTO.getUsername(), userDTO.getPassword());
    }

    @Test
    public void testValidateToken_Success() {
        String token = "validToken";
        doNothing().when(authService).validateToken(token);

        String result = authController.validateToken(token);

        assertEquals("Valid User Token", result);
        verify(authService, times(1)).validateToken(token);
    }

    @Test
    public void testValidateToken_InvalidToken() {
        String token = "invalidToken";
        doThrow(new InvalidTokenException("Invalid Token")).when(authService).validateToken(token);

        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () ->
                authController.validateToken(token)
        );

        assertEquals(Constant.INVALID_TOKEN_MESSAGE, exception.getMessage());
        verify(authService, times(1)).validateToken(token);
    }

    @Test
    public void testGenerateToken_InvalidUserExceptionThrown() {
        UserDTO userDTO = new UserDTO(1L, "user1", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        InvalidUserException exception = assertThrows(InvalidUserException.class, () ->
                authController.generateToken(userDTO)
        );

        assertEquals(Constant.INVALID_USER_CREDENTIALS, exception.getMessage());
        verify(authService, never()).login(userDTO.getUsername(), userDTO.getPassword());
    }

}
