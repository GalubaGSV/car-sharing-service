package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.mapper.DtoMapper;
import com.example.carsharingservice.model.Role;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User", description = "The User API. "
        + "Contains all the operations that can be performed on a customer/manager.")
public class UserController {
    private final UserService userService;
    private final DtoMapper<UserRequestDto, UserResponseDto, User> userMapper;

    @Operation(summary = "Update user role", description = "Update user role")
    @PutMapping("/{id}/role")
    public UserResponseDto updateRole(@PathVariable Long id,
            @RequestParam String role) {
        try {
            User user = userService.get(id);
            user.setRole(Role.valueOf(role));
            return userMapper.mapToDto(userService.update(user));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("There is no such role!", e);
        }
    }

    @Operation(summary = "Get current user info", description = "Get current user info")
    @GetMapping("/me")
    public UserResponseDto get(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        return userMapper.mapToDto(userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User doesn't exist!")));
    }

    @Operation(summary = "Update current user info", description = "Update current user info")
    @PutMapping("/me")
    public UserResponseDto updateInfo(Authentication auth,
                                      @RequestBody UserRequestDto dto) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        String email = details.getUsername();
        Long userId = userService.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User doesn't exist!")).getId();
        User user = userMapper.mapToModel(dto);
        user.setId(userId);
        return userMapper.mapToDto(userService.update(user));
    }
}
