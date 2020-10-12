package com.kongx.serve.controller.system;

import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemProfile;
import com.kongx.serve.service.system.SystemProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("/SystemProfileController")
@RequestMapping("/system/profile/")
public class SystemProfileController extends BaseController {
    @Autowired
    private SystemProfileService systemProfileService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<SystemProfile> findAll() {
        return systemProfileService.findAll();
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public SystemProfile findActiveKongClient(UserInfo userInfo) {
        return this.systemProfileService.getClientByName(userInfo.getUserId());
    }

    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public SystemProfile setActiveKongClient(UserInfo userInfo, @RequestBody SystemProfile systemProfile) {
        this.systemProfileService.setActiveClient(userInfo.getUserId(), systemProfile);
        return systemProfile;
    }

    @RequestMapping(value = "/profiles", method = RequestMethod.GET)
    public JsonHeaderWrapper list() {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        List<SystemProfile> systemProfiles = this.systemProfileService.findAll();
        jsonHeaderWrapper.setData(systemProfiles);
        return jsonHeaderWrapper;
    }


    @RequestMapping(value = "/profiles/group", method = RequestMethod.GET)
    public JsonHeaderWrapper profilesGroup() {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        List<Map<String, Object>> systemProfiles = this.systemProfileService.findAllByGroup();
        jsonHeaderWrapper.setData(systemProfiles);
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/profiles", method = RequestMethod.POST)
    public JsonHeaderWrapper add(@RequestBody SystemProfile systemProfile) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            this.systemProfileService.addClient(systemProfile);
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }


    @RequestMapping(value = "/profiles/probing", method = RequestMethod.POST)
    public JsonHeaderWrapper probing(@RequestBody SystemProfile systemProfile) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            Map map = this.systemProfileService.probing(systemProfile);
            jsonHeaderWrapper.setData(map.get("version"));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/profiles/{clientId}", method = RequestMethod.POST)
    public JsonHeaderWrapper update(@PathVariable int clientId, @RequestBody SystemProfile systemProfile, UserInfo userInfo) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            this.systemProfileService.updateClient(systemProfile);
            this.log(userInfo, OperationLog.OperationType.OPERATION_UPDATE, OperationLog.OperationTarget.SYSTEM_PROFILE, systemProfile, systemProfile.getProfile());
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/profiles/{clientId}", method = RequestMethod.DELETE)
    public JsonHeaderWrapper remove(@PathVariable int clientId) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            jsonHeaderWrapper.setData(this.systemProfileService.removeClient(clientId));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/profiles/{profile}", method = RequestMethod.GET)
    public JsonHeaderWrapper findByProfile(@PathVariable String profile) {
        JsonHeaderWrapper jsonHeaderWrapper = this.init();
        try {
            jsonHeaderWrapper.setData(this.systemProfileService.findByProfile(profile));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }
}
