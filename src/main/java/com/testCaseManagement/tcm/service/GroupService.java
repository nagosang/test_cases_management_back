package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.Group;

import java.util.List;

public interface GroupService {
    JSONObject[] getGroupListByAdmin();
}
