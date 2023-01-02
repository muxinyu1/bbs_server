package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.User;
import com.mxy.bbs_server.response.user.UserResponse;
import com.mxy.bbs_server.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public UserResponse addUser(@RequestBody User user) {
        return userService.add(user);
    }

    @PostMapping("/query")
    public UserResponse queryUser(@RequestBody User user) {
        return userService.query(user);
    }
}
