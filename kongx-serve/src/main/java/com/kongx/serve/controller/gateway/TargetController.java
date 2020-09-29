package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Target;
import com.kongx.serve.entity.gateway.TargetHealth;
import com.kongx.serve.entity.gateway.Upstream;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.TargetService;
import com.kongx.serve.service.gateway.UpstreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController("/targetController")
@RequestMapping("/kong/api/")
@Import(FeignClientsConfiguration.class)
public class TargetController extends BaseController {
    private static final String TARGET_URI_PATH = "/upstreams/{id}/targets";
    private static final String TARGET_URI_HEALTH_PATH = "/upstreams/{id}/targets/health";
    private static final String TARGET_URI_ID_PATH = "/upstreams/{upstreamId}/targets/{id}";

    @Autowired
    private TargetService targetFeignService;

    /**
     * 查询upstream下的所有target
     *
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = TARGET_URI_HEALTH_PATH, method = RequestMethod.POST)
    public JsonHeaderWrapper findAll(UserInfo userInfo, @PathVariable String id, @RequestBody SystemProfile systemProfile) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            SystemProfile activeClient = this.systemProfile(userInfo);
            KongEntity<TargetHealth> results = this.targetFeignService.findAllHealth(systemProfile.IS_NULL() ? activeClient : systemProfile, id);
            jsonHeaderWrapper.setData(results.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 新增upstream
     *
     * @param target
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = TARGET_URI_PATH, method = RequestMethod.POST)
    public JsonHeaderWrapper add(UserInfo userInfo, @PathVariable String id, @RequestBody Target target) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            this.targetFeignService.add(systemProfile(userInfo), id, target);
            this.log(userInfo, OperationLog.OperationType.OPERATION_ADD, OperationLog.OperationTarget.TARGETS, target,
                    remark(userInfo, target, target.getUpstream().getId()));
        } catch (Exception e) {
            e.printStackTrace();
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 删除target
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = TARGET_URI_ID_PATH, method = RequestMethod.DELETE)
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String upstreamId, @PathVariable String id) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            Target target = this.targetFeignService.findById(systemProfile(userInfo), upstreamId, id);
            this.targetFeignService.remove(systemProfile(userInfo), upstreamId, id);
            this.log(userInfo, OperationLog.OperationType.OPERATION_DELETE, OperationLog.OperationTarget.TARGETS, target,
                    remark(userInfo, target, upstreamId));
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @Autowired
    private UpstreamService upstreamService;

    private String remark(UserInfo userInfo, Target target, String upstreamId) {
        Upstream upstream = null;
        String remark = "";
        try {
            upstream = this.upstreamService.findUpstream(systemProfile(userInfo), upstreamId);
            remark = String.format("'%s' 从属于上游服务 '%s'", target.getTarget(), upstream.getName());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return remark;
    }

}
