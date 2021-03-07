package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.Project;

import java.util.HashMap;
import java.util.List;

public interface ProjectService {
    JSONObject[] getProjectListByGroup(String GroupId);
    JSONObject[] getProjectListByUser(String UserId);
    int getProjectNumberByGroup(String GroupId);
    HashMap<String, Object> getProjectInfo(String projectId);
    Boolean updateProjectInfo(HashMap<String, String> newProject) throws Exception;
}
