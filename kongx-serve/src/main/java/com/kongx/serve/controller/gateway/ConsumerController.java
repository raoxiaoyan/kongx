package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.Consumer;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.PluginVO;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.ConsumerService;
import com.kongx.serve.service.gateway.PluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Map;

@RestController("consumerController")
@RequestMapping("/kong/api/")
@Slf4j
public class ConsumerController extends BaseController {
    private static final String CONSUMER_URI = "/consumers";
    private static final String CONSUMER_URI_ID = "/consumers/{id}";
    private static final String CONSUMER_URI_plugins = "/consumers/{customerId}/plugins";
    private static final String CREDENTIALS_URI = "/consumers/{customerId}/{entityName}";
    private static final String CREDENTIALS_URI_ID = "/consumers/{customerId}/{entityName}/{entityId}";

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private PluginService pluginService;

    /**
     * 查询所有upstream
     *
     * @return
     */
    @RequestMapping(value = CONSUMER_URI, method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Consumer> upstreamKongEntity = consumerService.findAll(systemProfile(userInfo));
            jsonHeaderWrapper.setData(upstreamKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = CONSUMER_URI_plugins, method = {RequestMethod.POST})
    public JsonHeaderWrapper findAllPlugin(UserInfo userInfo, @PathVariable String customerId, @RequestBody SystemProfile systemProfile) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            SystemProfile activeClient = this.systemProfile(userInfo);
            KongEntity<PluginVO> pluginVOKongEntity = pluginService.findAllByConsumer(systemProfile.IS_NULL() ? activeClient : systemProfile, customerId);
            jsonHeaderWrapper.setData(pluginVOKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }


    @RequestMapping(value = CREDENTIALS_URI, method = RequestMethod.GET)
    public JsonHeaderWrapper findAllCredentials(UserInfo userInfo, @PathVariable String customerId, @PathVariable String entityName) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Map> upstreamKongEntity = consumerService.findAllCredentials(systemProfile(userInfo), customerId, entityName);
            jsonHeaderWrapper.setData(upstreamKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = CREDENTIALS_URI, method = RequestMethod.POST)
    public JsonHeaderWrapper addCredentials(UserInfo userInfo, @RequestBody Map map, @PathVariable String customerId, @PathVariable String entityName) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Map results = this.consumerService.addCredentials(systemProfile(userInfo), map, customerId, entityName);
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_ADD, OperationLog.OperationTarget.CONSUMERS, map);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 新增upstream
     *
     * @param consumer
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = CONSUMER_URI, method = RequestMethod.POST)
    public JsonHeaderWrapper add(UserInfo userInfo, @RequestBody Consumer consumer) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Consumer results = this.consumerService.add(systemProfile(userInfo), consumer.trim());
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_ADD, OperationLog.OperationTarget.CONSUMERS, consumer.getUsername());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 更新consumer
     *
     * @param id
     * @param consumer
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = CONSUMER_URI_ID, method = RequestMethod.POST)
    public JsonHeaderWrapper update(UserInfo userInfo, @PathVariable String id, @RequestBody Consumer consumer) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Consumer results = this.consumerService.update(systemProfile(userInfo), id, consumer.trim());
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_UPDATE, OperationLog.OperationTarget.CONSUMERS, consumer, consumer.getUsername());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 删除credential
     *
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = CREDENTIALS_URI_ID, method = RequestMethod.DELETE)
    public JsonHeaderWrapper removeCredential(UserInfo userInfo, @PathVariable String customerId, @PathVariable String entityName,
                                              @PathVariable String entityId) throws Exception {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Map> upstreamKongEntity =
                    this.consumerService.removeCredentials(systemProfile(userInfo), customerId, entityName, entityId);
            this.log(userInfo, OperationLog.OperationType.OPERATION_DELETE, OperationLog.OperationTarget.CONSUMERS, entityId);
            jsonHeaderWrapper.setData(upstreamKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = CONSUMER_URI_ID, method = RequestMethod.DELETE)
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String id) throws Exception {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Consumer consumer = this.consumerService.findConsumer(systemProfile(userInfo), id);
            KongEntity<Consumer> upstreamKongEntity = this.consumerService.remove(systemProfile(userInfo), id);
            this.log(userInfo, OperationLog.OperationType.OPERATION_DELETE, OperationLog.OperationTarget.CONSUMERS, consumer);
            jsonHeaderWrapper.setData(upstreamKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 查询单个consumer的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = CONSUMER_URI_ID, method = RequestMethod.GET)
    public JsonHeaderWrapper findUpstream(UserInfo userInfo, @PathVariable String id) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Consumer results = this.consumerService.findConsumer(systemProfile(userInfo), id);
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }
}
