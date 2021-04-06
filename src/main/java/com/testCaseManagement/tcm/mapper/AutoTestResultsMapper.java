package com.testCaseManagement.tcm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testCaseManagement.tcm.entity.AutoTestResults;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface AutoTestResultsMapper extends BaseMapper<AutoTestResults> {
    @Select("SELECT * FROM `auto_test_results` WHERE auto_test_results.interfaceId = #{interfaceId} LIMIT #{pageNumber},10")
    List<HashMap<String, Object>> queryTestResultsByPage(int interfaceId, int pageNumber);
}
