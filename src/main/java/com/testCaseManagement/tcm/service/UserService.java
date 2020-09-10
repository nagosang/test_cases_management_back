package com.testCaseManagement.tcm.service;

import com.testCaseManagement.tcm.entity.User;

public interface UserService {
    boolean Login(User user);
    String GetRole(String userName);
    User findByUserId(String userId);
}
