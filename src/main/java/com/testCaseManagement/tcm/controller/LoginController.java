package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.TokenService;
import com.testCaseManagement.tcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/login")
    public R login(@RequestBody User user){
        if (userService.Login((user))) {
            String token = tokenService.getToken(user);
            String role = userService.getRole(user.getUserId());
            return R.ok().put("token", token).put("role", role);
        }
        else {
            return R.error("error");
        }
    }
}
