package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("parameter_info")
public class Parameter {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "parameterName")
    private String parameterName;

    @TableField(value = "parameterType")
    private String parameterType;

    @TableField(value = "example")
    private String example;

    @TableField(value = "interfaceId")
    private int interfaceId;

    @TableField(value = "type")
    private int type;
}
