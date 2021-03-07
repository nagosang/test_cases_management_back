package com.testCaseManagement.tcm.controller;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.entity.Project;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.ProjectService;
import com.testCaseManagement.tcm.service.TokenService;
import com.testCaseManagement.tcm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.desktop.SystemSleepEvent;
import java.util.HashMap;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

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

    @UserLoginToken
    @GetMapping(value = "/getProjectInfo")
    public R getProjectInfo(@Param("projectId") String projectId) {
        try{
            return R.ok().put("data", projectService.getProjectInfo(projectId));
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/updateProjectInfo")
    public R updateProjectInfo(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> projectInfo){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String errMsg = "";
            if(role.contains("1") || (belongGroupId.equals(projectInfo.get("manageGroupId")) && (role.contains("3") || role.contains("4")))){
                if(projectService.updateProjectInfo(projectInfo)){
                    return R.ok();
                }
                else {
                    return R.error("更新失败");
                }
            }
            else if (!belongGroupId.equals(projectInfo.get("manageGroupId"))){
                errMsg="你是不该组成员，不能更新项目信息";
            }
            else if (!role.contains("4")) {
                errMsg="你不是该项目的开发人员，不能更新项目信息";
            }
            else if (!role.contains("3")) {
                errMsg="你不是该项目管理员，不能更新项目信息";
            }
            else {
                errMsg="你不是管理员，不能更新项目信息";
            }
            return R.error(errMsg);
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }
}
