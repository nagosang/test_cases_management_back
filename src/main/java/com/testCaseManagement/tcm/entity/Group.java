package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("Group_info")
public class Group {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "groupId")
    private String groupId;

    @TableField(value = "groupName")
    private String groupName;

    @TableField(value = "leaderId")
    private String leaderId;
}
