package com.springboot.userservice.controller;

import com.springboot.userservice.data.User;
import com.springboot.userservice.model.UserDTO;
import com.springboot.userservice.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;import java.util.List;
@ResponseStatus
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listUser")
    public List<User> getAllUser() {
        return userService.getAll();
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
