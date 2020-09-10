package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public R login(@RequestBody User user){
        if (userService.Login((user))) {
            return R.ok();
        }
        else {
            return R.error("error");
        }
    }
}
