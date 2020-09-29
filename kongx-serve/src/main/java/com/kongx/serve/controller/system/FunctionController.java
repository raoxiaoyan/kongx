package com.kongx.serve.controller.system;

import com.kongx.common.jsonwrapper.JsonHeaderWrapper;
import com.kongx.serve.entity.system.OperationLog;
import com.kongx.serve.entity.system.SystemFunction;
import com.kongx.serve.service.IBaseService;
import com.kongx.serve.service.system.FunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("system/function")
@Slf4j
public class FunctionController extends DefaultController<SystemFunction, Integer> {
    @Autowired
    private FunctionService functionService;

    /**
     * 左侧菜单树
     *
     * @return
     */
    @RequestMapping("/tree")
    public JsonHeaderWrapper menuTree() {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.functionService.findFunctionByTree());
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @RequestMapping("/menu/role")
    public JsonHeaderWrapper menuTreeToRole() {
        JsonHeaderWrapper jsonHeaderWrapper = init();
        try {
            jsonHeaderWrapper.setData(this.functionService.findFunctionByRole());
        } catch (Exception e) {
            jsonHeaderWrapper.setErrmsg(e.getMessage());
            jsonHeaderWrapper.setStatus(JsonHeaderWrapper.StatusEnum.Failed.getCode());
        }
        return jsonHeaderWrapper;
    }

    @Override
    @Resource(name = "systemFunctionService")
    protected void setBaseService(IBaseService iBaseService) {
        this.baseService = iBaseService;
    }

    @Override
    protected OperationLog.OperationTarget operationTarget() {
        return OperationLog.OperationTarget.SYSTEM_FUNCTION;
    }
}

