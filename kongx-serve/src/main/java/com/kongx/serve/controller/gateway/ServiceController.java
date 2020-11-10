package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.PluginVO;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.PluginService;
import com.kongx.serve.service.gateway.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController("/ServiceController")
@RequestMapping("/kong/api/")
public class ServiceController extends BaseController {
    private static final String SERVICE_URI = "/services";
    private static final String SERVICE_URI_ID_PATH = "/services/{id}";
    private static final String SERVICE_URI_ID_PLUGIN_PATH = "/services/{serviceId}/list/plugins";
    private static final String SERVICE_ROUTE_URI_ID_PATH = "/routes/{routeId}/service";
    private static final String SERVICE_PLUGIN_URI_ID_PATH = "/plugins/{pluginId}/service";

    @Autowired
    private ServiceService kongFeignService;

    @Autowired
    private PluginService pluginService;

    /**
     * 查询所有SERVICE
     *
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SERVICE_URI, method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Service> upstreamKongEntity = kongFeignService.findAll(systemProfile(userInfo));
            jsonHeaderWrapper.setData(upstreamKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = SERVICE_URI_ID_PLUGIN_PATH, method = {RequestMethod.POST})
    public JsonHeaderWrapper findAllPlugin(UserInfo userInfo, @PathVariable String serviceId, @RequestBody SystemProfile systemProfile) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            SystemProfile activeClient = this.systemProfile(userInfo);
            KongEntity<PluginVO> pluginVOKongEntity = pluginService.findAllPluginByService(systemProfile.IS_NULL() ? activeClient : systemProfile, serviceId);
            jsonHeaderWrapper.setData(pluginVOKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 新增service
     *
     * @param service
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SERVICE_URI, method = RequestMethod.POST)
    public JsonHeaderWrapper add(UserInfo userInfo, @RequestBody Service service) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Service results = this.kongFeignService.add(systemProfile(userInfo), service.trim());
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_ADD, OperationLog.OperationTarget.SERVICE, results, results.getName());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 更新service
     *
     * @param id
     * @param service
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SERVICE_URI_ID_PATH, method = RequestMethod.POST)
    public JsonHeaderWrapper update(UserInfo userInfo, @PathVariable String id, @RequestBody Service service) throws URISyntaxException {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Service results = this.kongFeignService.update(systemProfile(userInfo), id, service.trim());
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_UPDATE, OperationLog.OperationTarget.SERVICE, results, results.getName());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 删除upstream
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SERVICE_URI_ID_PATH, method = RequestMethod.DELETE)
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String id) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Service service = this.kongFeignService.find(systemProfile(userInfo), id);
            KongEntity<Service> upstreamKongEntity = this.kongFeignService.remove(systemProfile(userInfo), id);
            jsonHeaderWrapper.setData(upstreamKongEntity.getData());
            this.log(userInfo, OperationLog.OperationType.OPERATION_DELETE, OperationLog.OperationTarget.SERVICE, service, service.getName());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;

    }

    /**
     * 查询单个upstream的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SERVICE_URI_ID_PATH, method = RequestMethod.GET)
    public Service find(UserInfo userInfo, @PathVariable String id) throws URISyntaxException {
        return this.kongFeignService.find(systemProfile(userInfo), id);
    }

    @RequestMapping(value = SERVICE_ROUTE_URI_ID_PATH, method = RequestMethod.GET)
    public Service findByRoute(UserInfo userInfo, @PathVariable String routeId) throws URISyntaxException {
        return this.kongFeignService.findByRoute(systemProfile(userInfo), routeId);
    }

    @RequestMapping(value = SERVICE_PLUGIN_URI_ID_PATH, method = RequestMethod.GET)
    public Service findByPlugin(UserInfo userInfo, @PathVariable String pluginId) throws URISyntaxException {
        return this.kongFeignService.findByPlugin(systemProfile(userInfo), pluginId);
    }
}
