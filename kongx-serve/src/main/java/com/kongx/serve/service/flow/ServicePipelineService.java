package com.kongx.serve.service.flow;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.entity.flow.ServicePipeline;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.mapper.ServicePipelineMapper;
import com.kongx.serve.service.IBaseService;
import com.kongx.serve.service.system.SystemProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("servicePipelineService")
public class ServicePipelineService implements IBaseService<ServicePipeline, Integer> {

    @Autowired
    private ServicePipelineMapper servicePipelineMapper;
    @Autowired
    private SystemProfileService systemProfileService;

    public List<ServicePipeline> findAll(SystemProfile systemProfile, ServicePipeline entity) {
        return this.servicePipelineMapper.findAll(systemProfile, entity);
    }

    @Override
    public void add(ServicePipeline entity, UserInfo userInfo) {
        entity.setCreator(userInfo.getName());
        entity.setCreate_at(new Date());
        entity.setProfile(systemProfileService.getClientByName(userInfo.getUserId()).getProfile());
        this.servicePipelineMapper.insert(entity);
    }

    @Override
    public void update(ServicePipeline entity, UserInfo userInfo) {
        entity.setModifier(userInfo.getName());
        entity.setModify_at(new Date());
        this.servicePipelineMapper.update(entity);
    }

    @Override
    public void remove(int pk) {
        ServicePipeline servicePipeline = this.servicePipelineMapper.findById(pk);
        if (servicePipeline.getNodeList().isEmpty()) {

            this.servicePipelineMapper.deleteById(pk);
        } else {
            throw new RuntimeException("请删除相关配置后再执行本操作");

        }
    }

    @Override
    public ServicePipeline findById(Integer id) {
        ServicePipeline servicePipeline = this.servicePipelineMapper.findById(id);
        return servicePipeline;
    }
}
