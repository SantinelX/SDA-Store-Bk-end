package com.sda.store.service.implementation;

import com.sda.store.exceptions.ResourceAlreadyPresentInDatabase;
import com.sda.store.exceptions.ResourceNotFoundInDatabase;
import com.sda.store.model.User;
import com.sda.store.repository.UserRepository;
import com.sda.store.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public User create(User user) {
        User userInDatabase = userRepository.findByEmail(user.getEmail());
        if (userInDatabase != null) {
            throw new ResourceAlreadyPresentInDatabase(String.format("User with email %s already exists", user.getEmail()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ResourceNotFoundInDatabase(String.format("User with id %d is not found in database !!!", id));
        }
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setImageUrl(user.getImageUrl());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setMessagingChannel(user.getMessagingChannel());
        return userRepository.save(existingUser);
    }

}
