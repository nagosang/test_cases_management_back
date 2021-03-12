package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public interface InterfaceService {
    JSONObject[] getInterfaceList(String ProjectId, int belongId);
    JSONObject getInterfaceInfo(int interfaceId);
    Boolean updateInterfaceInfo(HashMap<String, String> newInterfaceInfo);
    Boolean addInterface(HashMap<String, String> newInterface);
    Boolean deleteInterface(Integer interfaceId);
    String getManageGroupIdByInterfaceId(Integer interfaceId);
}
