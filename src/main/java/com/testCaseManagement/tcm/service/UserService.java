package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.User;

import java.util.List;

public interface UserService {
    boolean Login(User user);
    String GetRole(String userId);
    User findByUserId(String userId);
    JSONObject getInfo(String userId);
    List getGroupMember(String groupId);
}
