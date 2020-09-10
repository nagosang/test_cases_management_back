package com.testCaseManagement.tcm.service;

import com.testCaseManagement.tcm.entity.User;

public interface TokenService {
    String getToken(User user);
    boolean checkTekonLimitTime();
}
