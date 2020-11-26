package com.kongx.serve.controller.flow;

import com.kongx.serve.controller.system.DefaultController;
import com.kongx.serve.entity.flow.ServicePipeline;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.service.IBaseService;
import com.kongx.serve.service.flow.ServicePipelineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("/ServiceFlowController")
@RequestMapping("/kong/api/pipeline")
public class ServicePipelineController extends DefaultController<ServicePipeline, Integer> {

    @Resource(name = "servicePipelineService")
    private ServicePipelineService servicePipelineService;

    @Override
    @Resource(name = "servicePipelineService")
    protected void setBaseService(IBaseService<ServicePipeline, Integer> iBaseService) {
        this.baseService = iBaseService;
    }

    @Override
    protected OperationLog.OperationTarget operationTarget() {
        return OperationLog.OperationTarget.SERVICE_PIPELINE;
    }
}
