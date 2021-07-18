package com.sda.store.service;

import com.sda.store.model.User;

public interface UserService {
    User create(User user);
    User findByEmail(String email);
    User findById(Long id);
    User updateUser(User user);
}
