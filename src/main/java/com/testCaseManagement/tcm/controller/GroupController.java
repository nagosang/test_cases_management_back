package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.GroupService;
import com.testCaseManagement.tcm.service.TokenService;
import com.testCaseManagement.tcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GroupController {
    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @Autowired
    TokenService tokenService;

    @UserLoginToken
    @GetMapping(value = "/getGroupList")
    public R getGroupList(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        String userId = tokenService.getUserIdByToken(token);
        String isAdmin = userService.GetRole(userId);
        if (isAdmin.equals("admin")){
            return R.ok().put("data",groupService.getGroupListByAdmin());
        }
        return R.ok();
    }
}
