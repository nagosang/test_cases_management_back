package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("test_case_list")
public class TestCaseList {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "projectId")
    private String projectId;

    @TableField(value = "testCaseId")
    private String testCaseId;

    @TableField(value = "testCaseName")
    private String testCaseName;

    @TableField(value = "testCaseType")
    private int testCaseType;

    @TableField(value = "belong")
    private int belong;

    @TableField(value = "isTestCase")
    private int isTestCase;
}
