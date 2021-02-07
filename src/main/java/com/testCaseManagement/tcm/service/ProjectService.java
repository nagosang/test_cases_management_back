package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.Project;

public interface ProjectService {
    JSONObject[] getProjectListByGroup(String GroupId);
}
