package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.Group;
import com.testCaseManagement.tcm.mapper.GroupMapper;
import com.testCaseManagement.tcm.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public JSONObject[] getGroupListByAdmin(){
        try {
            QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
            ArrayList<Group> groupList = new ArrayList<>(groupMapper.selectList(groupQueryWrapper));
            JSONObject[] listByAdmin = new JSONObject[groupList.size()];
            for(int i=0;i<groupList.size();i++){
                JSONObject tempGroup = new JSONObject();
                tempGroup.put("GroupId", groupList.get(i).getGroupId());
                tempGroup.put("label",groupList.get(i).getGroupName());
                listByAdmin[i] = tempGroup;
            }
            return listByAdmin;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
