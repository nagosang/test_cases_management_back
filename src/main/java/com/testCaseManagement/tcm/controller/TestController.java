package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.mybeans.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping(value = "/test")
    public R test(){
        return R.ok();
    }
}
