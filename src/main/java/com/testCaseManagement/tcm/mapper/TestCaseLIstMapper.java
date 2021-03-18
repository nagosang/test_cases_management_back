package com.testCaseManagement.tcm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testCaseManagement.tcm.entity.TestCaseList;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

public interface TestCaseLIstMapper extends BaseMapper<TestCaseList> {
    @Select("SELECT " +
            "t.*, " +
            "temp1.createUserName, " +
            "temp2.lastModifyUserName " +
            " FROM " +
            "(SELECT t.createUserId, u.userName AS createUserName FROM test_case_list t, user u WHERE t.createUserId = u.userId AND t.id = #{testCaseId} AND t.isTestCase = 1) AS temp1, " +
            "(SELECT t.lastModifyUserId, u.userName AS lastModifyUserName FROM test_case_list t, user u WHERE t.lastModifyUserId = u.userId AND t.id = #{testCaseId} AND t.isTestCase = 1) AS temp2, " +
            "test_case_list t " +
            "WHERE t.createUserId = temp1.createUserId AND t.lastModifyUserId = temp2.lastModifyUserId AND t.id = #{testCaseId}")
    HashMap<String, Object> getTestCaseInfoById(Integer testCaseId);
}
