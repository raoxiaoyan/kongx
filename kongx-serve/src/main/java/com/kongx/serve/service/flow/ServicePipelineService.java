package com.kongx.serve.service.flow;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.entity.flow.ServicePipeline;
import com.kongx.serve.mapper.ServicePipelineMapper;
import com.kongx.serve.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("servicePipelineService")
public class ServicePipelineService implements IBaseService<ServicePipeline, Integer> {

    @Autowired
    private ServicePipelineMapper servicePipelineMapper;


    @Override
    public List<ServicePipeline> findAll(ServicePipeline entity) {
        return this.servicePipelineMapper.findAll(entity);
    }

    @Override
    public void add(ServicePipeline entity, UserInfo userInfo) {
        entity.setCreator(userInfo.getName());
        entity.setCreate_at(new Date());
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
        this.servicePipelineMapper.deleteById(pk);
    }

    @Override
    public ServicePipeline findById(Integer id) {
        return this.servicePipelineMapper.findById(id);
    }
}
