package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/test2")
    public R test2(){
        try {
            testService.test();
            return R.ok("success2");
        }
        catch (Exception e) {
            System.out.println("!!"+e.toString());
            return R.error().put("errMsg", e.toString());
        }
    }
}
