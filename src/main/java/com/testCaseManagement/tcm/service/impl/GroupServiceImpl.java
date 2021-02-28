package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.testCaseManagement.tcm.entity.Group;
import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mapper.GroupMapper;
import com.testCaseManagement.tcm.mapper.UserMapper;
import com.testCaseManagement.tcm.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

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

    @Override
    public Boolean createGroup(String groupName, String leaderId){
        try {
            Group newGroup = new Group();
            String newGroupId = "group" + System.currentTimeMillis();
            newGroup.setGroupId(newGroupId);
            newGroup.setGroupName(groupName);
            newGroup.setLeaderId(leaderId);
            if (groupMapper.insert(newGroup)==1) {
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("userId", leaderId);
                String newRole = userMapper.selectOne(userQueryWrapper).getRole().replaceAll(",3","")+ ",3";
                UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
                userUpdateWrapper.eq("userId", leaderId).set("role",newRole).set("belongGroupId", newGroupId);
                return true?userMapper.update(null, userUpdateWrapper)>=1:false;
            }
            return false;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean deleteGroup(String GroupId) {
        try {
            QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.eq("groupId",GroupId);
            String leaderId = groupMapper.selectOne(groupQueryWrapper).getLeaderId();
            if (groupMapper.delete(groupQueryWrapper) == 1){
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("userId",leaderId);
                String role = userMapper.selectOne(userQueryWrapper).getRole();

                UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
                userUpdateWrapper.eq("userId", leaderId).set("role", role.replaceAll(",3",""));
                if(userMapper.update(null, userUpdateWrapper ) == 1){
                    UpdateWrapper<User> userUpdateWrapper1 = new UpdateWrapper<>();
                    userUpdateWrapper1.eq("belongGroupId", GroupId).set("belongGroupId","");
                    return true?userMapper.update(null,userUpdateWrapper1)>=1:false;
                }
            }
            return false;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean groupAddMember(String memberId, String groupId){
        try {
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("userId", memberId).set("belongGroupId",groupId);
            return true?userMapper.update(null,userUpdateWrapper)>0:false;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean groupChangeLeader(String newLeaderId, String groupId){
        UpdateWrapper<User> userUpdateWrapper1 = new UpdateWrapper<>();
        return false;
    }

}
