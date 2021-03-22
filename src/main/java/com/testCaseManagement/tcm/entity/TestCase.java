package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "test_case")
public class TestCase {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "testCaseId")
    private int testCaseId;

    @TableField(value = "stepNo")
    private int stepNo;

    @TableField(value = "stepInfo")
    private String stepInfo;

    @TableField(value = "stepParam")
    private String stepParam;

    @TableField(value = "expect")
    private String expect;
}
