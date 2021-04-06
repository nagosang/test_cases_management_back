package com.testCaseManagement.tcm.controller;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.AutoTestService;
import com.testCaseManagement.tcm.service.TokenService;
import com.testCaseManagement.tcm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class AutoTestController {
    @Autowired
    AutoTestService autoTestService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;


    @UserLoginToken
    @PostMapping(value = "/autoTest")
    public R autoTes(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, JSONObject[]> data, @Param("method") String method, @Param("interfaceId") Integer interfaceId){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            if (role.contains("5")){
                System.out.println(data);
                return R.ok().put("data", autoTestService.autoTest(data, method, userId, interfaceId));
            }
            else{
                return R.error("您不是测试人员");
            }
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/confirmTestResults")
    public R confirmTestResults(@Param("id") Integer id) {
        try {
            if (autoTestService.confirmTestResult(id)) {
                return R.ok();
            }
            else {
                return R.error("发生错误");
            }
        }
        catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/queryTestResultsByPage")
    public R queryTestResultsByPage(@Param("interfaceId") Integer interfaceId, @Param("page") Integer page) {
        try {
            return R.ok().put("data", autoTestService.getTestResultsList(interfaceId,(page-1)*10));
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }
}
