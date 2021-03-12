package com.testCaseManagement.tcm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testCaseManagement.tcm.entity.Interface;
import org.apache.ibatis.annotations.Select;

public interface InterfaceMapper extends BaseMapper<Interface> {
    @Select("SELECT p.manageGroupId FROM interface_info i, project_info p WHERE i.projectId = p.projectId AND i.id = #{interfaceId}")
    String selectManageGroupIdByInterfaceId(Integer interfaceId);
}
