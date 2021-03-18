package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public interface TestCaseListService {
    JSONObject[] getTestCaseLIst(String projectId, int belongId);
    JSONObject getTestCaseInfo(int testCaseId);
    Boolean updateTestCaseStep(HashMap<String, String> newTestCaseStep);
}
