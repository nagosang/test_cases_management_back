package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.TokenService;
import com.testCaseManagement.tcm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

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

    @UserLoginToken
    @GetMapping(value = "/getUserInfo")
    public R getUserInfo(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // System.out.println(token);
        String userId = tokenService.getUserIdByToken(token);
        // System.out.println(userId);
        try {
            return R.ok().put("userInfo", userService.getInfo(userId));
        }
        catch (Exception e) {
            return R.error();
        }

    }
}
