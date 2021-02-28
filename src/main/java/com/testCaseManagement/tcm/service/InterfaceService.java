package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;

public interface InterfaceService {
    JSONObject[] getInterfaceList(String ProjectId, int belongId);
    JSONObject getInterfaceInfo(int interfaceId);
}
