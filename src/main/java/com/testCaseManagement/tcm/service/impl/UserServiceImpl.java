package com.testCaseManagement.tcm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mapper.UserMapper;
import com.testCaseManagement.tcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean Login(User user){
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
    public String GetRole(String userName){
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userId", userName);
            if (userMapper.selectOne(userQueryWrapper) == null){
               return "0";
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
}
