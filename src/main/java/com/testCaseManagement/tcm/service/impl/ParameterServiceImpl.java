package com.testCaseManagement.tcm.service.impl;

import com.testCaseManagement.tcm.entity.Parameter;
import com.testCaseManagement.tcm.mapper.ParameterMapper;
import com.testCaseManagement.tcm.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ParameterServiceImpl implements ParameterService {
    @Autowired
    ParameterMapper parameterMapper;

    @Override
    public Boolean addParameter(HashMap<String, String> newParameter, Integer interfaceId) {
        try{
            Parameter parameter = new Parameter();
            parameter.setParameterName(newParameter.get("parameterName"));
            parameter.setParameterType(newParameter.get("parameterType"));
            parameter.setExample(newParameter.get("example"));
            parameter.setInterfaceId(interfaceId);
            parameter.setType(Integer.parseInt(newParameter.get("type")));

            return parameterMapper.insert(parameter)>0;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteParameter(Integer parameterId){
        try {
            return parameterMapper.deleteById(parameterId)>0;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean updateParameter(HashMap<String, String> newParameter, Integer interfaceId) {
        try {
            Parameter parameter = new Parameter();
            parameter.setId(Integer.parseInt(newParameter.get("id")));
            parameter.setParameterName(newParameter.get("modifyParameterName"));
            parameter.setParameterType(newParameter.get("modifyParameterType"));
            parameter.setExample(newParameter.get("modifyExample"));
            parameter.setInterfaceId(interfaceId);
            parameter.setType(Integer.parseInt(newParameter.get("type")));
            return parameterMapper.updateById(parameter) > 0;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
