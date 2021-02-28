package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;

public interface TestCaseListService {
    JSONObject[] getTestCaseLIst(String projectId, int belongId);
}
