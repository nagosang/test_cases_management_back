package com.testCaseManagement.tcm.controller;

import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.InterfaceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterfaceController {
    @Autowired
    InterfaceService interfaceService;

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
}
