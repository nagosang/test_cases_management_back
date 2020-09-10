package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @UserLoginToken
    @PostMapping(value = "/test")
    public R test(){
        try {
            return R.ok();
        }
        catch (Exception e) {
            System.out.println("!!"+e.toString());
            return R.error().put("errMsg", e.toString());
        }
    }
}
