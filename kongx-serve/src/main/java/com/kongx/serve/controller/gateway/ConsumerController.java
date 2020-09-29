package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.Consumer;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.service.gateway.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController("consumerController")
@RequestMapping("/kong/api/")
@Slf4j
public class ConsumerController extends BaseController {
    private static final String CONSUMER_URI = "/consumers";
    private static final String CONSUMER_URI_ID = "/consumers/{id}";

    @Autowired
    private ConsumerService consumerService;

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

    /**
     * 新增upstream
     *
     * @param consumer
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = CONSUMER_URI, method = RequestMethod.POST)
    public JsonHeaderWrapper addUpstream(UserInfo userInfo, @RequestBody Consumer consumer) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Consumer results = this.consumerService.add(systemProfile(userInfo), consumer.trim());
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_ADD, OperationLog.OperationTarget.UPSTREAM, consumer.getUsername());
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
            this.log(userInfo, OperationLog.OperationType.OPERATION_UPDATE, OperationLog.OperationTarget.UPSTREAM, consumer, consumer.getUsername());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 删除consumer
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = CONSUMER_URI_ID, method = RequestMethod.DELETE)
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String id) throws Exception {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Consumer consumer = this.consumerService.findConsumer(systemProfile(userInfo), id);
            KongEntity<Consumer> upstreamKongEntity = this.consumerService.remove(systemProfile(userInfo), id);
            this.log(userInfo, OperationLog.OperationType.OPERATION_DELETE, OperationLog.OperationTarget.UPSTREAM, consumer);
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
