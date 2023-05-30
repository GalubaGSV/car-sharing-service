package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserLoginDto;
import com.example.carsharingservice.dto.request.UserRegistrationDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.AuthenticationService;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import com.example.carsharingservice.service.mapper.UserMapper;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationDto dto) {
        User user = authenticationService.register(dto.getEmail(), dto.getPassword(),
                dto.getFirstName(), dto.getLastName());
        return userMapper.mapToDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto dto)
            throws AuthenticationException {
        User user = authenticationService.login(dto.getLogin(),
                dto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), List.of(user.getRole().name()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}