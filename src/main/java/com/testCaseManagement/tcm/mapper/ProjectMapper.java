package com.testCaseManagement.tcm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testCaseManagement.tcm.entity.Project;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {
    @Select("SELECT p.projectId, p.projectName, p.manageGroupId, g.groupName AS manageGroupName, p.projectAddress, p.databaseAddress, p.projectInfo FROM project_info p, group_info g WHERE p.manageGroupId = g.groupId AND p.projectId = #{projectId}")
    HashMap<String, Object> selectProjectInfo(String projectId);
}
