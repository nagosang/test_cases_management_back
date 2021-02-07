package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.Project;
import com.testCaseManagement.tcm.mapper.ProjectMapper;
import com.testCaseManagement.tcm.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public JSONObject[] getProjectListByGroup(String groupId){
        try {
            QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<Project>();
            projectQueryWrapper.eq("GroupId",groupId);
            ArrayList<Project> projectList = new ArrayList<Project>(projectMapper.selectList(projectQueryWrapper));
            if (projectList.size()==0){
                JSONObject tempProject = new JSONObject();
                tempProject.put("label","暂无改组项目");
                tempProject.put("leaf",true);
                JSONObject[] listByGroup = new JSONObject[1];
                listByGroup[0] = tempProject;
                return  listByGroup;
            }
            else {
                JSONObject[] listByGroup = new JSONObject[projectList.size()];
                for (int i=0;i<projectList.size();i++){
                    JSONObject tempProject = new JSONObject();
                    tempProject.put("projectId", projectList.get(i).getProjectId());
                    tempProject.put("label", projectList.get(i).getProjectName());
                    tempProject.put("leaf", false);
                    listByGroup[i] = tempProject;
                }
                return listByGroup;
            }
        }
        catch (Exception e){
            throw e;
        }
    }
}
