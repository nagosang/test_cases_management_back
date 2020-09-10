package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "userId")
    private String userId;

    @TableField(value = "userName")
    private String userName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "role")
    private String role;

    @TableField(value = "tokenKey")
    private Long tokenKey;

    @TableField(value = "head")
    private String head;
}
