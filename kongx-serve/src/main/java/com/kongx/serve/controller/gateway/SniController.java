package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.annotation.KongLog;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.gateway.Sni;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.service.gateway.SniService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController("sniController")
@RequestMapping("/kong/api/")
@Slf4j
public class SniController extends BaseController {
    private static final String SNIS_URI = "/snis";
    private static final String SNIS_URI_ID = "/snis/{id}";

    @Autowired
    private SniService sniService;

    /**
     * 查询所有sni
     *
     * @return
     */
    @RequestMapping(value = SNIS_URI, method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Sni> upstreamKongEntity = sniService.findAll(systemProfile(userInfo));
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
     * @param sni
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SNIS_URI, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.SNI, content = "#sni")
    public JsonHeaderWrapper add(UserInfo userInfo, @RequestBody Sni sni) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Sni results = this.sniService.add(systemProfile(userInfo), sni.trim());
            jsonHeaderWrapper.setData(results);
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
     * @param sni
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SNIS_URI_ID, method = RequestMethod.POST)
    @KongLog(target = OperationLog.OperationTarget.SNI, content = "#sni")
    public JsonHeaderWrapper update(UserInfo userInfo, @PathVariable String id, @RequestBody Sni sni) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Sni results = this.sniService.update(systemProfile(userInfo), id, sni.trim());
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 删除sni
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SNIS_URI_ID, method = RequestMethod.DELETE)
    @KongLog(target = OperationLog.OperationTarget.SNI, content = "#id")
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String id) throws Exception {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Sni> upstreamKongEntity = this.sniService.remove(systemProfile(userInfo), id);
            jsonHeaderWrapper.setData(upstreamKongEntity.getData());
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    /**
     * 查询单个sni的信息
     *
     * @param id
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = SNIS_URI_ID, method = RequestMethod.GET)
    public JsonHeaderWrapper findSni(UserInfo userInfo, @PathVariable String id) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Sni results = this.sniService.findEntity(systemProfile(userInfo), id);
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }
}
