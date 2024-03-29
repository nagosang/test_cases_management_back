package com.testCaseManagement.tcm.controller;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mapper.InterfaceMapper;
import com.testCaseManagement.tcm.mapper.ProjectMapper;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.InterfaceService;
import com.testCaseManagement.tcm.service.ProjectService;
import com.testCaseManagement.tcm.service.TokenService;
import com.testCaseManagement.tcm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class InterfaceController {
    @Autowired
    InterfaceService interfaceService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    InterfaceMapper interfaceMapper;

    @UserLoginToken
    @GetMapping(value = "/getInterfaceList")
    public R getInterfaceList(@Param("projectId") String projectId, @Param("belongId") Integer belongId){
        try {
            return R.ok().put("data",interfaceService.getInterfaceList(projectId, belongId));
        }
        catch (Exception e){
            return R.error().put("errMsg", e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/getInterfaceInfo")
    public R getInterfaceInfo(@Param("interfaceId") Integer interfaceId){
        try {
            return R.ok().put("data", interfaceService.getInterfaceInfo(interfaceId));
        }
        catch (Exception e){
            return R.error().put("errMsg", e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/updateInterfaceInfo")
    public R updateInterfaceInfo(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> newInterfaceInfo) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(newInterfaceInfo.get("projectId"));
            String errMsg = "";
            if(role.contains("1") || (belongGroupId.equals(manageGroupId) && (role.contains("3") || role.contains("4")))){
                if(interfaceService.updateInterfaceInfo(newInterfaceInfo)){
                    return R.ok();
                }
                else {
                    return R.error("更新失败");
                }
            }
            else if (!belongGroupId.equals(manageGroupId)){
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
        catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/deleteInterface")
    public R deleteInterface(HttpServletRequest httpServletRequest, @Param("interfaceId") Integer interfaceId, @Param("projectId") String projectId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(projectId);
            String errMsg = "";
            if(role.contains("1") || (belongGroupId.equals(manageGroupId) && (role.contains("3") || role.contains("4")))){
                if(interfaceService.deleteInterface(interfaceId)){
                    return R.ok();
                }
                else {
                    return R.error("删除失败");
                }
            }
            else if (!belongGroupId.equals(manageGroupId)){
                errMsg="你是不该组成员，不能删除项目";
            }
            else if (!role.contains("4")) {
                errMsg="你不是该项目的开发人员，不能删除项目";
            }
            else if (!role.contains("3")) {
                errMsg="你不是该项目管理员，不能删除项目";
            }
            else {
                errMsg="你不是管理员，不能删除项目";
            }
            return R.error(errMsg);
        }
        catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/addInterface")
    public R addInterface(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> newInterfaceInfo) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(newInterfaceInfo.get("projectId"));
            String errMsg = "";
            if(role.contains("1") || (belongGroupId.equals(manageGroupId) && (role.contains("3") || role.contains("4")))){
                if(interfaceService.addInterface(newInterfaceInfo)){
                    return R.ok();
                }
                else {
                    return R.error("添加失败");
                }
            }
            else if (!belongGroupId.equals(manageGroupId)){
                errMsg="你是不该组成员，不能添加项目信息";
            }
            else if (!role.contains("4")) {
                errMsg="你不是该项目的开发人员，不能添加项目信息";
            }
            else if (!role.contains("3")) {
                errMsg="你不是该项目管理员，不能添加项目信息";
            }
            else {
                errMsg="你不是管理员，不能添加项目信息";
            }
            return R.error(errMsg);
        }
        catch (Exception e) {
            return R.error(e.toString());
        }
    }
}
