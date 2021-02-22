package com.testCaseManagement.tcm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("interface_info")
public class Interface {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "interfaceName")
    private String interfaceName;

    @TableField(value = "interfaceMethod")
    private String interfaceMethod;

    @TableField(value = "interfaceInfo")
    private String interfaceInfo;

    @TableField(value = "interfaceRoute")
    private String interfaceRoute;

    @TableField(value = "belong")
    private int belong;

    @TableField(value = "projectId")
    private String projectId;

    @TableField(value = "isInterface")
    private int isInterface;
}
