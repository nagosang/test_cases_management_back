package com.testCaseManagement.tcm.service;

import java.util.HashMap;

public interface ParameterService {
    Boolean addParameter(HashMap<String, String> newParameter, Integer interfaceId);
    Boolean deleteParameter(Integer parameterId);
    Boolean updateParameter(HashMap<String, String> newParameter, Integer interfaceId);
}
