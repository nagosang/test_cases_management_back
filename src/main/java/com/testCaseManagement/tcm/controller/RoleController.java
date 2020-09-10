package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/getRole")
    public R getRole(@Param("userId") String userId){
        String Role = userService.GetRole(userId);
        if (Role.equals("0")){
            return R.error("error");
        }
        else {
            return R.ok().put("role", Role);
        }
    }
}
