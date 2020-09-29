package com.kongx.serve.service.system;

import com.kongx.serve.mapper.GroupRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupRoleService {

    @Autowired
    private GroupRoleMapper groupRoleMapper;

    public int add(int groupId, int roleId, List<Map> profileList) {
        return this.groupRoleMapper.insert(wrapMap(groupId, roleId, profileList));
    }

    public int delete(int groupId, int roleId, List<Map> profileList) {
        return this.groupRoleMapper.remove(wrapMap(groupId, roleId, profileList));
    }

    private List<Map> wrapMap(int groupId, int roleId, List<Map> profileList) {
        List<Map> maps = new ArrayList<>();
        profileList.forEach((systemProfile -> {
            Map map = new HashMap();
            map.put("groupId", groupId);
            map.put("roleId", roleId);
            map.put("profile", systemProfile.get("label"));
            maps.add(map);
        }));
        return maps;
    }

}
