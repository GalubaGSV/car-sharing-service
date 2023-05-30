package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;

public interface UserService {
    User update(User user);

    User get(Long id);

    User findByEmail(String email);
}