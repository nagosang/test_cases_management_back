package com.testCaseManagement.tcm.service;

import com.alibaba.fastjson.JSONObject;

public interface InterfaceService {
    JSONObject getInterface(String ProjectId, int belongId);
}
