package com.testCaseManagement.tcm.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.testCaseManagement.tcm.entity.User;
import com.testCaseManagement.tcm.mapper.UserMapper;
import com.testCaseManagement.tcm.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    UserMapper userMapper;

    @Override
    public String getToken(User user) {
        String token="";

        Date date = new Date();
        long tokenKey = date.getTime();
        user.setTokenKey(tokenKey);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("tokenKey", tokenKey).eq("userId", user.getUserId());
        userMapper.update(user, userUpdateWrapper);
        date.setTime(tokenKey);
        token= JWT.create().withAudience(user.getUserId()) // 荷载用户ID
                .withIssuedAt(date) // token生成ID
                .sign(Algorithm.HMAC256(Long.toString(tokenKey))); // tokenKey
        return token;
    }

//    @Override
//    public boolean checkTokenLimitTime(){
//        return true;
//    }

    @Override
    public String getUserIdByToken(String token){
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("{'code':400, 'msg':'错误的token，请重新登录'}");
        }
        return userId;
    }
}
