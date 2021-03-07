package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mapper.UserMapper;
import com.testCaseManagement.tcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean Login(User user){
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userId", user.getUserId());
            if (userMapper.selectOne(userQueryWrapper) == null){
                return false;
            }
            if (userMapper.selectOne(userQueryWrapper).getPassword().equals(user.getPassword())){
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
           throw e;
        }
    }

    @Override
    public String getRole(String userId){
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userId", userId);
            if (userMapper.selectOne(userQueryWrapper) == null){
               return null;
            }
            else {
                return userMapper.selectOne(userQueryWrapper).getRole();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User findByUserId(String userId){
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userId", userId);
            if (userMapper.selectOne(userQueryWrapper) == null) {
                return null;
            } else {
                return userMapper.selectOne(userQueryWrapper);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public JSONObject getInfo(String userId){
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userId", userId);
            if (userMapper.selectOne(userQueryWrapper) == null) {
                throw new RuntimeException("{'code':400, 'msg':'用户不存在，请重新登录'}");
            } else {
                User user = userMapper.selectOne(userQueryWrapper);
                JSONObject userInfo = new JSONObject();
                userInfo.put("userId", user.getUserId());
                userInfo.put("userName", user.getUserName());
                userInfo.put("role", user.getRole());
                userInfo.put("belongGroupId",user.getBelongGroupId());
                return userInfo;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List getGroupMember(String groupId){
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("belongGroupId", groupId);
            return userMapper.selectList(userQueryWrapper);
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public JSONObject[] getNoGroupUser(){
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("belongGroupId","");
            ArrayList<User> userArrayList = new ArrayList<>(userMapper.selectList(userQueryWrapper));
            JSONObject[] reObj= new JSONObject[userArrayList.size()];
            for (int i=0;i<userArrayList.size();i++){
                JSONObject temp = new JSONObject();
                temp.put("value", userArrayList.get(i).getUserName());
                temp.put("userId",userArrayList.get(i).getUserId());
                reObj[i] = temp;
            }

            return reObj;
        }
        catch (Exception e){
            throw e;
        }
    }

}
