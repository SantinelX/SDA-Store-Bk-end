package com.sda.store.controller;

import com.sda.store.controller.dto.product.ProductRequestDto;
import com.sda.store.controller.dto.user.AddressDto;
import com.sda.store.controller.dto.user.UserDto;
import com.sda.store.model.*;
import com.sda.store.service.RoleService;
import com.sda.store.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping(value = "/register")
    public UserDto create(@RequestBody UserDto userDto) {
        User user = userService.create(mapUserDtoToUser(userDto));
        return mapUserToUserDto(user);
    }

    @GetMapping(value = "/users/roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @PostMapping(value = "/users/login")
    public org.springframework.security.core.userdetails.User login() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PutMapping(value = "/users/{id}")
    public UserDto update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        User updateUser = updateUserDtoToUser(userService.findById(id), userDto);
        return mapUserToUserDto(userService.updateUser(updateUser));
    }

    private UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setImgUrl(user.getImageUrl());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMessagingChannel(user.getMessagingChannel());

        Set<Role> userRoles = user.getRole();
        for (Role role : userRoles) {
            userDto.setRole(role.getName());
        }

        userDto.setAddressDto(AddressDto.mapAddressToAddressDto(user.getAddress()));
        return userDto;
    }

    private User mapUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setImageUrl(userDto.getImgUrl());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMessagingChannel(userDto.getMessagingChannel());
        Role role = roleService.findByName(userDto.getRole());
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setAddress(mapAddressDtoToAddress(userDto.getAddressDto()));
        return user;
    }

    private Address mapAddressDtoToAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setZipCode(addressDto.getZipCode());
        address.setStreet(addressDto.getStreet());
        return address;
    }

    private User updateUserDtoToUser(User user, UserDto userDto) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setImageUrl(userDto.getImgUrl());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setMessagingChannel(userDto.getMessagingChannel());
        return user;

    }
}
