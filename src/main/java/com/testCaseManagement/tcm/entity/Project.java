package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Value;

@Data
@TableName("project_info")
public class Project {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "projectId")
    private  String projectId;

    @TableField(value = "projectName")
    private  String projectName;

    @TableField(value = "projectInfo")
    private String projectInfo;

    @TableField(value = "manageGroupId")
    private  String manageGroupId;

    @TableField(value = "projectAddress")
    private String projectAddress;

    @TableField(value = "databaseAddress")
    private String databaseAddress;

}
