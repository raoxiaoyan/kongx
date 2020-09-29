package com.kongx.serve.controller.system;

import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.UserInfoVO;
import com.kongx.serve.entity.system.UserPwd;
import com.kongx.serve.entity.system.UserRoleParas;
import com.kongx.serve.service.IBaseService;
import com.kongx.serve.service.system.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.kongx.serve.entity.system.OperationLog.OperationTarget.USER_INFO;

@RestController("UserInfoController")
@RequestMapping("/system/user")
public class UserInfoController extends DefaultController<UserInfoVO, Integer> {
    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public JsonHeaderWrapper findByPage(UserInfoVO project) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            PaginationSupport paginationSupport = userInfoService.findByPage(project);
            jsonHeaderWrapper.setData(paginationSupport);
        } catch (Exception e) {
            e.printStackTrace();
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @Override
    @Resource(name = "userInfoService")
    protected void setBaseService(IBaseService<UserInfoVO, Integer> iBaseService) {
        this.baseService = iBaseService;
    }

    @Override
    protected OperationLog.OperationTarget operationTarget() {
        return USER_INFO;
    }

    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
    public JsonHeaderWrapper updateUserRole(@RequestBody UserRoleParas paras) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.userInfoService.updateUserRole(paras));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/reset/{userId}/", method = RequestMethod.POST)
    public JsonHeaderWrapper resetpwd(@PathVariable String userId) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.userInfoService.resetpwd(userId));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/status/{status}/{userId}/", method = RequestMethod.POST)
    public JsonHeaderWrapper status(@PathVariable String status, @PathVariable String userId) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.userInfoService.status(status, userId));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/modifypwd/", method = RequestMethod.POST)
    public JsonHeaderWrapper modifypwd(@RequestBody UserPwd userPwd) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.userInfoService.modifyPwd(userPwd));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }
}
