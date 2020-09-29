package com.kongx.serve.service.system;

import com.github.pagehelper.Page;
import com.kongx.common.core.entity.PaginationSupport;
import com.kongx.common.core.entity.UserInfo;
import com.kongx.serve.entity.system.*;
import com.kongx.serve.mapper.UserInfoMapper;
import com.kongx.serve.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kongx.common.core.entity.UserInfo.DOMESTIC_CONSUMER;
import static com.kongx.common.core.entity.UserInfo.SUPER_ADMIN;
import static com.kongx.common.utils.BaseUtils.sha1;

@Service("userInfoService")
public class UserInfoService implements IBaseService<UserInfoVO, String> {

    private String DEFAULT_PWD = "MTIzNDU2";

    @Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> findAll() {
        List<UserInfo> allUser = userInfoMapper.findAllUser();
        return allUser;
    }

    public UserInfo login(String userName, String password) {
        UserInfo userInfo = this.findById(userName);
        if (userInfo == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (!sha1(password + userInfo.getUserId()).equals(userInfo.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        return userInfo;
    }

    public Optional updateUserRole(UserRoleParas userRoleParas) {
        for (UserInfo userInfo : userRoleParas.getUserInfos()) {
            this.userInfoMapper.deleteUserRole(userInfo.getUserId());
            List<SystemUserRole> systemUserRoles = new ArrayList<>();
            for (Integer roleId : userRoleParas.getSystemRoles()) {
                systemUserRoles.add(new SystemUserRole(userInfo.getUserId(), roleId));
            }
            if (!systemUserRoles.isEmpty()) {
                this.userInfoMapper.batchInsertRole(systemUserRoles);
            }
        }
        return Optional.ofNullable(0);
    }

    @Override
    public PaginationSupport findByPage(UserInfoVO entity) {
        PaginationSupport paginationSupport = new PaginationSupport();
        Page page = this.userInfoMapper.findByPage(entity.getStart(), entity.getLimit(), entity);
        paginationSupport.setItems(page);
        paginationSupport.setPageSize(entity.getLimit());
        paginationSupport.setTotalCount(Integer.valueOf(page.getTotal() + ""));
        return paginationSupport;
    }

    public Optional findAllMenu(UserInfo userInfo, Integer parentId, String menuType, SystemProfile systemProfile) {
        int roleCount = userInfoMapper.countAuthorityRole(userInfo.getUserId(), systemProfile);
        String roleName = Optional.ofNullable(userInfo.getRoleName()).orElseGet(() -> DOMESTIC_CONSUMER);
        if (SUPER_ADMIN.equalsIgnoreCase(roleName)) {
            return Optional.ofNullable(wrapChildren(this.userInfoMapper.findMenuByRoleCode(SUPER_ADMIN, menuType), parentId, menuType));
        }
        if (roleCount == 0) {
            return Optional.ofNullable(wrapChildren(this.userInfoMapper.findMenuByRoleCode(DOMESTIC_CONSUMER, menuType), parentId, menuType));
        }
        return Optional.ofNullable(wrapChildren(this.userInfoMapper.findMenuByUserId(userInfo.getUserId(), menuType, systemProfile), parentId, menuType));
    }

    private List<Menu> wrapChildren(List<Menu> systemFunctions, Integer parentId, String menuType) {
        if ("point".equalsIgnoreCase(menuType)) {
            return systemFunctions;
        }
        List<Menu> children = new ArrayList<>();
        systemFunctions.forEach(systemFunction -> {
            if (systemFunction.getParentId() == parentId) {
                systemFunction.setChildren(this.wrapChildren(systemFunctions, systemFunction.getId(), menuType));
                children.add(systemFunction);
            }
        });
        return children;
    }

    @Override
    public void add(UserInfoVO entity, UserInfo userInfo) {
        UserInfo exists = this.findById(entity.getUserId());
        if (exists != null) {
            throw new RuntimeException("用户名已存在!");
        }
        entity.setPassword(sha1(DEFAULT_PWD + entity.getUserId()));
        entity.setCreator(userInfo.getUserId());
        this.userInfoMapper.insertUser(entity);
    }

    @Override
    public void update(UserInfoVO entity, UserInfo userInfo) {
        this.userInfoMapper.updateUser(entity);
    }

    public UserInfoVO findById(String id) {
        return userInfoMapper.findById(id);
    }


    public boolean resetpwd(String userId) {
        this.userInfoMapper.resetpwd(sha1(DEFAULT_PWD + userId), userId); //默认为123456
        return true;
    }

    public boolean status(String status, String userId) {
        this.userInfoMapper.status(status, userId);
        return true;
    }

    public boolean modifyPwd(UserPwd userPwd) {
        UserInfo userInfo = this.findById(userPwd.getUserId());
        if (!sha1(userPwd.getOldPassword() + userPwd.getUserId()).equals(userInfo.getPassword())) {
            throw new RuntimeException("原密码输入错误");
        }
        this.userInfoMapper.resetpwd(sha1(userPwd.getNewPassword() + userPwd.getUserId()), userPwd.getUserId());
        return true;
    }

    public static void main(String[] args) {
        System.out.println(sha1("MTIzNDU2admin"));
    }
}
