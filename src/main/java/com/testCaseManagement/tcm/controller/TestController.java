package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.TestService;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    TestService testService;
    @GetMapping(value = "/test")
    public R test(){
        try {
            return R.ok("success");
        }
        catch (Exception e) {
            System.out.println("!!"+e.toString());
            return R.error().put("errMsg", e.toString());
        }
    }

    @PostMapping(value = "/test2")
    public R test2(HttpServletRequest httpServletRequest, @RequestParam HashMap<String, String> bodyData){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            System.out.println(token);
            testService.test2();
            return R.ok("success").put("bodyData", bodyData);
        }
        catch (Exception e) {
            System.out.println("!!"+e.toString());
            return R.error().put("errMsg", e.toString());
        }
    }
}
