package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.AutoTestResults;

import java.util.HashMap;
import java.util.List;

public interface AutoTestService {
    AutoTestResults autoTest(HashMap<String, JSONObject[]> data, String method, String userId, Integer interfaceId);
    Boolean confirmTestResult(int id);
    HashMap<String, Object> getTestResultsList(int interfaceId, int page);
}
