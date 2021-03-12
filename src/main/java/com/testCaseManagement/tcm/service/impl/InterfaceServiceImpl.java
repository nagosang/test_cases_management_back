package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.Interface;
import com.testCaseManagement.tcm.entity.Parameter;
import com.testCaseManagement.tcm.mapper.InterfaceMapper;
import com.testCaseManagement.tcm.mapper.ParameterMapper;
import com.testCaseManagement.tcm.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class InterfaceServiceImpl implements InterfaceService {
    @Autowired
    private InterfaceMapper interfaceMapper;

    @Autowired
    private ParameterMapper parameterMapper;

    @Override
    public JSONObject[] getInterfaceList(String ProjectId, int belongId){
        try {
            QueryWrapper<Interface> interfaceQueryWrapper = new QueryWrapper<>();
            interfaceQueryWrapper.eq("projectId", ProjectId).eq("belong", belongId).orderByAsc("isInterface");
            ArrayList<Interface> interfacesList = new ArrayList<>(interfaceMapper.selectList(interfaceQueryWrapper));
            if (interfacesList.size()==0){
                JSONObject tempProject = new JSONObject();
                tempProject.put("label","暂无内容");
                tempProject.put("leaf",true);
                JSONObject[] i_list = new JSONObject[1];
                i_list[0] = tempProject;
                return  i_list;
            }
            else {
                JSONObject[] list = new JSONObject[interfacesList.size()];
                for(int i=0;i<interfacesList.size();i++){
                    JSONObject tempInterface = new JSONObject();
                    tempInterface.put("interfaceId", interfacesList.get(i).getId());
                    tempInterface.put("label",interfacesList.get(i).getInterfaceName());
                    tempInterface.put("projectId",interfacesList.get(i).getProjectId());
                    if(interfacesList.get(i).getIsInterface() == 1){
                        tempInterface.put("leaf", true);
                    }
                    else {
                        tempInterface.put("leaf", false);
                    }
                    list[i] = tempInterface;
                }
                return list;
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public JSONObject getInterfaceInfo(int interfaceId) {
        try {
            Interface tempInterface = interfaceMapper.selectById(interfaceId);
            JSONObject data = new JSONObject();
            data.put("interfaceId", tempInterface.getId());
            data.put("projectId", tempInterface.getProjectId());
            data.put("interfaceName", tempInterface.getInterfaceName());
            data.put("interfaceMethod", tempInterface.getInterfaceMethod() == null ? "" :tempInterface.getInterfaceMethod());
            data.put("interfaceInfo", tempInterface.getInterfaceInfo() == null ? "" :tempInterface.getInterfaceInfo());
            data.put("interfaceRoute", tempInterface.getInterfaceRoute() == null ? "" :tempInterface.getInterfaceRoute());
            JSONObject reObj = new JSONObject();
            reObj.put("data", data);
            QueryWrapper<Parameter> parameterQueryWrapper = new QueryWrapper<>();
            parameterQueryWrapper.eq("interfaceId", interfaceId);
            ArrayList<Parameter> parameterArrayList = new ArrayList<>(parameterMapper.selectList(parameterQueryWrapper));
            if (parameterArrayList.size() == 0){
                reObj.put("count", 0);
                return reObj;
            }
            else {
                reObj.put("count", parameterArrayList.size());
                reObj.put("paramData", parameterArrayList);
                return reObj;
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean updateInterfaceInfo(HashMap<String, String> newInterfaceInfo) {
        try {
            Interface newInterface = interfaceMapper.selectById(newInterfaceInfo.get("interfaceId"));
            newInterface.setInterfaceName(newInterfaceInfo.get("modifyInterfaceName"));
            newInterface.setInterfaceMethod(newInterfaceInfo.get("modifyInterfaceMethod"));
            newInterface.setInterfaceRoute(newInterfaceInfo.get("modifyInterfaceRoute"));
            newInterface.setInterfaceInfo(newInterfaceInfo.get("modifyInterfaceInfo"));
            return interfaceMapper.updateById(newInterface)>0;
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean addInterface(HashMap<String, String> newInterfaceInfo){
        try {
            Interface newInterface = new Interface();
            newInterface.setInterfaceName(newInterfaceInfo.get("interfaceName"));
            newInterface.setProjectId(newInterfaceInfo.get("projectId"));
            newInterface.setBelong(Integer.parseInt(newInterfaceInfo.get("belongId")));
            if (newInterfaceInfo.get("isInterface").equals("true")) {
                newInterface.setIsInterface(1);
                newInterface.setInterfaceMethod(newInterfaceInfo.get("interfaceMethod"));
                newInterface.setInterfaceRoute(newInterfaceInfo.get("interfaceRoute"));
                newInterface.setInterfaceInfo(newInterfaceInfo.get("interfaceInfo"));
            }
            else {
                newInterface.setIsInterface(0);
            }
            return interfaceMapper.insert(newInterface)>0;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteInterface(Integer interfaceId) {
        try{
            return interfaceMapper.deleteById(interfaceId)>0;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public String getManageGroupIdByInterfaceId(Integer interfaceId) {
        try {
            return interfaceMapper.selectManageGroupIdByInterfaceId(interfaceId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
