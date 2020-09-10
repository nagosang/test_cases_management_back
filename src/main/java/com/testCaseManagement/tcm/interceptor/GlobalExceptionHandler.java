package com.testCaseManagement.tcm.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        String msg = e.getMessage();
        try {
            JSONObject msgJSON = new JSONObject();
            msgJSON = JSON.parseObject(msg);
            return msgJSON;
        }
        catch (Exception ee){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", -1);
            jsonObject.put("message", "未知错误："+msg+"请联系系统管理员");
            return jsonObject;
        }
    }
}
