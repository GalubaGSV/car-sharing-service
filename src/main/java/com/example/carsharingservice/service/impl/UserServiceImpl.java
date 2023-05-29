package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.User;
import com.example.carsharingservice.repository.UserRepository;
import com.example.carsharingservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User update(User user) {
        User userFromDb = userRepository.getReferenceById(user.getId());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setRole(user.getRole());
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        return userRepository.save(userFromDb);
    }

    @Override
    public User get(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
