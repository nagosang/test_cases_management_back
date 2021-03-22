package com.testCaseManagement.tcm.controller;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.entity.TestCase;
import com.testCaseManagement.tcm.entity.TestCaseList;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.ProjectService;
import com.testCaseManagement.tcm.service.TestCaseListService;
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
import java.util.List;

@RestController
public class TestCaseController {
    @Autowired
    TestCaseListService testCaseListService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @UserLoginToken
    @GetMapping(value = "/getTestCaseList")
    public R getTestCaseList(@Param("projectId") String projectId, @Param("belongId") Integer belongId) {
        try {
            return R.ok().put("data", testCaseListService.getTestCaseLIst(projectId, belongId));
        } catch (Exception e) {
            return R.error().put("errMsg", e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/getTestCaseInfo")
    public R getTestCaseInfo(@Param("testCaseId") Integer testCaseId) {
        try {
            return R.ok().put("data", testCaseListService.getTestCaseInfo(testCaseId));
        } catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/updateTestCaseStep")
    public R updateTestCaseStep(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> newTestCaseStep, @Param("projectId") String projectId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(projectId);
            String errMsg = "";
            if (role.contains("1") || (belongGroupId.equals(manageGroupId) && role.contains("3")) || (belongGroupId.equals(manageGroupId) && role.contains("5"))) {
                if (testCaseListService.updateTestCaseStep(newTestCaseStep, userId)) {
                    return R.ok();
                } else {
                    return R.error("修改失败");
                }
            } else if (!belongGroupId.equals(manageGroupId)) {
                errMsg = "你是不该组成员，不能修改用例信息";
            } else if (!role.contains("5")) {
                errMsg = "你不是该项目的测试人员，不能修改用例信息";
            } else if (!role.contains("3")) {
                errMsg = "你不是该项目管理员，不能修改用例信息";
            } else {
                errMsg = "你不是管理员，不能修改用例信息";
            }
            return R.error(errMsg);
        } catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/createTestCaseStep")
    public R createTestCaseStep(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> newTestCaseStep, @Param("projectId") String projectId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(projectId);
            String errMsg = "";
            if (role.contains("1") || (belongGroupId.equals(manageGroupId) && role.contains("3")) || (belongGroupId.equals(manageGroupId) && role.contains("5"))) {
                if (testCaseListService.createTestCaseStep(newTestCaseStep, userId)) {
                    return R.ok();
                } else {
                    return R.error("修改失败");
                }
            } else if (!belongGroupId.equals(manageGroupId)) {
                errMsg = "你是不该组成员，不能修改用例信息";
            } else if (!role.contains("5")) {
                errMsg = "你不是该项目的测试人员，不能修改用例信息";
            } else if (!role.contains("3")) {
                errMsg = "你不是该项目管理员，不能修改用例信息";
            } else {
                errMsg = "你不是管理员，不能修改用例信息";
            }
            return R.error(errMsg);
        } catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/deleteTestCaseStep")
    public R deleteTestStep(HttpServletRequest httpServletRequest, @Param("testCaseStepId") Integer testCaseStepId, @Param("projectId") String projectId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(projectId);
            String errMsg = "";
            if (role.contains("1") || (belongGroupId.equals(manageGroupId) && role.contains("3")) || (belongGroupId.equals(manageGroupId) && role.contains("5"))) {
                if (testCaseListService.deleteTestCaseStep(testCaseStepId, userId)) {
                    return R.ok();
                } else {
                    return R.error("修改失败");
                }
            } else if (!belongGroupId.equals(manageGroupId)) {
                errMsg = "你是不该组成员，不能修改用例信息";
            } else if (!role.contains("5")) {
                errMsg = "你不是该项目的测试人员，不能修改用例信息";
            } else if (!role.contains("3")) {
                errMsg = "你不是该项目管理员，不能修改用例信息";
            } else {
                errMsg = "你不是管理员，不能修改用例信息";
            }
            return R.error(errMsg);
        } catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/changeTestCaseStepNo")
    public R changeTestCaseStepNo(HttpServletRequest httpServletRequest, @RequestBody List<TestCase> stepList, @Param("projectId") String projectId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(projectId);
            String errMsg = "";
            if (role.contains("1") || (belongGroupId.equals(manageGroupId) && role.contains("3")) || (belongGroupId.equals(manageGroupId) && role.contains("5"))) {
                if (testCaseListService.changeTestCaseStepNo(stepList, userId)) {
                    return R.ok();
                } else {
                    return R.error("修改失败");
                }
            } else if (!belongGroupId.equals(manageGroupId)) {
                errMsg = "你是不该组成员，不能修改用例信息";
            } else if (!role.contains("5")) {
                errMsg = "你不是该项目的测试人员，不能修改用例信息";
            } else if (!role.contains("3")) {
                errMsg = "你不是该项目管理员，不能修改用例信息";
            } else {
                errMsg = "你不是管理员，不能修改用例信息";
            }
            return R.error(errMsg);
        } catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/createTestCase")
    public R createTestCase(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> testCase){
        try{
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(testCase.get("projectId"));
            String errMsg = "";
            if (role.contains("1") || (belongGroupId.equals(manageGroupId) && role.contains("3")) || (belongGroupId.equals(manageGroupId) && role.contains("5"))) {
                TestCaseList testCaseList = new TestCaseList();
                testCaseList.setProjectId(testCase.get("projectId"));
                testCaseList.setTestCaseName(testCase.get("testCaseName"));
                testCaseList.setTestCaseType(testCase.get("testCaseType").equals("true")?1:0);
                testCaseList.setBelong(Integer.parseInt(testCase.get("belong")));
                testCaseList.setIsTestCase(testCase.get("isTestCase").equals("true")?1:0);

                if (testCaseListService.createTestCase(testCaseList, userId)) {
                    return R.ok();
                } else {
                    return R.error("新增失败");
                }
            } else if (!belongGroupId.equals(manageGroupId)) {
                errMsg = "你是不该组成员，不能新增用例";
            } else if (!role.contains("5")) {
                errMsg = "你不是该项目的测试人员，不能新增用例";
            } else if (!role.contains("3")) {
                errMsg = "你不是该项目管理员，不能新增用例";
            } else {
                errMsg = "你不是管理员，不能新增用例";
            }
            return R.error(errMsg);
        } catch (Exception e) {
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/deleteTestCase")
    public R deleteTestCase(HttpServletRequest httpServletRequest, @Param("testCaseId") Integer testCaseId, @Param("projectId") String projectId){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            String manageGroupId = projectService.getGroupIdByProjectId(projectId);
            String errMsg = "";
            if (role.contains("1") || (belongGroupId.equals(manageGroupId) && role.contains("3")) || (belongGroupId.equals(manageGroupId) && role.contains("5"))) {
                if (testCaseListService.deleteTestCase(testCaseId)) {
                    return R.ok();
                } else {
                    return R.error("删除失败");
                }
            } else if (!belongGroupId.equals(manageGroupId)) {
                errMsg = "你是不该组成员，不能删除用例";
            } else if (!role.contains("5")) {
                errMsg = "你不是该项目的测试人员，不能删除用例";
            } else if (!role.contains("3")) {
                errMsg = "你不是该项目管理员，不能删除用例";
            } else {
                errMsg = "你不是管理员，不能删除用例";
            }
            return R.error(errMsg);
        } catch (Exception e) {
            return R.error(e.toString());
        }
    }
}
