package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.annotation.KongLog;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Plugin;
import com.kongx.serve.entity.gateway.PluginVO;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.service.gateway.PluginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Map;

@RestController("/PluginController")
@RequestMapping("/kong/api/")
@Import(FeignClientsConfiguration.class)
public class PluginController extends BaseController {
    private static final String PLUGIN_URI = "/plugins";
    private static final String PLUGIN_ROUTE_URI_PATH = "/routes/{routeId}/plugins";
    private static final String PLUGIN_SERVICE_URI_PATH = "/services/{serviceId}/plugins";
    private static final String PLUGIN_URI_ID_PATH = "/plugins/{id}";
    private static final String PLUGIN_URI_SCHEMA_PATH = "/plugins/schema/{name}";
    @Autowired
    private PluginService kongFeignService;


    /**
     * 查询所有plugin
     *
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = PLUGIN_URI, method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<PluginVO> pluginVOKongEntity = this.kongFeignService.findAll(systemProfile(userInfo));
            jsonHeaderWrapper.setData(pluginVOKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = PLUGIN_ROUTE_URI_PATH, method = RequestMethod.GET)
    public JsonHeaderWrapper findAllByRoute(UserInfo userInfo, @PathVariable String routeId) throws URISyntaxException {

        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<PluginVO> pluginVOKongEntity = this.kongFeignService.findAllByRoute(systemProfile(userInfo), routeId);
            jsonHeaderWrapper.setData(pluginVOKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = PLUGIN_SERVICE_URI_PATH, method = RequestMethod.GET)
    public JsonHeaderWrapper findAllByService(UserInfo userInfo, @PathVariable String serviceId) throws URISyntaxException {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<PluginVO> pluginVOKongEntity = this.kongFeignService.findAllByService(systemProfile(userInfo), serviceId);
            jsonHeaderWrapper.setData(pluginVOKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = PLUGIN_URI_SCHEMA_PATH, method = RequestMethod.GET)
    public Map findPluginSchema(UserInfo userInfo, @PathVariable String name) throws URISyntaxException {
        return this.kongFeignService.findPluginSchema(systemProfile(userInfo), name);

    }

    /**
     * 新增全局的plugin
     *
     * @param plugin
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = PLUGIN_URI, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.GLOBAL_PLUGIN, content = "#plugin")
    public JsonHeaderWrapper<Plugin> add(UserInfo userInfo, @RequestBody Plugin plugin) {
        JsonHeaderWrapper<Plugin> jsonHeaderWrapper = init();
        try {
            Plugin result = this.kongFeignService.add(systemProfile(userInfo), plugin);
            jsonHeaderWrapper.setData(result);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = PLUGIN_ROUTE_URI_PATH, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.ROUTE_PLUGIN, content = "#plugin")
    public JsonHeaderWrapper addByRoute(UserInfo userInfo, @PathVariable String routeId, @RequestBody Plugin plugin) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            this.kongFeignService.addByRoute(systemProfile(userInfo), routeId, plugin);
            KongEntity<PluginVO> pluginVOKongEntity = this.kongFeignService.findAllByRoute(systemProfile(userInfo), routeId);
            jsonHeaderWrapper.setData(pluginVOKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = PLUGIN_SERVICE_URI_PATH, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.SERVICE_PLUGIN, content = "#plugin")
    public JsonHeaderWrapper addByService(UserInfo userInfo, @PathVariable String routeId, @RequestBody Plugin plugin) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            this.kongFeignService.addByService(systemProfile(userInfo), routeId, plugin);
            KongEntity<PluginVO> pluginVOKongEntity = this.kongFeignService.findAllByService(systemProfile(userInfo), routeId);
            jsonHeaderWrapper.setData(pluginVOKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 更新Plugin
     *
     * @param id
     * @param plugin
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = PLUGIN_URI_ID_PATH, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.ROUTE_PLUGIN, content = "#plugin")
    public JsonHeaderWrapper<Plugin> update(UserInfo userInfo, @PathVariable String id, @RequestBody Plugin plugin) {
        JsonHeaderWrapper<Plugin> jsonHeaderWrapper = init();
        try {
            Plugin result = this.kongFeignService.update(systemProfile(userInfo), id, plugin);
            jsonHeaderWrapper.setData(result);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());

        }
        return jsonHeaderWrapper;
    }

    /**
     * 删除Plugin
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = PLUGIN_URI_ID_PATH, method = RequestMethod.DELETE)
    @KongLog(target = OperationLog.OperationTarget.GLOBAL_PLUGIN, content = "#id")
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String id) throws URISyntaxException {
        this.kongFeignService.remove(systemProfile(userInfo), id);
        return findAll(userInfo);
    }

    /**
     * 查询单个Plugin的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = PLUGIN_URI_ID_PATH, method = RequestMethod.GET)
    public Plugin find(UserInfo userInfo, @PathVariable String id) throws URISyntaxException {
        return this.kongFeignService.find(systemProfile(userInfo), id);
    }
}
