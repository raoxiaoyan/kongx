package com.kongx.serve.service.system;

import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.core.entity.UserInfo;
import com.github.pagehelper.Page;
import com.kongx.serve.entity.system.UserGroup;
import com.kongx.serve.mapper.UserGroupMapper;
import com.kongx.serve.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("userGroupService")
public class UserGroupService implements IBaseService<UserGroup, Integer> {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public PaginationSupport findByPage(UserGroup entity) {
        PaginationSupport paginationSupport = new PaginationSupport();
        Page page = this.userGroupMapper.findByPage(entity.getStart(), entity.getLimit(), entity);
        paginationSupport.setItems(page);
        paginationSupport.setPageSize(entity.getLimit());
        paginationSupport.setTotalCount(Integer.valueOf(page.getTotal() + ""));
        return paginationSupport;
    }

    @Override
    public List<UserGroup> findAll(UserGroup entity) {
        return userGroupMapper.findAllGroup();
    }

    @Override
    public void add(UserGroup entity, UserInfo userInfo) {
        entity.setCreate_at(new Date());
        entity.setCreator(userInfo.getName());
        this.userGroupMapper.insert(entity);
    }

    @Override
    public void update(UserGroup entity, UserInfo userInfo) {
        entity.setModify_at(new Date());
        entity.setModifier(userInfo.getName());
        this.userGroupMapper.update(entity);
    }

    @Override
    public UserGroup findById(Integer id) {
        return this.userGroupMapper.findById(id);
    }
}
