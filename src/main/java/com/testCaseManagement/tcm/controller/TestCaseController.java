package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.TestCaseListService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCaseController {
    @Autowired
    TestCaseListService testCaseListService;

    @UserLoginToken
    @GetMapping(value = "/getTestCaseList")
    public R getTestCaseList(@Param("projectId") String projectId, @Param("belongId") Integer belongId){
        try {
            return R.ok().put("data",testCaseListService.getTestCaseLIst(projectId, belongId));
        }
        catch (Exception e) {
            return R.error().put("errMsg", e.toString());
        }
    }
}
