package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.TestCaseList;
import com.testCaseManagement.tcm.mapper.TestCaseLIstMapper;
import com.testCaseManagement.tcm.service.TestCaseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TestCaseListImpl implements TestCaseListService {
    @Autowired
    TestCaseLIstMapper testCaseLIstMapper;

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
}
