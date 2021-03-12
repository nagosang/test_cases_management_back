package com.testCaseManagement.tcm.controller;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.InterfaceService;
import com.testCaseManagement.tcm.service.ParameterService;
import com.testCaseManagement.tcm.service.TokenService;
import com.testCaseManagement.tcm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.HashMap;

@RestController
public class ParameterController {
    @Autowired
    ParameterService parameterService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    InterfaceService interfaceService;

    @UserLoginToken
    @PostMapping(value = "/addParameter")
    public R addParameter(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> newParameter, @Param("interfaceId") Integer interfaceId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = interfaceService.getManageGroupIdByInterfaceId(interfaceId);
            String errMsg = "";
            if(role.contains("1") || (belongGroupId.equals(manageGroupId) && (role.contains("3") || role.contains("4")))){
                if(parameterService.addParameter(newParameter, interfaceId)){
                    return R.ok();
                }
                else {
                    return R.error("添加失败");
                }
            }
            else if (!belongGroupId.equals(manageGroupId)){
                errMsg="你是不该组成员，不能添加参数信息";
            }
            else if (!role.contains("4")) {
                errMsg="你不是该项目的开发人员，不能添加参数信息";
            }
            else if (!role.contains("3")) {
                errMsg="你不是该项目管理员，不能添加参数信息";
            }
            else {
                errMsg="你不是管理员，不能添加参数信息";
            }
            return R.error(errMsg);
        }
        catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/deleteParameter")
    public R deleteParameter(HttpServletRequest httpServletRequest, @Param("parameterId") Integer parameterId, @Param("interfaceId") Integer interfaceId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = interfaceService.getManageGroupIdByInterfaceId(interfaceId);
            String errMsg = "";
            if(role.contains("1") || (belongGroupId.equals(manageGroupId) && (role.contains("3") || role.contains("4")))){
                if(parameterService.deleteParameter(parameterId)){
                    return R.ok();
                }
                else {
                    return R.error("删除失败");
                }
            }
            else if (!belongGroupId.equals(manageGroupId)){
                errMsg="你是不该组成员，不能删除参数信息";
            }
            else if (!role.contains("4")) {
                errMsg="你不是该项目的开发人员，不能删除参数信息";
            }
            else if (!role.contains("3")) {
                errMsg="你不是该项目管理员，不能删除参数信息";
            }
            else {
                errMsg="你不是管理员，不能删除参数信息";
            }
            return R.error(errMsg);
        }
        catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/updateParameter")
    public R updateParameter(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> newParameter, @Param("interfaceId") Integer interfaceId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = interfaceService.getManageGroupIdByInterfaceId(interfaceId);
            String errMsg = "";
            if(role.contains("1") || (belongGroupId.equals(manageGroupId) && (role.contains("3") || role.contains("4")))){
                if(parameterService.updateParameter(newParameter, interfaceId)){
                    return R.ok();
                }
                else {
                    return R.error("修改失败");
                }
            }
            else if (!belongGroupId.equals(manageGroupId)){
                errMsg="你是不该组成员，不能修改参数信息";
            }
            else if (!role.contains("4")) {
                errMsg="你不是该项目的开发人员，不能修改参数信息";
            }
            else if (!role.contains("3")) {
                errMsg="你不是该项目管理员，不能修改参数信息";
            }
            else {
                errMsg="你不是管理员，不能修改参数信息";
            }
            return R.error(errMsg);
        }
        catch (Exception e) {
            return R.error(e.toString());
        }
    }
}
