package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;
import com.testCaseManagement.tcm.entity.Group;

import java.util.List;

public interface GroupService {
    JSONObject[] getGroupListByAdmin();
    Boolean createGroup(String groupName, String leaderId);
    Boolean deleteGroup(String GroupId);
    Boolean groupAddMember(String memberId, String groupId);
    Boolean groupChangeLeader(String newLeaderId, String groupId);
}
