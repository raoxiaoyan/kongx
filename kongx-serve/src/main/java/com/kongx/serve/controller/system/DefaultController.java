package com.kongx.serve.controller.system;

import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.core.entity.UserInfo;
import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.controller.BaseController;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.service.IBaseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class DefaultController<T, PK> extends BaseController {
    protected IBaseService baseService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public JsonHeaderWrapper findByPage(T project) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            PaginationSupport paginationSupport = baseService.findByPage(project);
            jsonHeaderWrapper.setData(paginationSupport);
        } catch (Exception e) {
            e.printStackTrace();
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<T> findAll(T project) {
        return baseService.findAll(project);
    }


    @RequestMapping(method = RequestMethod.POST)
    public JsonHeaderWrapper add(@RequestBody T project, UserInfo userInfo, HttpServletRequest request) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            this.baseService.add(project, userInfo);
            jsonHeaderWrapper.setData(project);
            this.log(userInfo, OperationLog.OperationType.OPERATION_ADD, operationTarget(), project, request);
        } catch (Exception e) {
            e.printStackTrace();
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public JsonHeaderWrapper update(@RequestBody T project, UserInfo userInfo, HttpServletRequest request) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            this.baseService.update(project, userInfo);
            jsonHeaderWrapper.setData(project);
            this.log(userInfo, OperationLog.OperationType.OPERATION_UPDATE, operationTarget(), project, request);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public JsonHeaderWrapper findById(UserInfo userInfo, @PathVariable Integer id) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.baseService.findById(id));
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public JsonHeaderWrapper removeById(@PathVariable Integer id) {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            this.baseService.remove(id);
        } catch (Exception e) {
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
            jsonHeaderWrapper.setErrmsg(e.getMessage());
        }
        return jsonHeaderWrapper;
    }

    protected abstract void setBaseService(IBaseService<T, PK> iBaseService);

    protected abstract OperationLog.OperationTarget operationTarget();
}
