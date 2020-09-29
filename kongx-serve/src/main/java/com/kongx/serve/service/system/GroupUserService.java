package com.kongx.serve.service.system;

import com.kongx.serve.mapper.GroupUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupUserService {

    @Autowired
    private GroupUserMapper groupUserMapper;


    public int add(int groupId, String userId) {
        return this.groupUserMapper.insert(groupId, userId);
    }

    public int batchInsert(int groupId, List<String> userIds) {
        return this.groupUserMapper.batchInsert(groupId, userIds);
    }


    public int delete(int groupId, String userId) {
        return this.groupUserMapper.remove(groupId, userId);
    }

    public int batchDelete(int groupId, List<String> userIds) {
        return this.groupUserMapper.batchRemove(groupId, userIds);
    }
}
