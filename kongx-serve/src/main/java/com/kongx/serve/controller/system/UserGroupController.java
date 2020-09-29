package com.kongx.serve.controller.system;


import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.UserGroup;
import com.kongx.serve.service.IBaseService;
import com.kongx.serve.service.system.GroupRoleService;
import com.kongx.serve.service.system.UserGroupService;
import com.kongx.serve.service.system.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController("UserGroupController")
@RequestMapping("system/user/group")
public class UserGroupController extends DefaultController<UserGroup, Integer> {

    @Resource(name = "userGroupService")
    private UserGroupService userGroupService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupRoleService groupRoleService;

    @Override
    @Resource(name = "userGroupService")
    protected void setBaseService(IBaseService iBaseService) {
        this.baseService = iBaseService;
    }

    @Override
    protected OperationLog.OperationTarget operationTarget() {
        return OperationLog.OperationTarget.USER_GROUP;
    }

    @RequestMapping(value = "/{groupId}/user/{userId}", method = RequestMethod.POST)
    public JsonHeaderWrapper setGroupUser(@PathVariable int groupId, @PathVariable String userId) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(groupUserService.add(groupId, userId));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/{groupId}/user", method = RequestMethod.POST)
    public JsonHeaderWrapper setBatchGroupUser(@PathVariable int groupId, @RequestBody List<String> userId) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            if (userId.isEmpty()) {
                jsonHeaderWrapper.setStatus(500);
                jsonHeaderWrapper.setErrmsg("请选择用户列表!");
            } else {
                jsonHeaderWrapper.setData(groupUserService.batchInsert(groupId, userId));
            }
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/{groupId}/user", method = RequestMethod.DELETE)
    public JsonHeaderWrapper deleteBatchGroupUser(@PathVariable int groupId, @RequestBody List<String> userId) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(groupUserService.batchDelete(groupId, userId));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/{groupId}/user/{userId}", method = RequestMethod.DELETE)
    public JsonHeaderWrapper removeGroupUser(@PathVariable int groupId, @PathVariable String userId) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(groupUserService.delete(groupId, userId));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/{groupId}/role/{roleId}", method = RequestMethod.POST)
    public JsonHeaderWrapper setGroupRole(@PathVariable int groupId, @PathVariable int roleId,
                                          @RequestBody List<Map> systemProfileList) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(groupRoleService.add(groupId, roleId, systemProfileList));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/{groupId}/drole/{roleId}", method = RequestMethod.POST)
    public JsonHeaderWrapper removeGroupRole(@PathVariable int groupId, @PathVariable int roleId,
                                             @RequestBody List<Map> systemProfileList) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(groupRoleService.delete(groupId, roleId, systemProfileList));
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }
}
