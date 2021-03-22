package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.TestCase;
import com.testCaseManagement.tcm.entity.TestCaseList;

import java.util.HashMap;
import java.util.List;

public interface TestCaseListService {
    JSONObject[] getTestCaseLIst(String projectId, int belongId);
    JSONObject getTestCaseInfo(int testCaseId);
    Boolean updateTestCaseStep(HashMap<String, String> newTestCaseStep, String userId);
    Boolean createTestCaseStep(HashMap<String, String> newTestCaseStep, String userId);
    Boolean deleteTestCaseStep(Integer stepId, String userId);
    Boolean changeTestCaseStepNo(List<TestCase> testCaseStepList, String userId);
    Boolean createTestCase(TestCaseList newTestCase, String userId);
    Boolean deleteTestCase(Integer testCaseId);
}
