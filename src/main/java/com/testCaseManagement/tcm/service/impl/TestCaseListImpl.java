package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.testCaseManagement.tcm.entity.TestCase;
import com.testCaseManagement.tcm.entity.TestCaseList;
import com.testCaseManagement.tcm.mapper.TestCaseLIstMapper;
import com.testCaseManagement.tcm.mapper.TestCaseMapper;
import com.testCaseManagement.tcm.service.TestCaseListService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    public Boolean updateTestCaseStep(HashMap<String, String> newTestCaseStep , String userId) {
        try {
            TestCase testCaseStep = new TestCase();
            testCaseStep.setId(Integer.parseInt(newTestCaseStep.get("id")));
            testCaseStep.setStepInfo(newTestCaseStep.get("modifyStepInfo"));
            testCaseStep.setStepParam(newTestCaseStep.get("modifyStepParam"));
            testCaseStep.setExpect(newTestCaseStep.get("modifyExpect"));
            testCaseStep.setStepNo(Integer.parseInt(newTestCaseStep.get("stepNo")));
            testCaseStep.setTestCaseId(Integer.parseInt(newTestCaseStep.get("testCaseId")));
            if (testCaseMapper.updateById(testCaseStep) > 0) {
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                TestCaseList newTestCaseList = testCaseLIstMapper.selectById(Integer.parseInt(newTestCaseStep.get("testCaseId")));
                newTestCaseList.setLastModifyTime(ft.format(dNow));
                newTestCaseList.setLastModifyUserId(userId);
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

    @Override
    public Boolean createTestCaseStep(HashMap<String, String> newTestCaseStep, String userId){
        try {
            TestCase testCaseStep = new TestCase();
            testCaseStep.setStepInfo(newTestCaseStep.get("modifyStepInfo"));
            testCaseStep.setStepParam(newTestCaseStep.get("modifyStepParam"));
            testCaseStep.setExpect(newTestCaseStep.get("modifyExpect"));
            testCaseStep.setStepNo(Integer.parseInt(newTestCaseStep.get("stepNo")));
            testCaseStep.setTestCaseId(Integer.parseInt(newTestCaseStep.get("testCaseId")));
            if (testCaseMapper.insert(testCaseStep)> 0) {
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                TestCaseList newTestCaseList = testCaseLIstMapper.selectById(Integer.parseInt(newTestCaseStep.get("testCaseId")));
                newTestCaseList.setLastModifyTime(ft.format(dNow));
                newTestCaseList.setLastModifyUserId(userId);
                if (testCaseLIstMapper.updateById(newTestCaseList) > 0) {
                    return true;
                }
                else{
                    new Exception("新建信息失败");
                    return false;
                }
            }
            else {
                new Exception("新建失败");
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteTestCaseStep(Integer stepId, String userId){
        try {
            QueryWrapper<TestCase> testCaseQueryWrapper1 = new QueryWrapper<>();
            testCaseQueryWrapper1.eq("id", stepId);
            int testCaseId = testCaseMapper.selectOne(testCaseQueryWrapper1).getTestCaseId();
            if (testCaseMapper.delete(testCaseQueryWrapper1)>0){
                QueryWrapper<TestCase> testCaseQueryWrapper2 = new QueryWrapper<>();
                testCaseQueryWrapper2.eq("testCaseId", testCaseId).orderByAsc("stepNo");
                ArrayList<TestCase> stepList = new ArrayList<>(testCaseMapper.selectList(testCaseQueryWrapper2));
                for (int i=0;i<stepList.size();i++) {
                    stepList.get(i).setStepNo(i+1);
                    if (testCaseMapper.updateById(stepList.get(i))>0){
                        continue;
                    }
                    else {
                        new Exception("步骤顺序重排错误");
                        return false;
                    }
                }
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                TestCaseList newTestCaseList = testCaseLIstMapper.selectById(testCaseId);
                newTestCaseList.setLastModifyTime(ft.format(dNow));
                newTestCaseList.setLastModifyUserId(userId);
                testCaseLIstMapper.updateById(newTestCaseList);
                return true;
            }
            else {
                new Exception("删除失败");
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean changeTestCaseStepNo(List<TestCase> testCaseStepList, String userId){
        try {
            for (int i = 0;i<testCaseStepList.size();i++) {
                if(testCaseMapper.updateById(testCaseStepList.get(i)) > 0) {
                    continue;
                }
                else {
                    new Exception("顺序变换错误");
                    return false;
                }
            }
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            TestCaseList newTestCaseList = testCaseLIstMapper.selectById(testCaseStepList.get(0).getTestCaseId());
            newTestCaseList.setLastModifyTime(ft.format(dNow));
            newTestCaseList.setLastModifyUserId(userId);
            testCaseLIstMapper.updateById(newTestCaseList);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean createTestCase(TestCaseList newTestCase, String userId){
        try {
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            newTestCase.setCreateUserId(userId);
            newTestCase.setLastModifyUserId(userId);
            newTestCase.setLastModifyTime(ft.format(dNow));
            return testCaseLIstMapper.insert(newTestCase)>0;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteTestCase(Integer testCaseId) {
        try {
            return testCaseLIstMapper.deleteById(testCaseId)>0;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
