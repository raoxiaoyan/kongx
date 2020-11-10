package com.kongx.serve.controller.gateway;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.gateway.Certificate;
import com.kongx.serve.entity.gateway.KongEntity;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.service.gateway.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController("CertificateController")
@RequestMapping("/kong/api/")
@Slf4j
public class CertificateController extends BaseController {
    private static final String CERTIFICATES_URI = "/certificates";
    private static final String CERTIFICATES_URI_ID = "/certificates/{id}";

    @Autowired
    private CertificateService certificateService;

    /**
     * 查询所有sni
     *
     * @return
     */
    @RequestMapping(value = CERTIFICATES_URI, method = RequestMethod.GET)
    public JsonHeaderWrapper findAll(UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            KongEntity<Certificate> upstreamKongEntity = certificateService.findAll(systemProfile(userInfo));
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
    @RequestMapping(value = CERTIFICATES_URI, method = RequestMethod.POST)
    public JsonHeaderWrapper addUpstream(UserInfo userInfo, @RequestBody Certificate sni) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Certificate results = this.certificateService.add(systemProfile(userInfo), sni.trim());
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_ADD, OperationLog.OperationTarget.Certificate, sni);
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
    @RequestMapping(value = CERTIFICATES_URI_ID, method = RequestMethod.POST)
    public JsonHeaderWrapper update(UserInfo userInfo, @PathVariable String id, @RequestBody Certificate sni) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Certificate results = this.certificateService.update(systemProfile(userInfo), id, sni.trim());
            jsonHeaderWrapper.setData(results);
            this.log(userInfo, OperationLog.OperationType.OPERATION_UPDATE, OperationLog.OperationTarget.Certificate, sni, sni.getKey());
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
    @RequestMapping(value = CERTIFICATES_URI_ID, method = RequestMethod.DELETE)
    public JsonHeaderWrapper remove(UserInfo userInfo, @PathVariable String id) throws Exception {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Certificate sni = this.certificateService.findEntity(systemProfile(userInfo), id);
            KongEntity<Certificate> upstreamKongEntity = this.certificateService.remove(systemProfile(userInfo), id);
            this.log(userInfo, OperationLog.OperationType.OPERATION_DELETE, OperationLog.OperationTarget.Certificate, sni);
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
    @RequestMapping(value = CERTIFICATES_URI_ID, method = RequestMethod.GET)
    public JsonHeaderWrapper findSni(UserInfo userInfo, @PathVariable String id) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Certificate results = this.certificateService.findEntity(systemProfile(userInfo), id);
            jsonHeaderWrapper.setData(results);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }
}
