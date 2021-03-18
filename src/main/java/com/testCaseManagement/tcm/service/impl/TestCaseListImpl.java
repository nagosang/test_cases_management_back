package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.TestCase;
import com.testCaseManagement.tcm.entity.TestCaseList;
import com.testCaseManagement.tcm.mapper.TestCaseLIstMapper;
import com.testCaseManagement.tcm.mapper.TestCaseMapper;
import com.testCaseManagement.tcm.service.TestCaseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
public class TestCaseListImpl implements TestCaseListService {
    @Autowired
    TestCaseLIstMapper testCaseLIstMapper;

    @Autowired
    TestCaseMapper testCaseMapper;

    @Override
    public JSONObject[] getTestCaseLIst(String projectId, int belongId){
        QueryWrapper<TestCaseList> testCaseListQueryWrapper = new QueryWrapper<>();
        testCaseListQueryWrapper.eq("projectId", projectId).eq("belong", belongId).orderByAsc("isTestCase");
        ArrayList<TestCaseList> testCaseListArrayList = new ArrayList<>(testCaseLIstMapper.selectList(testCaseListQueryWrapper));
        if (testCaseListArrayList.size() == 0) {
            JSONObject temp = new JSONObject();
            temp.put("label","暂无内容");
            temp.put("leaf",true);
            JSONObject[] reObj = new JSONObject[1];
            reObj[0] = temp;
            return reObj;
        }
        else {
            JSONObject[] reObj = new JSONObject[testCaseListArrayList.size()];
            for(int i =0;i<testCaseListArrayList.size();i++){
                JSONObject temp = new JSONObject();
                temp.put("testCaseId", testCaseListArrayList.get(i).getId());
                temp.put("label", testCaseListArrayList.get(i).getTestCaseName());
                temp.put("projectId", testCaseListArrayList.get(i).getProjectId());
                temp.put("testCaseType", testCaseListArrayList.get(i).getTestCaseType());
                if(testCaseListArrayList.get(i).getIsTestCase() == 1){
                    temp.put("leaf", true);
                }
                else{
                    temp.put("leaf", false);
                }
                reObj[i] = temp;
            }
            return reObj;

        }
    }

    @Override
    public JSONObject getTestCaseInfo(int testCaseId){
        try {
            JSONObject reObj = new JSONObject();
            reObj.put("testCaseInfo", testCaseLIstMapper.getTestCaseInfoById(testCaseId));
            QueryWrapper<TestCase> testCaseQueryWrapper = new QueryWrapper<>();
            testCaseQueryWrapper.eq("testCaseId", testCaseId).orderByAsc("stepNo");
            reObj.put("testCase", testCaseMapper.selectList(testCaseQueryWrapper));
            return reObj;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean updateTestCaseStep(HashMap<String, String> newTestCaseStep) {
        try {
            TestCase testCaseStep = new TestCase();
            testCaseStep.setId(Integer.parseInt(newTestCaseStep.get("id")));
            testCaseStep.setStepInfo(newTestCaseStep.get("modifyStepInfo"));
            testCaseStep.setStepParam(newTestCaseStep.get("modifyStepParam"));
            testCaseStep.setExpect(newTestCaseStep.get("modifyExpect"));
            testCaseStep.setStepNo(Integer.parseInt(newTestCaseStep.get("stepNo")));
            testCaseStep.setTestCaseId(Integer.parseInt(newTestCaseStep.get("testCaseId")));
            testCaseStep.setInterfaceId(Integer.parseInt(newTestCaseStep.get("interfaceId")));
            if (testCaseMapper.updateById(testCaseStep) > 0) {
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
                TestCaseList newTestCaseList = testCaseLIstMapper.selectById(Integer.parseInt(newTestCaseStep.get("testCaseId")));
                newTestCaseList.setLastModifyTime(ft.format(dNow));
                if (testCaseLIstMapper.updateById(newTestCaseList) > 0) {
                    return true;
                }
                else{
                    new Exception("更新信息失败");
                    return false;
                }
            }
            else {
                new Exception("更新失败");
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
