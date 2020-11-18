package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.annotation.KongLog;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Route;
import com.kongx.serve.entity.gateway.RouteParams;
import com.kongx.serve.entity.gateway.Service;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.gateway.RouteService;
import com.kongx.serve.service.gateway.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController("/RouteController")
@RequestMapping("/kong/api/")
public class RouteController extends BaseController {
    private static final String ROUTE_URI = "/routes";
    private static final String ROUTE_URI_ID_PATH = "/routes/{id}";
    private static final String ROUTE_PLUGIN_URI_ID_PATH = "/plugins/{pluginId}/route";
    private static final String ROUTE_SERVICE_URI_PATH = "/services/{serviceId}/routes";
    private static final String ROUTE_HOSTS_URI_PATH = "/routes/hosts";
    private static final String ROUTE_SERVICE_LIST_URI_PATH = "/services/{serviceId}/routes/list";

    @Autowired
    private RouteService kongFeignService;


    /**
     * 查询所有route
     *
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = ROUTE_URI, method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Route> routeKongEntity = kongFeignService.findAll(systemProfile(userInfo));
            jsonHeaderWrapper.setData(routeKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 查询所有route
     *
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = ROUTE_SERVICE_LIST_URI_PATH, method = RequestMethod.POST)
    public JsonHeaderWrapper findAllRoutesByService(UserInfo userInfo, @PathVariable String serviceId, @RequestBody SystemProfile systemProfile) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            SystemProfile activeClient = this.systemProfile(userInfo);
            KongEntity<Route> routeKongEntity = this.kongFeignService.findAllByService(systemProfile.IS_NULL() ? activeClient : systemProfile, serviceId);
            jsonHeaderWrapper.setData(routeKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @Autowired
    private ServiceService serviceService;

    /**
     * 批量更新路由Hosts
     *
     * @return
     */
    @RequestMapping(value = {ROUTE_HOSTS_URI_PATH}, method = RequestMethod.POST)
    public JsonHeaderWrapper batchUpdateRouteDomain(UserInfo userInfo, @RequestBody RouteParams routeParams) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            SystemProfile systemProfile = this.systemProfile(userInfo);
            KongEntity<Route> routeKongEntity = this.kongFeignService.findAll(systemProfile);
            if (routeParams.getService() != null) {
                Service service = this.serviceService.find(systemProfile, routeParams.getService().getId());
                routeKongEntity = this.kongFeignService.findAllByService(systemProfile, service.getName());
            }
            StringBuilder routeNames = new StringBuilder();
            for (Route datum : routeKongEntity.getData()) {
                datum.setHosts(routeParams.getHosts());
                this.kongFeignService.update(systemProfile, datum.getId(), datum);
            }
            jsonHeaderWrapper.setErrmsg(routeNames.toString());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 新增Route
     *
     * @param route
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = ROUTE_SERVICE_URI_PATH, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.ROUTE, content = "#route")
    public JsonHeaderWrapper add(UserInfo userInfo, @PathVariable String serviceId, @RequestBody Route route) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Route> routeKongEntity = this.kongFeignService.add(systemProfile(userInfo), serviceId, route.clear());
            jsonHeaderWrapper.setData(routeKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 更新Route
     *
     * @param id
     * @param route
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = ROUTE_URI_ID_PATH, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.ROUTE, content = "#route")
    public JsonHeaderWrapper update(UserInfo userInfo, @PathVariable String id, @RequestBody Route route) throws URISyntaxException {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Route results = this.kongFeignService.update(systemProfile(userInfo), id, route.clear());
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 删除Route
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = ROUTE_URI_ID_PATH, method = RequestMethod.DELETE)
    @KongLog(target = OperationLog.OperationTarget.ROUTE, content = "#id")
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String id) throws URISyntaxException {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Route> routeKongEntity = this.kongFeignService.remove(systemProfile(userInfo), id);
            jsonHeaderWrapper.setData(routeKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 查询单个route的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = ROUTE_URI_ID_PATH, method = RequestMethod.GET)
    public JsonHeaderWrapper find(UserInfo userInfo, @PathVariable String id) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Route results = this.kongFeignService.find(systemProfile(userInfo), id);
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 通过插件查询单个route的信息
     *
     * @param pluginId
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = ROUTE_PLUGIN_URI_ID_PATH, method = RequestMethod.GET)
    public JsonHeaderWrapper findByPlugin(UserInfo userInfo, @PathVariable String pluginId) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Route results = this.kongFeignService.findByPlugin(systemProfile(userInfo), pluginId);
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }
}
