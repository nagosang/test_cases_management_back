package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.ProjectService;
import com.testCaseManagement.tcm.service.TokenService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.desktop.SystemSleepEvent;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    TokenService tokenService;

    @UserLoginToken
    @GetMapping(value = "/getProjectListByGroup")
    public R getProjectListByGroup(@Param(value = "groupId") String groupId) {
        return R.ok().put("data",projectService.getProjectListByGroup(groupId));
    }

    @UserLoginToken
    @GetMapping(value = "/getProjectListByUser")
    public R getProjectListByUser(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // System.out.println(token);
        String userId = tokenService.getUserIdByToken(token);
        return R.ok().put("data", projectService.getProjectListByUser(userId));
    }
}
