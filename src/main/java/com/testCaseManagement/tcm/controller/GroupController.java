package com.testCaseManagement.tcm.controller;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.annotation.UserLoginToken;
import com.testCaseManagement.tcm.entity.Group;
import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mybeans.R;
import com.testCaseManagement.tcm.service.GroupService;
import com.testCaseManagement.tcm.service.ProjectService;
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
public class GroupController {
    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TokenService tokenService;

    @UserLoginToken
    @GetMapping(value = "/getGroupList")
    public R getGroupList(HttpServletRequest httpServletRequest){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            String role = userService.getRole(userId);
            if (role.indexOf("1") != -1){
                return R.ok().put("data",groupService.getGroupListByAdmin());
            }
            return R.ok();
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/createGroup")
    public R createGroup(HttpServletRequest httpServletRequest, @RequestBody HashMap<String, String> groupInfo){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            String role = userService.getRole(userId);
            if (role.indexOf("1") == -1){
                return R.error("你不是管理员，没有权限创建组");
            }
            else {
                if(groupService.createGroup(groupInfo.get("groupName"), groupInfo.get("leaderAlternateId"))) {
                    return R.ok();
                }
                else {
                    return R.error();
                }
            }
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/deleteGroup")
    public R deleteGroup(HttpServletRequest httpServletRequest, @Param("groupId") String groupId) {
        try {
            if (projectService.getProjectNumberByGroup(groupId)>0){
                return R.error("解散失败，该组有负责的项目进行中");
            }
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            if(role.indexOf("1") != -1|| ( role.indexOf("3") != -1 && belongGroupId.equals(groupId) )){
                if (groupService.deleteGroup(groupId)){
                    return R.ok();
                }
                else {
                    return R.error("删除失败");
                }
            }
            else {
                return R.error("你不是管理员，没有权限解散组");
            }

        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/groupAddMember")
    public R groupAddMember(HttpServletRequest httpServletRequest,@Param("memberId") String memberId,@Param("groupId") String groupId){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            if(role.indexOf("1") != -1|| ( role.indexOf("3") != -1 && belongGroupId.equals(groupId) )){
                if (groupService.groupAddMember(memberId, groupId)){
                    return R.ok();
                }
                else{
                    return R.error("添加失败");
                }
            }
            else {
                return R.error("你不是管理员，没有权限添加成员");
            }

        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/groupChangeLeader")
    public R groupChangeLeader(HttpServletRequest httpServletRequest,@Param("groupId") String groupId, @Param("newLeaderId") String newLeaderId){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            if(role.indexOf("1") != -1|| ( role.indexOf("3") != -1 && belongGroupId.equals(groupId) )){
                if (groupService.groupChangeLeader(newLeaderId, groupId)){
                    return R.ok();
                }
                else{
                    return R.error("变更失败");
                }
            }
            else {
                return R.error("你不是管理员，没有权限变更组长");
            }

        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @PostMapping(value = "/removeMember")
    public R  removeMember(HttpServletRequest httpServletRequest, @Param("groupId") String groupId, @Param("memberId") String memberId) {
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            JSONObject currentUser = userService.getInfo(userId);
            String role = currentUser.get("role").toString();
            String belongGroupId = currentUser.get("belongGroupId").toString();
            if(role.indexOf("1") != -1|| ( role.indexOf("3") != -1 && belongGroupId.equals(groupId) )){
                if (groupService.removeMember(memberId)){
                    return R.ok();
                }
                else{
                    return R.error("移除失败");
                }
            }
            else if (belongGroupId.equals(groupId)){
                if (groupService.removeMember(memberId)){
                    return R.ok();
                }
                else{
                    return R.error("退出失败");
                }
            }
            else {
                return R.error("你不是管理员，没有权限移除组员");
            }

        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }

    @UserLoginToken
    @GetMapping(value = "/getGroupListForChange")
    public R getGroupListForChange(HttpServletRequest httpServletRequest){
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String userId = tokenService.getUserIdByToken(token);
            return R.ok().put("data", groupService.getGroupListForChange(userId));
        }
        catch (Exception e){
            return R.error(e.toString());
        }
    }
}
