package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("auto_test_results")
public class AutoTestResults {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "interfaceId")
    private int interfaceId;

    @TableField(value = "method")
    private String method;

    @TableField(value = "statusCode")
    private String statusCode;

    @TableField(value = "statusCodeValue")
    private int statusCodeValue;

    @TableField(value = "headers")
    private String headers;

    @TableField(value = "body")
    private String body;

    @TableField(value = "testHeader")
    private String testHeader;

    @TableField(value = "testBody")
    private String testBody;

    @TableField(value = "testCookie")
    private String testCookie;

    @TableField(value = "date")
    private String date;

    @TableField(value = "testUser")
    private String testUser;

    @TableField(value = "isPass")
    private int isPass;
}
