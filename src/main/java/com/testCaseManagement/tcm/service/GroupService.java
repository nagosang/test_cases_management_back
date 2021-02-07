package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.Group;

public interface GroupService {
    JSONObject[] getGroupListByAdmin();
}
