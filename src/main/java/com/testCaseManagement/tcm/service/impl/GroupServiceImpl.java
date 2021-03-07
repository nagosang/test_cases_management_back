package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.testCaseManagement.tcm.entity.Group;
import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mapper.GroupMapper;
import com.testCaseManagement.tcm.mapper.UserMapper;
import com.testCaseManagement.tcm.service.GroupService;
import com.testCaseManagement.tcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

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
                return userMapper.update(null, userUpdateWrapper)>0;
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
                    return userMapper.update(null,userUpdateWrapper1)>0;
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
            return userMapper.update(null,userUpdateWrapper)>0;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean groupChangeLeader(String newLeaderId, String groupId) throws Exception{
        try {
            QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
            groupQueryWrapper.eq("groupId", groupId);
            String oldLeaderId = groupMapper.selectOne(groupQueryWrapper).getLeaderId();
            if (oldLeaderId.equals(newLeaderId)) {
                throw new Exception("该用户已是该组领导");
            }
            QueryWrapper<User> getOldRoleWrapper = new QueryWrapper<>();
            getOldRoleWrapper.eq("userId", oldLeaderId);
            String oldRole = userMapper.selectOne(getOldRoleWrapper).getRole();
            oldRole = oldRole.replaceAll(",3","");

            QueryWrapper<User> grtNewRoleWrapper = new QueryWrapper<>();
            grtNewRoleWrapper.eq("userId", newLeaderId);
            String newRole = userMapper.selectOne(grtNewRoleWrapper).getRole();
            newRole = newRole.replaceAll(",3","")+",3";

            UpdateWrapper<User> oldLeaderWrapper = new UpdateWrapper<>();
            oldLeaderWrapper.eq("userId",oldLeaderId).set("role", oldRole);

            UpdateWrapper<User> newLeaderWrapper = new UpdateWrapper<>();
            newLeaderWrapper.eq("userId", newLeaderId).set("role", newRole);
            if (userMapper.update(null, oldLeaderWrapper) >0 && userMapper.update(null, newLeaderWrapper) > 0) {
                UpdateWrapper<Group> groupUpdateWrapper = new UpdateWrapper<>();
                groupUpdateWrapper.eq("groupId", groupId).set("leaderId", newLeaderId);
                return groupMapper.update(null, groupUpdateWrapper)>0;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean removeMember(String memberId) throws Exception{
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userId",memberId);
            if(userMapper.selectOne(userQueryWrapper).getRole().indexOf("3") != -1){
                throw new Exception("组长不可移除出组");
            }
            else {
                UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
                userUpdateWrapper.eq("userId", memberId).set("belongGroupId","");
                return userMapper.update(null,userUpdateWrapper)>0;
            }
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JSONObject[] getGroupListForChange(String userId) throws Exception{
        try {
            String role = userService.getRole(userId);
            if(role.contains("1")) {
                ArrayList<Group> groupArrayList = new ArrayList<>(groupMapper.selectList(null));
                JSONObject[] reList = new JSONObject[groupArrayList.size()];
                for(int i=0;i<groupArrayList.size();i++) {
                    JSONObject temp = new JSONObject();
                    temp.put("value", groupArrayList.get(i).getGroupId());
                    temp.put("label", groupArrayList.get(i).getGroupName());
                    reList[i] = temp;
                }
                return reList;
            }
            else if(role.contains("3")) {
                QueryWrapper<Group> groupQueryWrapper = new QueryWrapper<>();
                groupQueryWrapper.ne("groupId", "0001");
                ArrayList<Group> groupArrayList = new ArrayList<>(groupMapper.selectList(groupQueryWrapper));
                JSONObject[] reList = new JSONObject[groupArrayList.size()];
                for(int i=0;i<groupArrayList.size();i++) {
                    JSONObject temp = new JSONObject();
                    temp.put("value", groupArrayList.get(i).getGroupId());
                    temp.put("label", groupArrayList.get(i).getGroupName());
                    reList[i] = temp;
                }
                return reList;
            }
            else {
                throw new Exception("您不是管理员，不能修改项目信息");
            }
        }
        catch (Exception e){
            throw e;
        }
    }

}
