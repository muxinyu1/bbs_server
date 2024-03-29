package com.mxy.bbs_server.controller;

import com.mxy.bbs_server.entity.UserInfoRequest;
import com.mxy.bbs_server.response.userinfo.UserInfoResponse;
import com.mxy.bbs_server.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/userInfo")
@CrossOrigin("*")
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/update")
    public UserInfoResponse update(UserInfoRequest userInfoRequest) throws IOException {
        return userInfoService.update(userInfoRequest);
    }

    @PostMapping("/query")
    public UserInfoResponse query(UserInfoRequest userInfoRequest) {
        return userInfoService.query(userInfoRequest);
    }

    @GetMapping("/query/{username}")
    public UserInfoResponse query(@PathVariable("username") String username) {
        return userInfoService.query(new UserInfoRequest(username, "", "", null));
    }
}
