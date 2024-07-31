package com.springboot.userservice.controller;
import java.util.List;

import com.springboot.userservice.data.User;
import com.springboot.userservice.model.UserDTO;
import com.springboot.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/listUser")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody UserDTO dto) {
        return userService.saveUser(dto);
    }
    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO dto) {
        return userService.login(dto.getUsername(), dto.getPassword());
    }
}