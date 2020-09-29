package com.kongx.serve.service.system;

import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.entity.system.SystemFunction;
import com.kongx.serve.mapper.FunctionMapper;
import com.kongx.serve.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("systemFunctionService")
public class FunctionService implements IBaseService<SystemFunction, Integer> {

    @Autowired
    private FunctionMapper systemFunctionMapper;

    @Override
    public PaginationSupport findByPage(SystemFunction entity) {
        throw new RuntimeException("");
    }

    public List<SystemFunction> findFunctionByTree() {
        return findFunctionByRole();
    }

    public List<SystemFunction> findFunctionByRole() {
        List<SystemFunction> list = this.systemFunctionMapper.findFunctionByTree(-1);
        List<SystemFunction> parents = new ArrayList<>();
        SystemFunction parent = new SystemFunction();
        parent.setName("Kongx");
        parent.setId(-1);
        parent.setChildren(this.wrapChildren(list, -1));
        parents.add(parent);
        return parents;
    }

    private List<SystemFunction> wrapChildren(List<SystemFunction> systemFunctions, Integer parentId) {
        List<SystemFunction> children = new ArrayList<>();
        systemFunctions.forEach(systemFunction -> {
            if (systemFunction.getParentId() == parentId) {
                systemFunction.setChildren(this.wrapChildren(systemFunctions, systemFunction.getId()));
                children.add(systemFunction);
            }
        });
        return children;
    }

    @Override
    public void add(SystemFunction entity, UserInfo userInfo) {
        this.systemFunctionMapper.insert(entity);
    }

    @Override
    public void update(SystemFunction entity, UserInfo userInfo) {
        this.systemFunctionMapper.update(entity);
    }

    @Override
    public SystemFunction findById(Integer id) {
        return this.systemFunctionMapper.findById(id);
    }
}
