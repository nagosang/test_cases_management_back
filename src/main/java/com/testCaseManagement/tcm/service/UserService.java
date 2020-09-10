package com.testCaseManagement.tcm.service;

import com.testCaseManagement.tcm.entity.User;

public interface UserService {
    Boolean Login(User user);
    String GetRole(String userName);
}
