package com.kongx.serve.controller.flow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.common.utils.Jackson2Helper;
import com.kongx.serve.controller.system.DefaultController;
import com.kongx.serve.entity.flow.FlowNode;
import com.kongx.serve.entity.flow.NodeMeta;
import com.kongx.serve.entity.flow.ServicePipeline;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.TargetHealth;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.IBaseService;
import com.kongx.serve.service.flow.ServicePipelineService;
import com.kongx.serve.service.gateway.TargetService;
import com.kongx.serve.service.gateway.TruncateEntityService;
import com.kongx.serve.service.system.SystemProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/ServiceFlowController")
@RequestMapping("/kong/api/pipeline")
public class ServicePipelineController extends DefaultController<ServicePipeline, Integer> {

    @Autowired
    private TruncateEntityService truncateEntityService;
    @Autowired
    private ServicePipelineService servicePipelineService;

    @Autowired
    private TargetService targetService;

    @Autowired
    private SystemProfileService systemProfileService;

    @Override
    @Resource(name = "servicePipelineService")
    protected void setBaseService(IBaseService<ServicePipeline, Integer> iBaseService) {
        this.baseService = iBaseService;
    }

    @RequestMapping(value = "/list/profile", method = RequestMethod.GET)
    public List<ServicePipeline> findAll(UserInfo userInfo, ServicePipeline project) {
        return servicePipelineService.findAll(this.systemProfile(userInfo), project);
    }

    @RequestMapping(path = "/truncate/entity", method = RequestMethod.POST)
    public JsonHeaderWrapper<ServicePipeline> removeEntity(UserInfo userInfo, @RequestBody FlowNode flowNode) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            if (!flowNode.getMeta().isReady()) {
                jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
                jsonHeaderWrapper.setErrmsg("该实体未进行配置，请检查后再试!");
                return jsonHeaderWrapper;
            }
            this.truncateEntityService.remove(this.systemProfile(userInfo), this.wrapUri(flowNode));
        } catch (Exception e) {
            String content = e.getMessage();
            if (content.contains("Not found")) {
                jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.NOT_FOUND.getCode());
            } else {
                jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            }
            jsonHeaderWrapper.setErrmsg(content);
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public JsonHeaderWrapper findById(UserInfo userInfo, @PathVariable Integer id) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            ServicePipeline servicePipeline = this.servicePipelineService.findById(id);
            SystemProfile systemProfile = this.systemProfileService.findByProfile(servicePipeline.getProfile());
            List<FlowNode> flowNodeList = Jackson2Helper.jsonToList(Jackson2Helper.toJsonString(servicePipeline.getNodeList()), FlowNode.class);
            KongEntity<TargetHealth> kongEntity = null;
            for (FlowNode flowNode : flowNodeList) {
                if (flowNode.getMeta().isReady()) {
                    this.wrapFlowNode(systemProfile, flowNode, kongEntity);
                }
            }
            servicePipeline.setNodeList(flowNodeList);
            jsonHeaderWrapper.setData(servicePipeline);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    private FlowNode wrapFlowNode(SystemProfile systemProfile, FlowNode flowNode, KongEntity<TargetHealth> kongEntity) {
        try {
            if (flowNode.getMeta().getProp().equals("targets")) {
                if (kongEntity == null) {
                    kongEntity = Jackson2Helper.parsonObject(
                            Jackson2Helper.toJsonString(this.targetService.findAllHealth(systemProfile, flowNode.getMeta().getParent().getId())),
                            new TypeReference<KongEntity<TargetHealth>>() {
                            });
                }
                for (TargetHealth datum : kongEntity.getData()) {
                    if (datum.getId().equals(flowNode.getMeta().getId())) {
                        flowNode.getMeta().setEntity(Jackson2Helper.parsonObject(Jackson2Helper.toJsonString(datum), new TypeReference<Map>() {
                        }));
                        return flowNode;
                    }
                }
                flowNode.getMeta().setReady(false);

                Map parent = new HashMap();
                parent.put("id", flowNode.getMeta().getParent().getId());
                flowNode.getMeta().setEntity(parent);
            } else {
                String uri = this.wrapUri(flowNode);
                flowNode.getMeta().setEntity(this.truncateEntityService.findById(systemProfile, uri));
            }
        } catch (Exception e) {
            flowNode.getMeta().setReady(false);
            if (flowNode.getMeta().getParent() != null) {
                Map parent = new HashMap();
                parent.put("id", flowNode.getMeta().getParent().getId());
                flowNode.getMeta().setEntity(parent);
            }

        }
        return flowNode;
    }

    private String wrapUri(FlowNode flowNode) {
        String url = flowNode.getMeta().getProp();
        String id = flowNode.getMeta().getId();
        if ("targets".equals(url)) {
            NodeMeta parent = flowNode.getMeta().getParent();
            url = "/" + parent.getProp() + "/%s/" + url + "/%s";
            url = String.format(url, parent.getId(), id);
        } else {
            url = "/" + url + "/%s";
            url = String.format(url, id);
        }
        return url;
    }

    @RequestMapping(path = "/query/entity", method = RequestMethod.POST)
    public JsonHeaderWrapper<Map> findEntityById(UserInfo userInfo, @RequestBody FlowNode flowNode) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            if (!flowNode.getMeta().isReady()) {
                jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
                jsonHeaderWrapper.setErrmsg("该实体未进行配置，请检查后再试!");
                return jsonHeaderWrapper;
            }
            jsonHeaderWrapper.setData(this.truncateEntityService.findById(this.systemProfile(userInfo), this.wrapUri(flowNode)));
        } catch (Exception e) {
            String content = e.getMessage();
            if (content.contains("Not found")) {
                jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.NOT_FOUND.getCode());
                jsonHeaderWrapper.setErrmsg("该实体可能已被移除，请检查后再试!");
            } else {
                jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
                jsonHeaderWrapper.setErrmsg(content);
            }
        }
        return jsonHeaderWrapper;
    }

    @Override
    protected OperationLog.OperationTarget operationTarget() {
        return OperationLog.OperationTarget.SERVICE_PIPELINE;
    }
}
